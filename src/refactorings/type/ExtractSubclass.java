package refactorings.type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import recoder.CrossReferenceServiceConfiguration;
import recoder.abstraction.ClassType;
import recoder.abstraction.Constructor;
import recoder.abstraction.Method;
import recoder.abstraction.Type;
import recoder.convenience.AbstractTreeWalker;
import recoder.convenience.ForestWalker;
import recoder.convenience.TreeWalker;
import recoder.java.CompilationUnit;
import recoder.java.Identifier;
import recoder.java.Import;
import recoder.java.ProgramElement;
import recoder.java.declaration.ClassDeclaration;
import recoder.java.declaration.ConstructorDeclaration;
import recoder.java.declaration.DeclarationSpecifier;
import recoder.java.declaration.Extends;
import recoder.java.declaration.FieldDeclaration;
import recoder.java.declaration.MemberDeclaration;
import recoder.java.declaration.MethodDeclaration;
import recoder.java.declaration.TypeDeclaration;
import recoder.java.reference.FieldReference;
import recoder.java.reference.MemberReference;
import recoder.java.reference.MethodReference;
import recoder.java.reference.SuperConstructorReference;
import recoder.java.reference.SuperReference;
import recoder.java.reference.ThisReference;
import recoder.java.reference.TypeReference;
import recoder.java.reference.VariableReference;
import recoder.kit.MethodKit;
import recoder.kit.MiscKit;
import recoder.kit.PackageKit;
import recoder.kit.ProblemReport;
import recoder.kit.UnitKit;
import recoder.kit.VariableKit;
import recoder.list.generic.ASTArrayList;
import recoder.list.generic.ASTList;
import recoder.service.CrossReferenceSourceInfo;
import refactorings.Refactoring;

public class ExtractSubclass extends Refactoring 
{
	private TypeDeclaration currentDeclaration, subDeclaration;
	private CompilationUnit unit;
	private HashSet<MethodDeclaration> methods;
	private ArrayList<FieldDeclaration> fields;
	private ASTList<MemberDeclaration> members;
	private ArrayList<ClassDeclaration> subClasses;
	private ArrayList<Integer> positions;
	private int[] importSizes;
	
	public ExtractSubclass(CrossReferenceServiceConfiguration sc) 
	{
		super(sc);
	}
	
	public ExtractSubclass() 
	{
		super();
	}
	
	public ProblemReport analyze(int iteration, int unit, int element) 
	{
		// Initialise and pick the element to visit.
		super.tw = new TreeWalker(getSourceFileRepository().getKnownCompilationUnits().get(unit));
		
		for (int i = 0; i < element; i++)
		{
			super.tw.next(TypeDeclaration.class);
			TypeDeclaration td = (TypeDeclaration) super.tw.getProgramElement();
			if (!mayRefactor(td))
				i--;
		}
		
		ProgramElement pe = super.tw.getProgramElement();
		this.currentDeclaration = (TypeDeclaration) pe;
		CrossReferenceSourceInfo si = getCrossReferenceSourceInfo();
		this.importSizes = new int[this.subClasses.size()];
		Set<Type> distinctTypes = new HashSet<Type>();
		
		this.members = new ASTArrayList<MemberDeclaration>(this.fields.size() + this.methods.size());
		this.members.addAll(this.fields);
		this.members.addAll(this.methods);
		
		for (MemberDeclaration md : this.members)
			distinctTypes.addAll(super.getTypes(md, si));
		
		ArrayList<Type> types = new ArrayList<Type>(distinctTypes);
		ASTList<Import> imports = super.getMemberImports(types, UnitKit.getCompilationUnit(this.currentDeclaration).getImports(), si);
		
		// Construct refactoring transformation.
		// The transformation is handled here manually and the transformation
		// method will do nothing for this refactoring when it is called.
		super.transformation = null;
		getChangeHistory().begin(this);
		
		this.positions = new ArrayList<Integer>(this.members.size());
		
		for (MemberDeclaration md : this.members)
			positions.add(super.getPosition(this.currentDeclaration, md));
		
		String name = this.currentDeclaration.getIdentifier().getText() + "_SubClass";
		ArrayList<String> typeNames = getAllTypeNames();
		
		while (typeNames.contains(name))
		{
			if (name.equals(this.currentDeclaration.getIdentifier().getText() + "_SubClass"))			
				name = name + "_";
			
			name = name + (char)(((int)(Math.random() * 26)) + 'a');
		}
			
		// Create new class from current class.
		Identifier newIdentifier = getProgramFactory().createIdentifier(name);
		ASTList<DeclarationSpecifier> declarations = new ASTArrayList<DeclarationSpecifier>(getProgramFactory().createPublic());
		Extends superclass = getProgramFactory().createExtends(getProgramFactory().createTypeReference(this.currentDeclaration.getIdentifier()));
		this.subDeclaration = this.currentDeclaration.deepClone();
		this.subDeclaration.setProgramModelInfo(this.currentDeclaration.getProgramModelInfo());
		attach(superclass, (ClassDeclaration) this.subDeclaration);
		attach(newIdentifier, this.subDeclaration);
		this.subDeclaration.setDeclarationSpecifiers(declarations);
		ASTList<MemberDeclaration> remove = new ASTArrayList<MemberDeclaration>(this.subDeclaration.getMembers().size());
		
		for (MemberDeclaration md : this.subDeclaration.getMembers())
			remove.add(md);
		
		for (MemberDeclaration md : remove)
			detach(md);
		
		// Create new compilation unit to add newly created class to, and update the program model.
		ASTList<TypeDeclaration> type = new ASTArrayList<TypeDeclaration>(this.subDeclaration);
		this.unit = getProgramFactory().createCompilationUnit(UnitKit.getCompilationUnit(this.currentDeclaration).getPackageSpecification(), 
                                                              imports, type);
		attach(this.unit);
		this.subDeclaration.getFactory().getServiceConfiguration().getChangeHistory().updateModel();
		
		for (ClassDeclaration cd : this.subClasses)
		{
			this.importSizes[this.subClasses.indexOf(cd)] = UnitKit.getCompilationUnit(cd).getImports().size();
			
			if (!(cd.getPackage().equals(this.subDeclaration.getPackage())))
			{
				Import i = getProgramFactory().createImport(PackageKit.createPackageReference(getProgramFactory(), 
						                                                                      this.subDeclaration.getPackage()));
				
				if (!(UnitKit.getCompilationUnit(cd).getImports().contains(i)))
					attach(i, UnitKit.getCompilationUnit(cd), UnitKit.getCompilationUnit(cd).getImports().size());
			}
			
			Extends subClass = getProgramFactory().createExtends(getProgramFactory().createTypeReference(this.subDeclaration.getIdentifier()));
			attach(subClass, cd);
		}
		
		// Move members from the current class to the new extracted class.
		for (MemberDeclaration md : this.members)
		{
			detach(md);
			attach(md, this.subDeclaration, this.subDeclaration.getMembers().size());
		}
			
		// Specify refactoring information for results information.
		super.refactoringInfo = "Iteration " + iteration + ": \"Extract Subclass\" applied from element(s) \"";
		
		for (int i = 0; i < this.members.size(); i++)
		{
			if (this.members.get(i) instanceof MethodDeclaration)
				super.refactoringInfo = super.refactoringInfo + ((MethodDeclaration) this.members.get(i)).getName();
			else
			{
				int last = this.members.get(i).toString().lastIndexOf(">");
				super.refactoringInfo = super.refactoringInfo + this.members.get(i).toString().substring(last + 2);
			}
			
			if (i < this.members.size() - 1)
				super.refactoringInfo = super.refactoringInfo + ", ";
		}
		
		super.refactoringInfo = super.refactoringInfo + "\" in class " + ((TypeDeclaration) pe).getName() + " to " + this.subDeclaration.getName();
		return setProblemReport(EQUIVALENCE);
	}
	
	public ProblemReport analyzeReverse() 
	{
		// Construct refactoring transformation.
		super.transformation = null;
		TreeMap<Integer, MemberDeclaration> orderedMembers = new TreeMap<Integer, MemberDeclaration>();
		
		for (int i = 0; i < this.members.size(); i++)
			orderedMembers.put(this.positions.get(i), this.members.get(i));
		
		for (FieldDeclaration fd : this.fields)
			detach(fd);
		
		for (MethodDeclaration md : this.methods)
			detach(md);

		// Iterate through ascending list (by positions) in TreeMap
		for(@SuppressWarnings("rawtypes") Map.Entry entry : orderedMembers.entrySet())
		{  
			MemberDeclaration md =  (MemberDeclaration) entry.getValue();
			int p =  (int) entry.getKey();
			attach(md, this.currentDeclaration, p);	
		}
		
		for (ClassDeclaration cd : this.subClasses)
		{
			Extends currentClass = getProgramFactory().createExtends(getProgramFactory().createTypeReference(this.currentDeclaration.getIdentifier()));
			attach(currentClass, cd);
			
			if (UnitKit.getCompilationUnit(cd).getImports().size() != this.importSizes[this.subClasses.indexOf(cd)])
			{
				ASTList<Import> imports = UnitKit.getCompilationUnit(cd).getImports();
				imports.remove(imports.size() - 1);
				UnitKit.getCompilationUnit(cd).setImports(imports);
			}
		}
		
		detach(this.unit);
		getChangeHistory().updateModel();
		return setProblemReport(EQUIVALENCE);
	}

	public boolean mayRefactor(TypeDeclaration td)
	{
		// Makes a number of initial checks against the class 
		// in order to quickly exclude insufficient candidates. 
		if (!(td.isOrdinaryClass()) || (td.isPrivate() || (td.isFinal()) || (td.getName() == null))) 
			return false;
		else
		{
			// Prevents "Zero Service" outputs logged to the console.
			if (td.getProgramModelInfo() == null)
				td.getFactory().getServiceConfiguration().getChangeHistory().updateModel();
			
			if (!(td.getName().equals("Helpers")) && !(td.getName().equals("Unique")))
				return false;
			
			boolean next;
			boolean defaultConstructor = false;
			CrossReferenceSourceInfo si = getCrossReferenceSourceInfo();
			ArrayList<MethodDeclaration> availableMethods = new ArrayList<MethodDeclaration>(td.getMethods().size());
			ArrayList<FieldDeclaration> availableFields = new ArrayList<FieldDeclaration>(td.getFields().size());
			ArrayList<TypeDeclaration> innerTypes = new ArrayList<TypeDeclaration>(td.getTypes().size());
			
			for (ClassType ct : si.getSubtypes(td))
				if (!(ct instanceof TypeDeclaration) || !(ct.isOrdinaryClass()))
					return false;
			
			for (Constructor c : td.getConstructors())
				if (c.getSignature().size() == 0)
					defaultConstructor = true;
			
			if (!(defaultConstructor))
				return false;
			
			// Initial checks on methods to restrict the set from 
			// which a sub set of methods can be selected to be moved.
			for (MemberDeclaration md : td.getMembers())
			{
				next = false;
				
				if ((md instanceof MethodDeclaration) && !(md instanceof ConstructorDeclaration))
				{					
					if (MethodKit.getRedefiningMethods(si, (Method) md).size() > 0)
						next = true;
					
					// Checks any reference to the method in the program and if it
					// refers explicitly to the current class it will not be applicable.
					for (MemberReference mr : si.getReferences((Method) md))
					{
						if (!(mr instanceof MethodReference))
						{
							next = true;
							break;
						}

						if ((((MethodReference) mr).getReferencePrefix() instanceof TypeReference) || 
							((si.getType(((MethodReference) mr).getReferencePrefix()) != null) && 
							 !(((MethodReference) mr).getReferencePrefix() instanceof ThisReference) && 
							 !(((MethodReference) mr).getReferencePrefix() instanceof SuperReference) && 
							 (si.getType(((MethodReference) mr).getReferencePrefix()).equals(td))))
						{  
							next = true;
							break;
						}
					}
					
					if (!next)
						availableMethods.add((MethodDeclaration) md);
				}
				// Initial checks on fields to restrict the
				// set that may be moved with the methods.
				else if (md instanceof FieldDeclaration)
				{			
					// Checks any reference to the field in the program and if it
					// refers explicitly to the current class it will not be applicable.
					for (FieldReference fr : si.getReferences(((FieldDeclaration) md).getFieldSpecifications().get(0)))
					{
						if ((fr.getReferencePrefix() instanceof TypeReference) || 
							((si.getType(fr.getReferencePrefix()) != null) && 
							 !(fr.getReferencePrefix() instanceof ThisReference) && 
							 !(fr.getReferencePrefix() instanceof SuperReference) && 
							 (si.getType(fr.getReferencePrefix()).equals(td))))
						{
							next = true;
							break;
						}							
					}
					
					if (!next)
						availableFields.add((FieldDeclaration) md);
				}
				else if (md instanceof TypeDeclaration)
				{
					innerTypes.add((TypeDeclaration) md);
					
					for (ClassType ct : si.getAllSubtypes((TypeDeclaration) md))
						innerTypes.add((TypeDeclaration) ct);
				}
			}
			
			HashSet<MethodDeclaration> methodList = new HashSet<MethodDeclaration>(availableMethods.size());
			
			// Need to see if there is a set of methods to move to the 
			// sub class without breaking the semantics of the program.
			for (MethodDeclaration md : availableMethods)
			{

				next = false;
				methodList.clear();
				methodList.add(md);

				// Finds the methods in the class that reference the 
				// current method and if they can be moved add them to the list.
				// This finds potential groups of methods to move.
				for (MemberReference mr : MethodKit.getReferences(si, md, td, true))
				{
					if ((MiscKit.getParentMemberDeclaration(mr) == null) || !(MiscKit.getParentMemberDeclaration(mr) instanceof MethodDeclaration) ||
						!(availableMethods.contains(MiscKit.getParentMemberDeclaration(mr))))
					{
						next = true;
						break;
					}
					else 
					{
						if (!(methodList.contains((MethodDeclaration) MiscKit.getParentMemberDeclaration(mr))))
							methodList.add((MethodDeclaration) MiscKit.getParentMemberDeclaration(mr));
					}
				}
				
				// Covers the case that the return type includes a generic type as in this 
				// case it doesn't seem to catch any references to the method in the class.
				if (MethodKit.getReferences(si, md, td, true).size() == 0)
				{
					String returnType = md.toSource().substring(0, md.toSource().indexOf(md.getName() + '('));
					while (returnType.indexOf('<') != -1)
					{
						returnType = returnType.substring(returnType.indexOf('<'));
						if ((returnType.length() >= 3) && (returnType.charAt(2) == '>'))
						{
							next = true;
							break;
						}
					}
				}
				
				// If the group of methods makes up more than 50% of the methods in the class, discard it.
				if ((methodList.size() * 2) > td.getMethods().size())
					next = true;
				
				if (next)
					continue;
				
				// If any of the methods in the group access one of the other methods in the class
				// and the method is private and therefore will be inaccessible, discard the group.
				for (Method m : td.getMethods())
				{
					if (!(methodList.contains(m)) && (m.isPrivate()))
					{
						for (MemberReference mr : MethodKit.getReferences(si, m, td, true))
						{
							if (methodList.contains(MiscKit.getParentMemberDeclaration(mr)))
							{
								next = true;
								break;
							}
						}
					}
				}
				
				if (next)
					continue;

				// For the methods in the group, if any other methods not 
				// within the group need access to it the group is discarded.
				for (MethodDeclaration md2 : methodList)
				{
					if (!(md2.equals(md)))
					{
						for (MemberReference mr : MethodKit.getReferences(si, md2, td, true))
						{
							if ((MiscKit.getParentMemberDeclaration(mr) == null) || 
								!(MiscKit.getParentMemberDeclaration(mr) instanceof MethodDeclaration) ||
								!(methodList.contains(MiscKit.getParentMemberDeclaration(mr))))
							{
								next = true;
								break;
							}
						}

						if (next)
							break;
					}
				}
				
				if (next)
					continue;

				// The previous list contains the fields that can be moved and this list
				// will store the sub selection that will be moved if this group can be used 
				// i.e. fields the method group use that are not needed elsewhere in the class.
				ArrayList<FieldDeclaration> fieldsToMove = new ArrayList<FieldDeclaration>(availableFields.size());

				// If a field is referenced in the method group and elsewhere in the class and it is private the 
				// method group will not be able to have access to the field and it will be discarded.
				for (MemberDeclaration fd : td.getMembers())
				{
					if (fd instanceof FieldDeclaration)
					{
						boolean inMethods = false;
						boolean outMethods = false;

						for (VariableReference vr : VariableKit.getReferences(si, ((FieldDeclaration) fd).getFieldSpecifications().get(0), td, true))
						{
							if (methodList.contains(MiscKit.getParentMemberDeclaration(vr)))
							{
								inMethods = true;
								if (outMethods)
									break;
							}
							else
							{
								outMethods = true;
								if (inMethods)
									break;
							}
						}

						if  (((outMethods) && (inMethods) && (fd.isPrivate())) || 
							 (!(outMethods) && (inMethods) && (fd.isPrivate()) && !(availableMethods.contains(fd))))
						{
							next = true;
							break;
						}
						else if (!(outMethods) && (inMethods) && (availableMethods.contains(fd)))
							fieldsToMove.add((FieldDeclaration) fd);
					}
				}
				
				if (next)
					continue;
				
				// If any of the sub classes of the current type use the members being moved to the sub class, 
				// The direct sub class will need to be changed to derive from the sub class.
				this.subClasses = new ArrayList<ClassDeclaration>(si.getSubtypes(td).size());
				for (ClassType ct : si.getSubtypes(td))
				{
					ArrayList<ClassType> classes = new ArrayList<ClassType>(si.getAllSubtypes(ct).size() + 1);
					classes.add(ct);
					classes.addAll(si.getAllSubtypes(ct));
					boolean breakout = false;

					for (MethodDeclaration m : methodList)
					{
						for (ClassType c : classes)
						{
							if (MethodKit.getReferences(si, m, (TypeDeclaration) c, true).size() > 0)
							{
								this.subClasses.add((ClassDeclaration) ct);
								breakout = true;
								break;
							}
						}
						
						if (breakout)
							break;
					}
					
					if (!breakout)
					{
						for (FieldDeclaration f : fieldsToMove)
						{						
							for (ClassType c : classes)
							{
								if (VariableKit.getReferences(si, f.getFieldSpecifications().get(0), (TypeDeclaration) c, true).size() > 0)
								{
									this.subClasses.add((ClassDeclaration) ct);
									breakout = true;
									break;
								}
							}
							
							if (breakout)
								break;
						}
					}
					
					if (!(this.subClasses.contains((ClassDeclaration) ct)))
						this.subClasses.add((ClassDeclaration) ct);
					
					// If any of these classes contain any super references to
					// a constructor with parameters, the group will be discarded.
					if (breakout)
					{
						AbstractTreeWalker tw = new TreeWalker((TypeDeclaration) ct);
						while (tw.next(SuperConstructorReference.class)) 
						{
							SuperConstructorReference ref = (SuperConstructorReference) tw.getProgramElement();
							if ((ref.getArguments() != null) && (ref.getArguments().size() > 0))
							{
								next = true;
								break;
							}
						}
					}
					
					if (next)
						break;
				}
				
				Set<Type> distinctTypes = new HashSet<Type>();
				for (MemberDeclaration dec : availableMethods)
					distinctTypes.addAll(super.getTypes(dec, si));
				
				for (MemberDeclaration dec : availableFields)
					distinctTypes.addAll(super.getTypes(dec, si));
				
				for (Type t : distinctTypes)
					if (innerTypes.contains(t))
						next = true;

				// If an acceptable group of methods can be found from the class,
				// the methods and fields to be moved are saved and the method returns true.
				if (!next)
				{ 
					this.methods = methodList;
					this.fields = fieldsToMove;
					this.currentDeclaration = td;
					return true;
				}
			}

			return false;
		}
	}

	// Count the amount of available elements in the chosen class for refactoring.
	// If an element is not applicable for the current refactoring it is not counted.
	public int getAmount(int unit)
	{
		int counter = 0;
		AbstractTreeWalker tw = new TreeWalker(getSourceFileRepository().getKnownCompilationUnits().get(unit));

		// Only counts the relevant program element.
		while (tw.next(TypeDeclaration.class))
		{
			TypeDeclaration td = (TypeDeclaration) tw.getProgramElement();
			if (mayRefactor(td))
				counter++;
		}

		return counter;
	}
	
	public String getName(int unit, int element)
	{		
		AbstractTreeWalker tw = new TreeWalker(getSourceFileRepository().getKnownCompilationUnits().get(unit));

		for (int i = 0; i < element; i++)
		{
			tw.next(TypeDeclaration.class);
			TypeDeclaration td = (TypeDeclaration) tw.getProgramElement();
			if (!mayRefactor(td))
				i--;
		}
		
		TypeDeclaration td = (TypeDeclaration) tw.getProgramElement();
		return td.getName();
	}
	
	private ArrayList<String> getAllTypeNames()
	{
		AbstractTreeWalker tw = new ForestWalker(getSourceFileRepository().getKnownCompilationUnits());
		ArrayList<String> names = new ArrayList<String>(getSourceFileRepository().getKnownCompilationUnits().size());

		while (tw.next(TypeDeclaration.class))
			names.add(((TypeDeclaration) tw.getProgramElement()).getName());

		return names;
	} 
}
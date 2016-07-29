package refactorings.field;

import java.util.ArrayList;
import java.util.List;

import recoder.CrossReferenceServiceConfiguration;
import recoder.abstraction.ClassType;
import recoder.abstraction.Field;
import recoder.abstraction.Method;
import recoder.abstraction.Package;
import recoder.abstraction.Type;
import recoder.bytecode.ClassFile;
import recoder.convenience.AbstractTreeWalker;
import recoder.convenience.TreeWalker;
import recoder.java.Import;
import recoder.java.ProgramElement;
import recoder.java.declaration.ClassDeclaration;
import recoder.java.declaration.FieldDeclaration;
import recoder.java.declaration.FieldSpecification;
import recoder.java.declaration.MemberDeclaration;
import recoder.java.declaration.MethodDeclaration;
import recoder.java.declaration.TypeDeclaration;
import recoder.java.reference.FieldReference;
import recoder.java.reference.MethodReference;
import recoder.java.reference.SuperReference;
import recoder.java.reference.ThisReference;
import recoder.java.reference.TypeReference;
import recoder.java.reference.VariableReference;
import recoder.kit.MiscKit;
import recoder.kit.PackageKit;
import recoder.kit.ProblemReport;
import recoder.kit.UnitKit;
import recoder.kit.VariableKit;
import recoder.list.generic.ASTList;
import recoder.service.CrossReferenceSourceInfo;
import refactorings.Refactoring;

public class MoveFieldDown extends Refactoring 
{
	private TypeDeclaration currentDeclaration, subDeclaration;
	private List<VariableReference> superReferences;
	private ArrayList<SuperReference> thisReferences;
	private ASTList<Import> subDeclarationImports;
	private int position, randomPosition, duplicatePosition;
	
	public MoveFieldDown(CrossReferenceServiceConfiguration sc) 
	{
		super(sc);
	}
	
	public MoveFieldDown() 
	{
		super();
	}
	
	public ProblemReport analyze(int iteration, int unit, int element) 
	{
		// Initialise and pick the element to visit.
		CrossReferenceSourceInfo si = getCrossReferenceSourceInfo();
		super.tw = new TreeWalker(getSourceFileRepository().getKnownCompilationUnits().get(unit));
		for (int i = 0; i < element; i++)
		{
			super.tw.next(FieldDeclaration.class);
			FieldDeclaration fd = (FieldDeclaration) super.tw.getProgramElement();
			if (!mayRefactor(fd))
				i--;
		}
		
		ProgramElement pe = super.tw.getProgramElement();
		FieldDeclaration fd = (FieldDeclaration) pe;
		int last = pe.toString().lastIndexOf(">");
		this.currentDeclaration = MiscKit.getParentTypeDeclaration(fd);
		this.position = super.getPosition(this.currentDeclaration, fd);
		this.duplicatePosition = -1;
		this.superReferences = new ArrayList<VariableReference>();
		ArrayList<Type> types = super.getTypes(fd, si);
		ASTList<Import> fieldImports = super.getMemberImports(types, UnitKit.getCompilationUnit(this.currentDeclaration).getImports(), si);
		boolean addPackageImport = false;
		Package pack = this.currentDeclaration.getPackage();
		
		// Construct refactoring transformation.
		// The transformation is handled here manually and the transformation
		// method will do nothing for this refactoring when it is called.
		super.transformation = null;
		getChangeHistory().begin(this);
		
		// Checks packages before changes are made to check if the current 
		// declaration package needs to be added as an import to its subtype.
		if (!(this.currentDeclaration.getPackage().equals(this.subDeclaration.getPackage())))
			addPackageImport = true;

		// Find "this." references in field and change them to "super." references.
		this.thisReferences =  new ArrayList<SuperReference>();
		for (ThisReference tr : super.getThisReferences(fd))
		{
			SuperReference sr = new SuperReference(tr.deepClone());
			tr.getASTParent().replaceChild(tr, sr);
			this.thisReferences.add(sr);
		}

		// Checks if the sub class contains an identical version of the field.
		for (MemberDeclaration dec : this.subDeclaration.getMembers())
		{
			if ((dec instanceof FieldDeclaration) && (dec.toSource().equals(fd.toSource())))
			{
				this.duplicatePosition = super.getPosition(this.subDeclaration, dec);
				detach(dec);
				break;
			}
		}
		
		// Any "super." references to the field in the sub class are changed to "this." references.
		for (FieldSpecification fs : fd.getFieldSpecifications())
			this.superReferences.addAll(VariableKit.getReferences(si, fs, this.subDeclaration, true));

		for (VariableReference vr : this.superReferences)
			if (((FieldReference) vr).getReferencePrefix() instanceof SuperReference)
				((VariableReference) vr).replaceChild(((FieldReference) vr).getReferencePrefix(), new ThisReference());
		
		ASTList<MemberDeclaration> members = this.subDeclaration.getMembers();
		
		if (members.isEmpty())
			this.randomPosition = 0;
		else
		{
			for (int i = 0; i < members.size(); i++)
			{
				if (members.get(i) instanceof MethodDeclaration)
				{
					this.randomPosition = i + 1;
					break;
				}
				else if (i == members.size() - 1)
					this.randomPosition = members.size() + 1;
			}

			// Generate random position between the start of the class and the first method declaration in the class.
			this.randomPosition = (int)(Math.random() * this.randomPosition);
		}

		detach(fd);
		attach(fd, this.subDeclaration, this.randomPosition);
		
		// Add any applicable imports from the current class to the sub class.
		this.subDeclarationImports =  UnitKit.getCompilationUnit(this.subDeclaration).getImports();
		ASTList<Import> imports =  this.subDeclarationImports;
		
		// If they aren't already present, add the field imports.
		for (Import ci : fieldImports)
		{
			boolean contains = false;

			for (Import i : imports)
			{
				if ((i.toString().equals(ci.toString())))
				{
					contains = true;
					break;
				}
			}

			if (!contains)
				imports.add(ci);
		}

		// If the package import hasn't already been added and the supertype
		// is in a different package, create and add an import to the package.
		if (addPackageImport)
		{
			Import wholePackage = getProgramFactory().createImport(PackageKit.createPackageReference(getProgramFactory(), pack));
			boolean contains = false;

			for (Import i : imports)
			{
				if ((i.toString().equals(wholePackage.toString())))
				{
					contains = true;
					break;
				}
			}

			if (!contains)
				imports.add(wholePackage);
		}
				
		UnitKit.getCompilationUnit(this.subDeclaration).setImports(imports);

		// Specify refactoring information for results information.
		super.refactoringInfo = "Iteration " + iteration + ": \"Move Field Down\" applied to field " 
				+ pe.toString().substring(last + 2) + " from " + this.currentDeclaration.getName() + " to " + this.subDeclaration.getName();

		return setProblemReport(EQUIVALENCE);
	}

	public ProblemReport analyzeReverse() 
	{
		// Initialise and pick the element to visit.
		FieldDeclaration fd = (FieldDeclaration) this.subDeclaration.getMembers().get(this.randomPosition);
				
		// Find new "super." references in field and change them back to "this." references.
		for (SuperReference sr : this.thisReferences)
		{
			sr.setReferencePrefix(null);
			sr.getASTParent().replaceChild(sr, new ThisReference());	
		}
				
		// Any "this." references to the field in the sub class are changed back to "super." references.
		for (VariableReference vr : this.superReferences)
			if (((FieldReference) vr).getReferencePrefix() instanceof ThisReference)
				((VariableReference) vr).replaceChild(((FieldReference) vr).getReferencePrefix(), new SuperReference());
				
		// Construct refactoring transformation.
		super.transformation = null;
		detach(fd);
		attach(fd, this.currentDeclaration, this.position);
		
		// If there was a duplicate of the field in the class originally it is added back.
		if (duplicatePosition != -1)
		{
			FieldDeclaration dec = fd.deepClone();
			attach(dec, this.subDeclaration, this.duplicatePosition);
		}
		
		UnitKit.getCompilationUnit(this.subDeclaration).setImports(this.subDeclarationImports);		
		return setProblemReport(EQUIVALENCE);
	}
	
	public boolean mayRefactor(FieldDeclaration fd)
	{
		TypeDeclaration td =  MiscKit.getParentTypeDeclaration(fd);
		CrossReferenceSourceInfo si = getCrossReferenceSourceInfo();
		TypeDeclaration std;
		
		// Makes initial checks against the field and the class in order to quickly exclude insufficient candidates. 
		if (!(td.isOrdinaryClass()) || (fd.isPrivate()))
			return false;
		else
		{			
			// Are there any references to the field in the class.
			for (FieldSpecification fs : fd.getFieldSpecifications())
				if (VariableKit.getReferences(si, fs, td, true).size() > 0)
					return false;

			// Allows the program to go through the child classes at random
			// and pick the first one (if any) that is applicable for the refactoring.
			List<ClassType> subtypes = si.getSubtypes(td);
			
			// Check each child class to see if the field can be accessed from the class.
			for (int i = 0; i < subtypes.size(); i++)
			{
				if (subtypes.get(i) instanceof ClassDeclaration)
					std = (TypeDeclaration) si.getSubtypes(td).get(i);
				else continue;
				
				boolean next = false;
				
				// Checks if a field with the same name is in the sub class that isn't identical.
				for (MemberDeclaration md : std.getMembers())
					if ((md.toString().equals(fd.toString())) && !(md.toSource().equals(fd.toSource())))
						next = true;
				
				if (next)
					continue;
				
				// If the class implements any interfaces and the sub class doesn't implement the same interfaces.
				if ((((ClassDeclaration) td).getImplementedTypes() != null) && 
					!(((ClassDeclaration) td).getImplementedTypes().equals(((ClassDeclaration) std).getImplementedTypes()))) 
					next = true;
				
				if (next)
					continue;

				// Checks if the sub class already contains a field with the same name.
				for (MemberDeclaration md : std.getMembers())
				{
					if (md instanceof FieldDeclaration)
					{
						int lastmd = md.toString().lastIndexOf(">");
						int lastfd = fd.toString().lastIndexOf(">");
						if ((fd.toString().substring(lastfd + 2).equals(md.toString().substring(lastmd + 2))) && !(fd.equals(md)))
						{
							next = true;
							break;
						}
					}
				}
				
				if (next)
					continue;
				
				// Get any methods referenced in field.
				ArrayList<MethodReference> methods = super.getMethods(fd);

				// Check if methods can be accessed in sub type.
				for (MethodReference mr : methods)
				{
					Method m = si.getMethod(mr);

					if (m.isPrivate())
					{
						if (!(m.getContainingClassType().equals(std)))
						{
							next = true;
							break;
						}
					}
					else if (!m.isPublic())
					{
						if (!m.getPackage().equals(std.getPackage()))
						{
							if (!m.isProtected())
							{
								next = true;
								break;
							}
							else if (!(std.getAllSupertypes().contains(m.getContainingClassType())))
							{
								next = true;
								break;
							}
						}
					}
				}

				if (next)
					continue;
				
				// Check if there are any other fields used by the field.
				ArrayList<Field> fields = super.getFields(fd, si);

				// Check if fields can be accessed in super type.
				for (Field f : fields)
				{
					for (FieldSpecification fs : fd.getFieldSpecifications())
						if (f.equals(fs))
							continue;
					
					if (f.isPrivate())
					{
						if (!(f.getContainingClassType().equals(std)))
						{
							next = true;
							break;
						}
					}
					else if (!f.isPublic())
					{
						if (!f.getContainingClassType().getPackage().equals(std.getPackage()))
						{
							if (!f.isProtected())
							{
								next = true;
								break;
							}
							else if (!(std.getAllSupertypes().contains(f.getContainingClassType())))
							{
								next = true;
								break;
							}
						}
					}
				}

				if (next)
					continue;
				
				// Get types accessed by field.
				ArrayList<Type> types = super.getTypes(fd, si);

				// Check if types can be accessed in super type.
				for (Type t: types)
				{
					if (((t instanceof TypeDeclaration) || (t instanceof ClassFile)))
					{
						if (((ClassType) t).isPrivate())
						{
							if (!(((ClassType) t).equals(std)))
							{
								next = true;
								break;
							}
						}
						else if (!((ClassType) t).isPublic())
						{
							if (!((ClassType) t).getPackage().equals(std.getPackage()))
							{
								if (!((ClassType) t).isProtected())
								{
									next = true;
									break;
								}
								else if (!(std.getAllSupertypes().contains((ClassType) t)))
								{
									next = true;
									break;
								}
							}
						}
					}
				}
				
				if (next)
					continue;
				
				// Checks any reference to the field in the program and if the class used to reference the
				// field is not the current child class then the child class will not be applicable.
				// An exception is if the field reference is a super reference in the child class. In this
				// case the reference can be modified during the refactoring to point to the right class.
				for (FieldSpecification fs : fd.getFieldSpecifications())
				{
					for (FieldReference fr : si.getReferences(fs))
					{
						// Check whether the field can be accessed from the class.
						if (!(std.getPackage().equals(MiscKit.getParentTypeDeclaration(fr).getPackage())) && !(fd.isPublic()))
						{
							if (!(fd.isProtected()))
							{
								next = true;
								break;
							}
							else if (!(MiscKit.getParentTypeDeclaration(fr).getAllSupertypes().contains(std)))
							{
								next = true;
								break;
							}
						}

						// If reference is "this." or "super." or has no prefix 
						// it needs to be in the sub class or one of its sub classes.
						if ((fr.getReferencePrefix() instanceof ThisReference) || (fr.getReferencePrefix() instanceof SuperReference) ||
							(fr.getReferencePrefix() == null))
						{
							if (!(MiscKit.getParentTypeDeclaration(fr).equals(std)) &&
								!(si.getAllSubtypes(std).contains(MiscKit.getParentTypeDeclaration(fr))))
							{
								next = true;
								break;
							}
						}
						// If the reference is prefixed by a type reference, it needs to be a
						// static field and the type reference needs to be the type of the sub class. 
						else if (fr.getReferencePrefix() instanceof TypeReference)
						{
							if (!(fr.getReferencePrefix().toSource().equals(std.getName())) || !(fd.isStatic()))
							{
								next = true;
								break;
							}
						}
						// If the reference if being called from a variable, the variable needs to be the type of the sub class or
						// one of its sub classes (in this case, if the field is a package type, it will need to be accessible).
						else
						{
							if (!(si.getType(fr.getReferencePrefix()).equals(std)))
							{
								if (!(si.getAllSubtypes(std).contains(si.getType(fr.getReferencePrefix()))) || 
									(!(fd.isProtected()) && !(fd.isPublic()) && 
									!(((TypeDeclaration) si.getType(fr.getReferencePrefix())).getPackage().equals(std.getPackage()))))
								{
									next = true;
									break;
								}
							}
						}
					}
					
					if (next)
						break;
				}

				if (!next)
				{
					this.subDeclaration = std;
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
		while (tw.next(FieldDeclaration.class))
		{
			FieldDeclaration fd = (FieldDeclaration) tw.getProgramElement();
			if (mayRefactor(fd))
				counter++;
		}

		return counter;
	}
	
	public String getName(int unit, int element)
	{		
		AbstractTreeWalker tw = new TreeWalker(getSourceFileRepository().getKnownCompilationUnits().get(unit));

		for (int i = 0; i < element; i++)
		{
			tw.next(FieldDeclaration.class);
			FieldDeclaration fd = (FieldDeclaration) tw.getProgramElement();
			if (!mayRefactor(fd))
				i--;
		}

		FieldDeclaration fd = (FieldDeclaration) tw.getProgramElement();
		return fd.toString();
	}
}
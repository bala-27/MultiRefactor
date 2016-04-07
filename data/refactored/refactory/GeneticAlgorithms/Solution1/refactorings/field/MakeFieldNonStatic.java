package refactorings.field;

import recoder.CrossReferenceServiceConfiguration;
import recoder.convenience.AbstractTreeWalker;
import recoder.convenience.TreeWalker;
import recoder.java.ProgramElement;
import recoder.java.declaration.FieldDeclaration;
import recoder.kit.Problem;
import recoder.kit.ProblemReport;
import recoder.kit.transformation.Modify;
import refactorings.Refactoring;
import refactory.AccessFlags;

public class MakeFieldNonStatic extends Refactoring {
    public MakeFieldNonStatic(CrossReferenceServiceConfiguration sc)
     {
        super(sc);
    }

    public MakeFieldNonStatic()
     {
        super();
    }

    public ProblemReport analyze(int iteration, int unit, int element)
     {
        // Initialise and pick the element to visit.
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

        // Find iterator in declaration list.
        int counter = -1;
        for (int i = 0; i < fd.getDeclarationSpecifiers().size(); i++)
            if (fd.getDeclarationSpecifiers().get(i).toString().contains("Static"))
                counter = i;

        // Construct refactoring transformation.
        // The transformation is handled here manually and the transformation
        // method will do nothing for this refactoring when it is called.
        super.transformation = null;
        getChangeHistory().begin(this);
        detach(fd.getDeclarationSpecifiers().get(counter));

        // Specify refactoring information for results information.
        super.refactoringInfo = "Iteration " + iteration + ": \"Make Field Non Static\" applied at class " + super.getFileName(getSourceFileRepository().getKnownCompilationUnits().get(unit).getName()) + " to element " + pe.getClass().getSimpleName() + " (" + pe.toString().substring(last + 2) + ")";

        return setProblemReport(EQUIVALENCE);
    }

    public ProblemReport analyzeReverse()
     {
        // Initialise and pick the element to visit.
        CrossReferenceServiceConfiguration config = getServiceConfiguration();
        ProblemReport report = EQUIVALENCE;
        FieldDeclaration fd = (FieldDeclaration) super.tw.getProgramElement();

        // Construct refactoring transformation.
        super.transformation = new Modify(config, true, fd, AccessFlags.STATIC);
        report = super.transformation.analyze();
        if (report instanceof Problem)
            return setProblemReport(report);

        return setProblemReport(EQUIVALENCE);
    }

    public boolean mayRefactor(FieldDeclaration fd)
     {
        if (fd.isStatic())
            return true;
         else
            return false;
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
            //counter++;
            FieldDeclaration fd = (FieldDeclaration) tw.getProgramElement();
            if (mayRefactor(fd))
                counter++;
        }

        return counter;
    }

    public int getID(int unit, int element)
     {
        super.tw = new TreeWalker(getSourceFileRepository().getKnownCompilationUnits().get(unit));

        for (int i = 0; i < element; i++)
         {
            super.tw.next(FieldDeclaration.class);
            FieldDeclaration fd = (FieldDeclaration) super.tw.getProgramElement();
            if (!mayRefactor(fd))
                i--;
        }

        return super.tw.getProgramElement().getID();
    }
}
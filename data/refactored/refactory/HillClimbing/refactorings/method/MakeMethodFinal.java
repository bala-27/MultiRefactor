package refactorings.method;

import recoder.CrossReferenceServiceConfiguration;
import recoder.convenience.AbstractTreeWalker;
import recoder.convenience.TreeWalker;
import recoder.java.ProgramElement;
import recoder.java.declaration.MethodDeclaration;
import recoder.kit.Problem;
import recoder.kit.ProblemReport;
import recoder.kit.transformation.Modify;
import refactorings.Refactoring;
import refactory.AccessFlags;

public abstract class MakeMethodFinal extends Refactoring {
    public MakeMethodFinal(CrossReferenceServiceConfiguration sc)
     {
        super(sc);
    }

    public MakeMethodFinal()
     {
        super();
    }

    public abstract ProblemReport analyze(int iteration, int unit, int element)
     {
        // Initialise and pick the element to visit.
        CrossReferenceServiceConfiguration config = getServiceConfiguration();
        ProblemReport report = EQUIVALENCE;
        super.tw = new TreeWalker(getSourceFileRepository().getKnownCompilationUnits().get(unit));

        for (int i = 0; i < element; i++)
         {
            super.tw.next(MethodDeclaration.class);
            MethodDeclaration md = (MethodDeclaration) super.tw.getProgramElement();
            if (!mayRefactor(md))
                i--;
        }

        ProgramElement pe = super.tw.getProgramElement();
        MethodDeclaration md = (MethodDeclaration) pe;

        // Construct refactoring transformation.
        super.transformation = new Modify(config, true, md, AccessFlags.FINAL);
        report = super.transformation.analyze();
        if (report instanceof Problem)
            return setProblemReport(report);

        // Specify refactoring information for results information.
        super.refactoringInfo = "Iteration " + iteration + ": \"Make Method Final\" applied at class " + super.getFileName(getSourceFileRepository().getKnownCompilationUnits().get(unit).getName()) + " to element " + pe.getClass().getSimpleName() + " (" + ((MethodDeclaration) pe).getName() + ")";

        return setProblemReport(EQUIVALENCE);
    }

    public abstract ProblemReport analyzeReverse()
     {
        // Initialise and pick the element to visit.
        //CrossReferenceServiceConfiguration config = getServiceConfiguration();
        ProblemReport report = EQUIVALENCE;
        //MethodDeclaration md = (MethodDeclaration) super.tw.getProgramElement();

        // Construct refactoring transformation.
        //super.transformation = new Modify(config, true, md, AccessFlags.FINAL);
        super.transformation = null;
        report = super.transformation.analyze();
        if (report instanceof Problem)
            return setProblemReport(report);

        return setProblemReport(EQUIVALENCE);
    }

    public abstract boolean mayRefactor(MethodDeclaration md)
     {
        if (md.isFinal())
            return false;
         else
            return true;
    }

    // Count the amount of available elements in the chosen class for refactoring.
    // If an element is not applicable for the current refactoring it is not counted.
    public abstract int getAmount(int unit)
     {
        int counter = 0;
        AbstractTreeWalker tw = new TreeWalker(getSourceFileRepository().getKnownCompilationUnits().get(unit));

        // Only counts the relevant program element.
        while (tw.next(MethodDeclaration.class))
         {
            //counter++;
            MethodDeclaration md = (MethodDeclaration) tw.getProgramElement();
            if (mayRefactor(md))
                counter++;
        }

        return counter;
    }
}

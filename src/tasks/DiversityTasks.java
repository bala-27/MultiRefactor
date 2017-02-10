package tasks;

import java.util.ArrayList;

import multirefactor.Configuration;
import recoder.CrossReferenceServiceConfiguration;
import recoder.ParserException;
import recoder.io.PropertyNames;
import refactorings.Refactoring;
import refactorings.field.*;
import refactorings.method.*;
import refactorings.type.*;
import searches.MonoObjectiveSearch;
import searches.MultiObjectiveSearch;
import searches.Search;

public class DiversityTasks extends Tasks
{
	// No attributes - empty constructor.
	public DiversityTasks()
	{
		super();
	}

	public DiversityTasks(String pathway)
	{
		super(pathway);		
	}
	
	public void run()
	{		
		String[] input = new String[]{
				"./data/original/mango",
				"./data/original/beaver-0.9.11",
				"./data/original/apachexmlrpc-2.0",
				"./data/original/jhotdraw-5.3", 
				"./data/original/ganttproject-1.11.1",
				"./data/original/xom-1.2.1"};
		
		// Create an initial service configuration to be overwritten.
		// Reads the source code from the specified directory.
		CrossReferenceServiceConfiguration sc = new CrossReferenceServiceConfiguration();
		String[][] sourceFiles = new String[][]{
			super.read("./data/original/mango"),
			super.read("./data/original/beaver-0.9.11"),
			super.read("./data/original/apachexmlrpc-2.0"),
			super.read("./data/original/jhotdraw-5.3"), 
			super.read("./data/original/ganttproject-1.11.1"),
			super.read("./data/original/xom-1.2.1")};
		
		// Create empty list of refactorings.
		// Reads the metric configuration in from a specified text file.
		ArrayList<Refactoring> refactorings = new ArrayList<Refactoring>();
		Configuration c = new Configuration("./configurations/qualityfunction.txt", refactorings);
		Configuration[] cM = {new Configuration("./configurations/qualityfunction.txt"), new Configuration("./configurations/diversity.txt")};
		
		// Initialise search tasks.
		ArrayList<Search> searches = new ArrayList<Search>();
		MonoObjectiveSearch geneticAlgorithm1 = new MonoObjectiveSearch(sc, c, sourceFiles[0], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm1.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm1);
		MonoObjectiveSearch geneticAlgorithm2 = new MonoObjectiveSearch(sc, c, sourceFiles[0], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm2.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm2);
		MonoObjectiveSearch geneticAlgorithm3 = new MonoObjectiveSearch(sc, c, sourceFiles[0], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm3.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm3);
		MonoObjectiveSearch geneticAlgorithm4 = new MonoObjectiveSearch(sc, c, sourceFiles[0], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm4.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm4);
		MonoObjectiveSearch geneticAlgorithm5 = new MonoObjectiveSearch(sc, c, sourceFiles[0], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm5.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm5);
		
		MonoObjectiveSearch geneticAlgorithm6 = new MonoObjectiveSearch(sc, c, sourceFiles[1], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm6.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm6);
		MonoObjectiveSearch geneticAlgorithm7 = new MonoObjectiveSearch(sc, c, sourceFiles[1], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm7.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm7);
		MonoObjectiveSearch geneticAlgorithm8 = new MonoObjectiveSearch(sc, c, sourceFiles[1], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm8.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm8);
		MonoObjectiveSearch geneticAlgorithm9 = new MonoObjectiveSearch(sc, c, sourceFiles[1], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm9.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm9);
		MonoObjectiveSearch geneticAlgorithm10 = new MonoObjectiveSearch(sc, c, sourceFiles[1], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm10.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm10);
		
		MonoObjectiveSearch geneticAlgorithm11 = new MonoObjectiveSearch(sc, c, sourceFiles[2], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm11.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm11);
		MonoObjectiveSearch geneticAlgorithm12 = new MonoObjectiveSearch(sc, c, sourceFiles[2], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm12.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm12);
		MonoObjectiveSearch geneticAlgorithm13 = new MonoObjectiveSearch(sc, c, sourceFiles[2], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm13.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm13);
		MonoObjectiveSearch geneticAlgorithm14 = new MonoObjectiveSearch(sc, c, sourceFiles[2], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm14.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm14);
		MonoObjectiveSearch geneticAlgorithm15 = new MonoObjectiveSearch(sc, c, sourceFiles[2], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm15.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm15);
		
		MonoObjectiveSearch geneticAlgorithm16 = new MonoObjectiveSearch(sc, c, sourceFiles[3], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm16.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm16);
		MonoObjectiveSearch geneticAlgorithm17 = new MonoObjectiveSearch(sc, c, sourceFiles[3], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm17.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm17);
		MonoObjectiveSearch geneticAlgorithm18 = new MonoObjectiveSearch(sc, c, sourceFiles[3], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm18.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm18);
		MonoObjectiveSearch geneticAlgorithm19 = new MonoObjectiveSearch(sc, c, sourceFiles[3], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm19.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm19);
		MonoObjectiveSearch geneticAlgorithm20 = new MonoObjectiveSearch(sc, c, sourceFiles[3], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm20.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm20);
		
		MonoObjectiveSearch geneticAlgorithm21 = new MonoObjectiveSearch(sc, c, sourceFiles[4], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm21.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm21);
		MonoObjectiveSearch geneticAlgorithm22 = new MonoObjectiveSearch(sc, c, sourceFiles[4], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm22.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm22);
		MonoObjectiveSearch geneticAlgorithm23 = new MonoObjectiveSearch(sc, c, sourceFiles[4], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm23.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm23);
		MonoObjectiveSearch geneticAlgorithm24 = new MonoObjectiveSearch(sc, c, sourceFiles[4], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm24.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm24);
		MonoObjectiveSearch geneticAlgorithm25 = new MonoObjectiveSearch(sc, c, sourceFiles[4], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm25.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm25);
		
		MonoObjectiveSearch geneticAlgorithm26 = new MonoObjectiveSearch(sc, c, sourceFiles[5], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm26.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm26);
		MonoObjectiveSearch geneticAlgorithm27 = new MonoObjectiveSearch(sc, c, sourceFiles[5], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm27.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm27);
		MonoObjectiveSearch geneticAlgorithm28 = new MonoObjectiveSearch(sc, c, sourceFiles[5], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm28.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm28);
		MonoObjectiveSearch geneticAlgorithm29 = new MonoObjectiveSearch(sc, c, sourceFiles[5], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm29.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm29);
		MonoObjectiveSearch geneticAlgorithm30 = new MonoObjectiveSearch(sc, c, sourceFiles[5], true, 100, 50, 0.2f, 0.8f);
		geneticAlgorithm30.setInitialRefactoringRange(50);
		searches.add(geneticAlgorithm30);
		
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm1 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[0], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm1.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm1);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm2 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[0], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm2.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm2);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm3 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[0], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm3.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm3);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm4 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[0], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm4.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm4);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm5 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[0], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm5.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm5);
		
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm6 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[1], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm6.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm6);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm7 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[1], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm7.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm7);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm8 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[1], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm8.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm8);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm9 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[1], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm9.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm9);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm10 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[1], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm10.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm10);
		
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm11 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[2], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm11.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm11);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm12 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[2], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm12.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm12);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm13 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[2], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm13.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm13);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm14 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[2], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm14.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm14);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm15 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[2], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm15.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm15);
		
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm16 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[3], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm16.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm16);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm17 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[3], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm17.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm17);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm18 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[3], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm18.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm18);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm19 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[3], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm19.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm19);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm20 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[3], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm20.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm20);
		
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm21 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[4], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm21.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm21);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm22 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[4], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm22.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm22);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm23 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[4], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm23.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm23);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm24 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[4], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm24.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm24);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm25 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[4], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm25.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm25);
		
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm26 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[5], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm26.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm26);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm27 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[5], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm27.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm27);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm28 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[5], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm28.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm28);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm29 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[5], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm29.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm29);
		MultiObjectiveSearch MultiObjectiveGeneticAlgorithm30 = new MultiObjectiveSearch(sc, cM, refactorings, sourceFiles[5], 100, 50, 0.2f, 0.8f);
		MultiObjectiveGeneticAlgorithm30.setInitialRefactoringRange(50);
		searches.add(MultiObjectiveGeneticAlgorithm30);
		
		// Create list of output directories for
		// each refactored project to be written to.
		String[] output = new String[]{
				"./data/refactored/DiversityExperiment/Mono-Objective/",
				"./data/refactored/DiversityExperiment/Multi-Objective/"};

		// Create list of output directories for
		// each result data output to be written to.
		String[] resultsDir = new String[]{
				"./results/DiversityExperiment/Mono-Objective/",
				"./results/DiversityExperiment/Multi-Objective/"};

		long timeTaken, startTime = System.currentTimeMillis();
		double time;
		
		for (int i = 0; i < searches.size(); i++)
		{
			// Creates new service configuration to start from scratch.
			sc = new CrossReferenceServiceConfiguration();
			int search = (i < 30) ? 0 : 1;
			int path = (int) Math.floor(i/5);
			int run = (i % 5) + 1;

			if (path > 5)
				path -= 6;
			
			String outputPath = output[search] + input[path].substring(input[path].lastIndexOf("/") + 1) + "/" + run + "/";
			String resultsPath = resultsDir[search] + input[path].substring(input[path].lastIndexOf("/") + 1) + "/" + run + "/";

			// Initialise available refactorings. Needs to be done each 
			// time as the service configuration won't be updated otherwise.
			refactorings = new ArrayList<Refactoring>();
			DecreaseMethodSecurity dms = new DecreaseMethodSecurity(sc);
			refactorings.add(dms);
			DecreaseFieldSecurity dfs = new DecreaseFieldSecurity(sc);
			refactorings.add(dfs);
			IncreaseMethodSecurity ims = new IncreaseMethodSecurity(sc);
			refactorings.add(ims);
			IncreaseFieldSecurity ifs = new IncreaseFieldSecurity(sc);
			refactorings.add(ifs);
			MakeClassAbstract mca = new MakeClassAbstract(sc);
			refactorings.add(mca);
			MakeClassConcrete mcc = new MakeClassConcrete(sc);
			refactorings.add(mcc);
			MakeClassFinal mcf = new MakeClassFinal(sc);
			refactorings.add(mcf);
			MakeMethodFinal mmf = new MakeMethodFinal(sc);
			refactorings.add(mmf);
			MakeFieldFinal mff = new MakeFieldFinal(sc);
			refactorings.add(mff);
			MakeClassNonFinal mcnf = new MakeClassNonFinal(sc);
			refactorings.add(mcnf);
			MakeMethodNonFinal mmnf = new MakeMethodNonFinal(sc);
			refactorings.add(mmnf);
			MakeFieldNonFinal mfnf = new MakeFieldNonFinal(sc);
			refactorings.add(mfnf);
			MakeMethodStatic mms = new MakeMethodStatic(sc);
			refactorings.add(mms);
			MakeFieldStatic mfs = new MakeFieldStatic(sc);
			refactorings.add(mfs);
			MakeMethodNonStatic mmns = new MakeMethodNonStatic(sc);
			refactorings.add(mmns);
			MakeFieldNonStatic mfns = new MakeFieldNonStatic(sc);
			refactorings.add(mfns);
			MoveMethodUp mmu = new MoveMethodUp(sc);
			refactorings.add(mmu);
			MoveFieldUp mfu = new MoveFieldUp(sc);
			refactorings.add(mfu);
			MoveMethodDown mmd = new MoveMethodDown(sc);
			refactorings.add(mmd);	
			MoveFieldDown mfd = new MoveFieldDown(sc);
			refactorings.add(mfd);
			RemoveInterface ri = new RemoveInterface(sc);
			refactorings.add(ri);
			RemoveClass rc = new RemoveClass(sc);
			refactorings.add(rc);
			RemoveMethod rm = new RemoveMethod(sc);
			refactorings.add(rm);
			RemoveField rf = new RemoveField(sc);
			refactorings.add(rf);
			ExtractSubclass es = new ExtractSubclass(sc);
			refactorings.add(es);
			CollapseHierarchy ch = new CollapseHierarchy(sc);
			refactorings.add(ch);
			
			try 
			{
				// Read the original input.
				sc.getSourceFileRepository().getCompilationUnitsFromFiles(sourceFiles[path]);
			}
			catch (ParserException e) 
			{
				System.out.println("\r\nEXCEPTION: Cannot read input.");
				System.exit(1);
			}
			
			// Set up initial properties of service configuration.
			sc.getProjectSettings().setProperty(PropertyNames.INPUT_PATH, input[path] + super.readLibs(input[path]));
			sc.getProjectSettings().setProperty(PropertyNames.OUTPUT_PATH, outputPath);
			sc.getProjectSettings().ensureSystemClassesAreInPath();

			// initialise search task.			
			if (searches.get(i).getClass().getName().contains("MultiObjectiveSearch"))
				((MultiObjectiveSearch) searches.get(i)).setRefactorings(refactorings);
			else
			{
				c = new Configuration("./configurations/qualityfunction.txt", refactorings);
				searches.get(i).setConfiguration(c);
			}
			
			searches.get(i).setServiceConfiguration(sc);
			searches.get(i).setResultsPath(resultsPath);
			searches.get(i).run();
		}	

		// Output overall time taken to console.
		timeTaken = System.currentTimeMillis() - startTime;
		time = timeTaken / 1000.0;
		System.out.printf("\r\n\r\nTime taken to run program: %.2fs", time);
	}
}
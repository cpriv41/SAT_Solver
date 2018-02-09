package a1;

import java.io.FileNotFoundException;
//import java.util.*;

public class MainCalebPrivitera {
	
		 public static void main(String[] args) throws FileNotFoundException{
			        
  		    	if (args.length < 1) {
					System.out.println("Must specify inputFileName from command line");
					System.exit(1);
		    	}
		        if (args.length == 1) {
		            run(args[0]);
		      
		        } else if (args.length > 1) {
		            for (String input : args) {
		                run(input);
		            }
		        } else {
		        	System.out.println("Must specify inputFileName from command line");
					System.exit(1);
		        }
		    }

		    /** Helper method to run SAT problem
		     * @param SATFile
		     */
		    private static void run(String input) throws FileNotFoundException {
		    	
		    	DPSolverCalebPrivitera DPSolver = new DPSolverCalebPrivitera();
		        TimerCalebPrivitera timer = new TimerCalebPrivitera();
		     
		     
		        System.out.println("Processing input file: " + input);
		        timer.start();
		        DPSolver.solve(DPSolver.readFormula(input));
		        timer.stop();
		        System.out.println("Time elapsed: " + timer.getDuration() + "ms");
		    }

}

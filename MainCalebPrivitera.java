
package a1;

import java.util.*;

public class MainCalebPrivitera {
	public static void main(String[] args) {
		
		if (args.length < 1) {
			System.out.println("Must specify inputFileName on command line");
			System.exit(1);
}

		DPSolverCalebPrivitera DPSolver = new DPSolverCalebPrivitera(args[0]);
		TimerCalebPrivitera timer = new TimerCalebPrivitera();

		timer.start();
		DPSolver.solve();
		timer.stop();

		System.out.println("Elasped time: " + timer.getDuration());

		FormulaReaderCalebPrivitera temp = new FormulaReaderCalebPrivitera();
		temp.read(args[0]);

		
	}
}

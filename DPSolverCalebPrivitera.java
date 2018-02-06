package a1;

import java.io.*;


public class DPSolverCalebPrivitera {
	

	
	
	/**
	 * Read formula by passing inputFileName to FormulaCalebPrivitera
	 * @param inputFileName
	 *
	 * @throws FileNotFoundException
	 */
	
	public FormulaCalebPrivitera read(String inputFileName) throws FileNotFoundException {
		
		FormulaCalebPrivitera inputFormula;
		
		inputFormula = read(inputFileName);
		
		return inputFormula;
	}
	
	/**
	 * Takes formula and attempts to satisfy it
	 * @param f
	 */
	
	boolean dpSolver(FormulaCalebPrivitera f) {
		
		if(f.isFormulaEmpty()) {
			return true;
		}
		else if (f.hasEmptyClause()) {
			return false;
		}
		else {
			int var = f.firstAvailable();
			f.assign(var, true);
			
			if(dpSolver(f))
				return true;
			else {
				var = 0;
				f.assign(var, false);
				if(dpSolver(f))
					return true;
				else {
					var = 0;
					f.assign(var, false);
					return false;
					}
					
				}
			}
		}
	
	/**
	 * Initiates the recursive call to dpSolver, prints result
	 * @param f
	 */
	
	public void solve(FormulaCalebPrivitera f) {
		
		if (dpSolver(f) == true) {
			System.out.println("Formula result is SATISFIED.");
			inputFormula.print();
		}
		else {
			System.out.println("Formula result is UNSATISFIED.");
		}
		
	}
	
	
}

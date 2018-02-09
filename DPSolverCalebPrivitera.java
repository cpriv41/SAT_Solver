package a1;


import java.io.*;


public class DPSolverCalebPrivitera {
	
	FormulaCalebPrivitera inputFormula;
	
	
	/**
	 * Read formula by passing inputFileName to FormulaCalebPrivitera
	 * @param inputFileName
	 *
	 * @throws FileNotFoundException
	 */
	public FormulaCalebPrivitera readFormula(String inputFileName) throws FileNotFoundException {
		
		inputFormula = new FormulaCalebPrivitera();
		
		try {
			inputFormula.read(inputFileName);
		} catch (FileNotFoundException ex) {
			// if no file is found, print error

			System.out.println("File not found.");
			throw (ex);
		}
		return inputFormula;
	}
	
	/**
	 * Takes formula and attempts to satisfy it
	 * @param f
	 */
	
	static boolean dpSolver(FormulaCalebPrivitera f) {
		
		if(f.isFormulaEmpty()) {
			return true;
		} else if (f.hasEmptyClause()) {
			return false;
		} else {
			int var = f.firstAvailable();
			
			if (var == FormulaCalebPrivitera.FALSE)
				return false; // no more unassigned variables
			
			f.assign(var, true);
			
			if(dpSolver(f))
				return true;
			else {
				//unset the variable and restore the previous link list
				// unsetting the variable is simply set the variable to
				//zero. Restoring the previous linked list is achieved
				//by dropping the first occurrence of -1.  
				for( int i = 0; i < f.sublist.size(); i++){
					if(f.sublist.get(i) == -1){
						f.sublist.remove(i);
						break;
					}
				} 
				
				f.assign(var, false);
				
				if(dpSolver(f)) {
					return true;
				} else {
					//unset the variable and restore the previous link list
					// unsetting the variable is simply set the variable to
					//zero. Restoring the previous linked list is achieved
					//by dropping the first occurrence of -1.  
					 		
					for( int i = 0; i < f.sublist.size(); i++){
						if(f.sublist.get(i) == -1){
							f.sublist.remove(i);
							break;
						}
					} 
					f.unassign(var);
					return false;
				}
			}
		}
	}
	
	/**
	 * Initiates the recursive call to dpSolver, prints result
	 * @param f
	 */
	
	public static void solve(FormulaCalebPrivitera f) {
		
		if (dpSolver(f) == true) {
			System.out.println("Formula result is SATISFIED.");
			f.print();
		} else {
			System.out.println("Formula result is UNSATISFIED.");
		}
		
	}
	
}

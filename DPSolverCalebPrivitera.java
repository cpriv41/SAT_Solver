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
		
		inputFormula.read(inputFileName);
		
		return inputFormula;
	}
	
	/**
	 * Takes formula and attempts to satisfy it
	 * @param f
	 */
	
	static boolean dpSolver(FormulaCalebPrivitera f) {
		
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
			//	tried fixing unset of var and removal of first -1
			else {
				f.sublist.set(var, 0);
				for( int i : f.sublist){
					if(f.sublist.get(i) == -1){
						f.sublist.remove(i);
						break;
					}
				}
				
				f.assign(var, false);
				
				if(dpSolver(f)) {
					return true;
				}else{
					f.sublist.set(var, 0);
					for( int i : f.sublist){
						if(f.sublist.get(i) == -1){
							f.sublist.remove(i);
							break;
						}
					}
					return false;
				}
				
				
				
				
				
				
				/*
				//unset the variable and restore the previous link list
				// unsetting the variable is simply set the variable to
				//zero. Restoring the previous linked list is achieved
				//by dropping the first occurrence of -1.  
				f.tValue[var] = 0;
				int temp = 0;
				for ( int i : formula){
					if ( i = -1){
						f.formula.remove(i);
						break;
					}
				i++;
			}
				f.assign(var, false);
				if(dpSolver(f))
					return true;
				else {
					f.tValue[var] = 0;
					int temp = 0;
					for ( int i : f.formula){
						if ( i = -1){
							f.formula.remove(i);
							break;
					}
				i++;
			}
					f.assign(var, false);
					return false;
					}
					*/
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
		}
		else {
			System.out.println("Formula result is UNSATISFIED.");
		}
		
	}


	
	
}

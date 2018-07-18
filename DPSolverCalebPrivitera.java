package a1;
 
import java.io.*;

public class DPSolverCalebPrivitera {

	FormulaCalebPrivitera inputFormula;

	/**
	 * Read formula by passing inputFileName to FormulaCalebPrivitera
	 * 
	 * @param inputFileName
	 *
	 * @throws FileNotFoundException
	 */
	DPSolverCalebPrivitera(String inputFileName) throws FileNotFoundException {

		inputFormula = new FormulaCalebPrivitera(inputFileName);

	}

	/**
	 * Takes formula and attempts to satisfy it
	 * 
	 * @param f
	 */

	static boolean dpSolver(FormulaCalebPrivitera f) {

		if (f.isFormulaEmpty()) {
			return true;
		} else if (f.hasEmptyClause()) {
			return false;
		} else {
			int var = f.firstAvailable();

			if (var == FormulaCalebPrivitera.FALSE) {
				return true; // no more unassigned variables
			}

			f.assign(var, true);
			
			if (dpSolver(f))
				return true;
			else {
				/**unset the variable and restore the previous link list
				 * unsetting the variable is simply set the variable to
				 * zero. Restoring the previous linked list is achieved
				 * by dropping the first occurrence of -1.
				 */
				for (int i = 0; i < f.sublist.size(); i++) {
					if (f.sublist.get(i) == -1) {
						f.sublist.remove(i);
						break;
					}
				}

				f.deepCopyInFormula();
				f.assign(var, false);

				if (dpSolver(f)) {
					return true;
				} else {
					/**unset the variable and restore the previous link list
					 * unsetting the variable is simply set the variable to
					 * zero. Restoring the previous linked list is achieved
					 * by dropping the first occurrence of -1.
					 */
					for (int i = 0; i < f.sublist.size(); i++) {
						if (f.sublist.get(i) == -1) {
							f.sublist.remove(i);
							break;
						}
					}
					f.deepCopyInFormula();
					f.unassign(var);
					return false;
				}
			}
		}
	}

	/**
	 * Initiates the recursive call to dpSolver, prints result
	 * 
	 * @param f
	 */

	public void solve() {

		if (dpSolver(inputFormula) == true && inputFormula.isFormulaEmpty()) {
			System.out.println("Formula result is SATISFIED.");
			inputFormula.printFormula();
			inputFormula.print();
		} else {
			System.out.println("Formula result is UNSATISFIED.");
			inputFormula.printFormula();
		}

	}
}

package a1;

import java.util.*;
import java.io.FileNotFoundException;

public class FormulaCalebPrivitera {

	FormulaReaderCalebPrivitera inFile;

	// Array to track truth values assigned to variables
	int[] tValue;

	// LinkedList to keep track of all sublists of clauses satisfied so far
	LinkedList<Integer> sublist = new LinkedList<>();

	// Local copy of number of variables, clauses and the formula
	int variables;
	int clauses;
	int[][] formula; // unsatisfied formula
	int[][] inFormula; // input formula

	// Markers
	final static int FALSE = -1;
	final static int UNASSIGNED = 0;
	final static int TRUE = 1;

	FormulaCalebPrivitera(String inputFile) throws FileNotFoundException {
		read(inputFile);
	}

	/**
	 * Returns true if the formula is empty, else returns false
	 */
	public boolean isFormulaEmpty() {

		return sublist.getFirst() == FALSE;
	}

	/**
	 * Returns true if a clause specified by <clauseNo> is empty, else returns false
	 * 
	 * @param clauseNo
	 * @return
	 */

	public boolean isClauseEmpty(int clauseNo) {

		for (int i : formula[clauseNo]) {
			if (i != 0 && tValue[Math.abs(i) - 1] == UNASSIGNED)
				return false;
		}
		return true;
	}

	/**
	 * Returns true if a formula contains an empty clause, else returns false
	 * 
	 */
	public boolean hasEmptyClause() {
		// Check all formulas in the first sublist
		for (int clause : sublist) {
			if (clause == FALSE)
				break;
			if (isClauseEmpty(clause))
				return true;
		}
		return false;
	}

	/**
	 * Returns the first available variable for backtracking
	 * 
	 */
	public int firstAvailable() {

		for (int i = 0; i < variables; i++) {
			if (tValue[i] == UNASSIGNED) {
				return i;
			}
		}
		return FALSE;
	}

	/**
	 * moves the first sublist in the linked list representation to a temp list
	 * 
	 */
	public LinkedList<Integer> separateClauses() {

		LinkedList<Integer> tempList = new LinkedList<>();
		int clause;

		if (sublist.size() > 0) {
			while ((clause = sublist.get(0)) != FALSE) {
				tempList.add(clause);
				sublist.remove(0);
			}
		}
		return tempList;
	}

	/**
	 * Assigns a truth value to the first available variable
	 * 
	 */
	public void assign(int var, boolean truth) {
		if (truth == true)
			tValue[var] = TRUE;
		else
			tValue[var] = FALSE;

		LinkedList<Integer> preSplit = separateClauses();
		LinkedList<Integer> satisfied = new LinkedList<>();
		LinkedList<Integer> unsatisfied = new LinkedList<>();

		// Unit Propagation
		boolean addedToList;
		for (int i : preSplit) {
			addedToList = false;
			// For all unsatisfied clauses
			for (int j = 0; j < formula[i].length; j++) {
				int v = formula[i][j];
				if ((Math.abs(v) - 1) != var)
					continue;
				// if literal matches value, erase clause; else erase literal
				if ((v > 0 && tValue[var] == TRUE) || (v < 0 && tValue[var] == FALSE)) {
					satisfied.add(i);
					addedToList = true;
					break;
				} else if ((v < 0 && tValue[var] == TRUE) || (v > 0 && tValue[var] == FALSE)) {
					// remove literal
					formula[i][j] = 0;
					unsatisfied.add(i);
					addedToList = true;
					break;
				}
			}
			if (!addedToList)
				unsatisfied.add(i);
		}

		// Reset the sublist to reflect the current status
		if (satisfied.size() > 0)
			sublist.addAll(0, satisfied);
		sublist.add(0, FALSE);
		sublist.addAll(0, unsatisfied);
	}

	/**
	 * Reset variable to default value.
	 * 
	 */
	public void unassign(int var) {
		tValue[var] = UNASSIGNED;
	}

	/**
	 * Returns true if the clause with label cno is satisfied, else returns false
	 * 
	 * @param cno
	 * 
	 */

	public boolean isClauseSatisfied(int cno) {
		for (int i : formula[cno]) {
			if (i != 0 && tValue[Math.abs(i) - 1] == TRUE)
				return true;
		}
		return false;
	}

	/**
	 * Displays the current assignment of the values of the variables
	 */
	public void printAssignment() {
		System.out.println("Current variable assignment:");
		System.out.print("[  ");
		for (int i = 0; i < variables; i++) {
			System.out.print("X" + (i + 1) + ": " + tValue[i] + " ");
			if (i > 0 && i % 10 == 0) {
				System.out.println(" ]");
				System.out.print("[  ");
			}

		}
		System.out.print(" ]");
		System.out.println();
	}

	/**
	 * Displays the formula in 2D format
	 */
	public void printFormula() {
		System.out.println("Initial Formula: ");

		for (int i = 0; i < clauses; i++) {
			System.out.print(i + ": " + "\t[\t");

			for (int j : inFormula[i]) {
				System.out.print(j + "\t");
			}
			System.out.print("]");
			System.out.println();
		}
	}

	/**
	 * Displays the linked list representation of the clause and the current
	 * assignment of the clauses of the variables
	 */
	public void print() {
		int listNo = 0;
		printAssignment();
		System.out.print("Unsatisfied List: ");
		for (int i : sublist) {
			if (i == FALSE)
				System.out.print("\n Satisfied List " + listNo++ + ": ");
			else
				System.out.print(i + " ");
		}
		System.out.println("");
	}

	/**
	 * Deep copy of formula, making unique copy, separate from original
	 */
	public void deepCopyInFormula() {
		formula = new int[inFormula.length][];
		for (int i = 0; i < inFormula.length; i++) {
			formula[i] = new int[inFormula[i].length];
			for (int j = 0; j < inFormula[i].length; j++)
				formula[i][j] = inFormula[i][j];
		}
	}

	public void read(String cnf) throws FileNotFoundException {

		inFile = new FormulaReaderCalebPrivitera();
		inFile.read(cnf);

		clauses = inFile.getNumClauses();
		variables = inFile.getNumVariables();
		inFormula = inFile.getFormula();
		deepCopyInFormula(); // initialize unsatisfied formula

		tValue = new int[variables];
		for (int i = 0; i < clauses; i++) {
			sublist.add(i);
		}
		sublist.add(FALSE); // initially, all clauses are unsatisfied
	}

}


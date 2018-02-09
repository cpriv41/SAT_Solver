package a1;

import java.util.*;
import java.io.FileNotFoundException;

public class FormulaCalebPrivitera {

  	static boolean setTrue = true;
	// Array to track truth values assigned to variables
	int [] tValue;

	// LinkedList to keep track of all sublists of clauses satisfied so far
	LinkedList<Integer> sublist = new LinkedList<>();

	// Local copy of number of variables, clauses and the formula
	int variables;
	int clauses;
	int[][] formula;

	// Markers
	final static int FALSE = -1;
	final static int UNASSIGNED = 0;
	final static int TRUE = 1;

	/**
	 * Returns true if the formula is empty, else returns false
	 */
	public boolean isFormulaEmpty() {

		return sublist.getFirst() == FALSE;
	}

	/**
	 * Returns true if a clause specified by <clauseNo> is empty, else returns
	 * false
	 * 
	 * @param clauseNo
	 * @return
	 */

	public boolean isClauseEmpty(int clauseNo) {
		for (int i : formula[clauseNo]) {
			if (tValue[Math.abs(i) - 1] == UNASSIGNED)
				return false;
		}
		return isClauseSatisfied(clauseNo);
	}

	/**
	 * Returns true if a formula contains an empty clause, else returns false
	 * 
	 */
	public boolean hasEmptyClause() {

		LinkedList<Integer> tempList = separateClauses();

		for (int i : tempList) {
			if (isClauseEmpty(i))
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
			if (tValue[i] != TRUE && tValue[i] != FALSE) {
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

		
		for (int clause : sublist){
			if (clause == FALSE)
				break;	// end of first sublist
			tempList.addLast(clause);
		}
		return tempList;

	}

	/**
	 * Assigns a truth value to the first available variable
	 * 
	 */
	public void assign(int var, boolean truth) {
		if (truth == true) {
			tValue[var] = TRUE;
		} else
			tValue[var] = FALSE;

		LinkedList<Integer> preSplit = new LinkedList<>(separateClauses());
		LinkedList<Integer> satisfied = new LinkedList<>();
		LinkedList<Integer> unsatisfied = new LinkedList<>();

		// Reset the sublist to reflect the current status
		sublist.clear();
		for (int i : preSplit) {
			if (isClauseSatisfied(i) == true) {
				satisfied.add(i);
			} else {
				unsatisfied.add(i);
			}
		}

		for (int i : unsatisfied)
			sublist.add(i);
		sublist.addLast(FALSE);
		for (int i : satisfied)
			sublist.add(i);
	}

	/**
	 * Assigns a truth value to the first available variable
	 * 
	 */
	public void unassign(int var) {
		tValue[var] = UNASSIGNED;
	}
	
/**
	 * Returns true if the clause with label cno is satisfied, else returns
	 * false
	 * 
	 * @param cno
	 * 
	 */

	public boolean isClauseSatisfied(int cno) {

		for (int i : formula[cno]) {
			if (tValue[Math.abs(i) - 1] == TRUE)
				return true;
		}
		return false;

	}

	/**
	 * Dsiplays the current assignment of the values of the variables
	 */
	public void printAssignment() {
		System.out.println("Current variable assignment:");

		for (int i = 0; i < variables; i++) {
			System.out.println("X" + (i + 1) + ": " + tValue[i]);
		}
		System.out.println();
	}

	/**
	 * Displays the formula in 2D format
	 */
	public void printFormula() {
		System.out.println("Initial Formula: ");

		for (int i = 0; i < clauses; i++) {
			System.out.print(i + ": " + "\t" + "(");

			for (int j : formula[i]) {
				System.out.print("X" + j + "\t");
			}
			System.out.print(")");
			System.out.println();
		}
	}

	/**
	 * Displays the linked list representation of the clause and the current
	 * assignment of the clauses of the variables
	 */
	public void print() {
		printAssignment();
		System.out.println("Clauses and current assignment: " + sublist);
	}

	public void read(String cnf) throws FileNotFoundException {
	
		FormulaReaderCalebPrivitera inFile = new FormulaReaderCalebPrivitera();
		inFile.read(cnf);
    
		clauses = inFile.getNumClauses();
		variables = inFile.getNumVariables();
		formula = inFile.getFormula();
    
		tValue = new int[variables];
		for (int i = 0; i < clauses ; i++) {
			sublist.add(i);
		}
		sublist.add(FALSE);	// initially, all clauses are unsatisfied
	}
}

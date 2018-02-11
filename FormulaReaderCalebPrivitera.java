package a1;

import java.io.File;
import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;

public class FormulaReaderCalebPrivitera {

	static int[][] formula;
	static int variables;
	static int clauses;

	void read(String inputFileName) throws FileNotFoundException {

		try {
			File input = new File(inputFileName);
			FileReader fileReader = new FileReader(input);
			Pattern pat = Pattern.compile("p cnf \\d+ \\d+");
			Scanner scan = new Scanner(fileReader);
			Matcher mat;
			boolean matchFlag = false;
			int curClause = 0;

			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				mat = pat.matcher(line);
				if (mat.matches()) {
					matchFlag = !matchFlag;
					String[] lines = line.split(" ");
					variables = Integer.parseInt(lines[2]);
					clauses = Integer.parseInt(lines[3]);
					formula = new int[clauses][];
					continue;
				}
				if (matchFlag) {
					String[] lines = line.split(" ");
					int[] clauseArray = new int[lines.length - 1];

					// Loop through the next line to fill the clause array
					for (int i = 0; i < lines.length; i++) {
						int val = Integer.parseInt(lines[i]);
						if (val == 0)
							break;
						clauseArray[i] = val;
					}
					formula[curClause++] = clauseArray;
				}
			}

			/*
			 * Debug
			 * for (int[] clause : formula) {
			 *	for (int i : clause) {
			 *		System.out.print(i + " ");
			 *	}
			 *	System.out.println();
			 *
			 */
			if (scan != null)
				scan.close();

		} catch (FileNotFoundException ex) {
			// if no file is found, print error

			System.out.println("File not found.");
			throw (ex);
		}
	}

	public int getNumVariables() {
		return variables;
	}

	public int getNumClauses() {
		return clauses;
	}

	public int[][] getFormula() {
		return formula;
	}

}

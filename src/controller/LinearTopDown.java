package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Grammar;

/**
 * @author Kristen Viguier
 *
 */
public class LinearTopDown {
	/**
	 * This is the grammar.
	 */
	private Grammar grammar;

	/**
	 * This is the input that we want to check if it is belong to the grammar.
	 */
	private String input;

	/**
	 * Table use for the memoization part of the TopDown. We store the computed
	 * previous results.
	 */
	private Boolean table[][][];

	/**
	 * This is an attribute to count the number of iteration (for performance
	 * measurements).
	 */
	private int iteration = 0;

	/**
	 * Constructor with these parameters.
	 * 
	 * @param grammar
	 * @param input
	 */
	public LinearTopDown(Grammar grammar, String input) {
		this.grammar = grammar;
		this.input = input;
		this.table = new Boolean[input.length()][input.length()][grammar.getGrammar().size()];
	}

	/**
	 * This is the adapted/optimized CKY algorithm in top down version.
	 * 
	 * @param A
	 *            represents the non terminal
	 * @param i
	 *            the start index
	 * @param j
	 *            the length index
	 * @return boolean either true or false
	 */
	public boolean linearTopDown(String A, int i, int j) {
		this.iteration++;

		// Check in the table
		Boolean tmp = table[i][j][getKeyIndex(A)];
		if (tmp != null) {
			return tmp; // Return the already computed result.
		}

		// This is the trivial case.
		if (i == j) {
			Boolean isRule = isRule(A, String.valueOf(input.charAt(i)));
			table[i][j][getKeyIndex(A)] = isRule; // We store the computed result
			return isRule;
		}

		// For all the rules N -> A B 
		for (String nt : this.grammar.getGrammar().get(A)) {
			if(nt.length() == 2) {
				// First partition
				if(linearTopDown(nt.substring(0, 1), i, i) && linearTopDown(nt.substring(1, 2), i + 1, j)) {
					table[i][j][getKeyIndex(A)] = true;
					return true;
				}
				// The second partition
				if(linearTopDown(nt.substring(0, 1), i, j - 1) && linearTopDown(nt.substring(1, 2), j, j)) {
					table[i][j][getKeyIndex(A)] = true;
					return true;
				}
			}
		}

		// No rule
		this.table[i][j][getKeyIndex(A)] = false;

		return false;
	}

	/**
	 * Check whether or not A->w[i] is a rule.
	 * 
	 * @param A
	 * @param w
	 * @return boolean
	 */
	private boolean isRule(String A, String w) {
		// For all rules A -> a we do
		for (Map.Entry<String, ArrayList<String>> entry : this.grammar.getGrammar().entrySet()) {
			String key = entry.getKey();
			ArrayList<String> values = entry.getValue();

			// if A == Na
			if (key.equals(A)) {
				for (String a : values) {
					// if a == input[i]
					if (a.length() == 1 && w.equals(a)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * This function search the position corresponding to the given parameter.
	 * 
	 * @param A
	 * @return int
	 */
	private int getKeyIndex(String A) {
		List<String> indexes = new ArrayList<String>(this.grammar.getGrammar().keySet()); // <== Set the keySet to List

		return indexes.indexOf(A);
	}

	/**
	 * @return the iteration
	 */
	public int getIteration() {
		return iteration;
	}

}

package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import model.Grammar;

/**
 * @author Kristen Viguier
 *
 */
public class LinearBottomUp {
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
	private boolean table[][][];

	/**
	 * This is an attribute to count the number of iteration (for performance
	 * measurements).
	 */
	private int iteration = 0;

	private List<String> indexes;

	/**
	 * Constructor with these given parameters.
	 * 
	 * @param grammar
	 * @param input
	 */
	public LinearBottomUp(Grammar grammar, String input) {
		this.grammar = grammar;
		this.input = input;
		this.table = new boolean[input.length()][input.length()][grammar.getGrammar().size()];
		this.indexes = new ArrayList<String>(this.grammar.getGrammar().keySet());
	}

	/**
	 * Determine with the bottom up optimized cky algorithm whether or not a word
	 * belong to this grammar.
	 * 
	 * @return boolean either true or false
	 */
	public boolean linearBottomUp() {
		// input string containing n chartacters
		int n = this.input.length();
		// Grammar contains R non terminal symbols
		int r = this.grammar.getGrammar().size();

		// First step is to initialize all elements of table to false.
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < r; k++) {
					this.table[i][j][k] = false;
				}
			}
		}

		Set<Entry<String, ArrayList<String>>> entryset = this.grammar.getGrammar().entrySet();
		/*
		 * The second step is : The first layer is for words of size 1. It means that it
		 * is for symbols (terminals). We store true in the cell table[i][i][A] if there
		 * is a rule Na-> input[i].
		 */
		for (int i = 0; i < n; i++) {
			// For all rules Na -> input[i][i] do
			for (Entry<String, ArrayList<String>> entry : entryset) {
				String key = entry.getKey();
				ArrayList<String> values = entry.getValue();

				for (String a : values) {
					// if a == input[i]
					if (a.length() == 1 && a.equals(String.valueOf(this.input.charAt(i)))) {
						this.table[i][i][getKeyIndex(key)] = true;
					}
				}
			}
		}

		// The length of the sub-string
		for (int l = 2; l <= n; l++) {
			// Start index
			for (int i = 0; i < n - l + 1; i++) {
				// for all production Na->Nb Nc
				for (Entry<String, ArrayList<String>> entry : entryset) {
					String key = entry.getKey();
					ArrayList<String> values = entry.getValue();

					for (String nt : values) {
						if (nt.length() == 2) {
							if (this.table[i][i][getKeyIndex(nt.substring(0, 1))]
									&& this.table[i + 1][i + l - 1][getKeyIndex(nt.substring(1, 2))]) {
								this.table[i][i + l - 1][getKeyIndex(key)] = true;
							}
							if (this.table[i][i + l - 2][getKeyIndex(nt.substring(0, 1))]
									&& this.table[i + l - 1][i + l - 1][getKeyIndex(nt.substring(1, 2))]) {
								this.table[i][i + l - 1][getKeyIndex(key)] = true;
							}
						}
						this.iteration++;
					}
				}
			}
		}

		if (this.table[0][n - 1][getKeyIndex(this.grammar.getStartSymbol())]) {
			return true;
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
		return this.indexes.indexOf(A);
	}

	/**
	 * @return the iteration
	 */
	public int getIteration() {
		return iteration;
	}

}

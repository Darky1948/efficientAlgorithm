package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import model.Grammar;

/**
 * 
 * @author Kristen VIGUIER
 *
 */
public class TopDown {
	
	/**
	 * This is the grammar.
	 */
	private Grammar grammar;
	
	/**
	 * This is the input that we want to check if it is belong to the grammar.
	 */
	private String input;
	
	/**
	 * Table use for the memoization part of the TopDown. We store the computed previous results.
	 */
	private Boolean table[][][];
	
	/**
	 * This is an attribute to count the number of iteration (for performance measurements).
	 */
	private int iterationTopDownMemoization;
	
	/**
	 * This is an attribute to count the number of iteration (for performance measurements).
	 */
	private int iterationTopDownNaive;
	
	/**
	 * We assume that constructor parameters are never null and well filled.
	 * @param grammar
	 * @param input
	 */
	public TopDown(Grammar grammar, String input) {
		this.grammar = grammar;
		this.input = input;
		// Table[n][n][r]
		this.table = new Boolean[input.length()][input.length()][grammar.getGrammar().size()];
		this.iterationTopDownMemoization = 0;
		this.iterationTopDownNaive = 0;
	}
	
	/**
	 * This is the CKY algorithm in top down version but as naive (without memoization).
	 * @param A represents the non terminal
	 * @param i the start index
	 * @param j the length index
	 * @return boolean either true or false
	 */
	public boolean naive(String A, int i, int j) {
		this.iterationTopDownNaive++;
		
		// This is the trivial case.
		if(i==j) {
			return isRule(A, String.valueOf(input.charAt(i)));
		}
		
		// For every partition
		for(int k = i; k <= j - 1; k++) {
			// For all the rule
			for(String nt : this.grammar.getGrammar().get(A)) {
				if(nt.length() == 2) {
					if(naive(nt.substring(0, 1), i, k) && naive(nt.substring(1, 2), k + 1, j)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Check whether or not A->w[i] is a rule.
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
		    if(key.equals(A)) {
		    	for (String a : values) {
					// if a == input[i]
		    		if(a.length() == 1 && w.equals(a)) {
		    			return true;
		    		}
				}
		    }
		}
		return false;
	}
	
	
	/**
	 * This is the CKY algorithm in top down version with memoization. We use a table to store the computed results. 
	 * We check whether the result has been already computer inside the table. 
	 * If not we compute it and then store it.
	 * @param A
	 * @param i
	 * @param j
	 * @return boolean 
	 */
	public boolean memoization(String A, int i, int j) {
		this.iterationTopDownMemoization++;
		
		// Check in the table
		Boolean tmp = table[i][j][getKeyIndex(A)];
		if(tmp != null) {
			return tmp; // Return the already computed result.
		}
		
		// This is the trivial case.
		if(i==j) {
			Boolean isRule = isRule(A, String.valueOf(input.charAt(i)));
			table[i][j][getKeyIndex(A)] = isRule; // We store the computed result
			return isRule;
		}
		
		// For every partition
		for(int k = i; k <= j - 1; k++) {
			// For all the rule
			for(String nt : this.grammar.getGrammar().get(A)) {
				if(nt.length() == 2) {
					if(memoization(nt.substring(0, 1), i, k) && memoization(nt.substring(1, 2), k + 1, j)) {
						table[i][j][getKeyIndex(A)] = true;
						return true;
					}
				}
			}
		}
		
		// No rule
		this.table[i][j][getKeyIndex(A)] = false;
		return false;
	}
	
	/**
	 * This function search the position corresponding to the given parameter.
	 * @param A
	 * @return int
	 */
	private int getKeyIndex(String A) {
		List<String> indexes = new ArrayList<String>(this.grammar.getGrammar().keySet()); // <== Set the keySet to List
		
		return indexes.indexOf(A);
	}

	/**
	 * @return the iterationTopDownMemoization
	 */
	public int getIterationTopDownMemoization() {
		return iterationTopDownMemoization;
	}

	/**
	 * @return the iterationTopDownNaive
	 */
	public int getIterationTopDownNaive() {
		return iterationTopDownNaive;
	}
	
}

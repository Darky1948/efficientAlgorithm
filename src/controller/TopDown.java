package controller;

import java.util.ArrayList;
import java.util.Map;

import model.Grammar;

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
	 * We assume that constructor parameters are never null and well filled.
	 * @param grammar
	 * @param input
	 */
	public TopDown(Grammar grammar, String input) {
		this.grammar = grammar;
		this.input = input;
		// Table[n][n][r]
		this.table = new Boolean[input.length()][input.length()][grammar.getGrammar().size()];
	}
	
	/**
	 * This is the CKY algorithm in top down version but as naive (without memoization).
	 * @param A represents the non terminal
	 * @param i the start index
	 * @param j the length index
	 * @return boolean either true or false
	 */
	public boolean naive(String A, int i, int j) {
		// This is the trivial case.
		if(i==j) {
			return isRule(A, String.valueOf(input.charAt(i)));
		}
		
		// For every partition
		for(int k = i; k < j - 1; k++) {
			// For all the rule
			for(String nt : this.grammar.getGrammar().get(A)) {
				if(naive(nt.substring(0, 1), i, k) && naive(nt.substring(1, 2), k + 1, j)) {
					return true;
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
	
	
	
	public boolean memoization() {
		return false;
	}
}

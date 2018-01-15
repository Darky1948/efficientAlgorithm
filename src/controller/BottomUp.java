/**
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Grammar;

/**
 * @author Kristen VIGUIER
 *
 */
public class BottomUp {
	
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
	private int iterationBottomUp;
	
	/**
	 * We assume that constructor parameters are never null and well filled.
	 * @param grammar
	 * @param input
	 */
	public BottomUp(Grammar grammar, String input) {
		this.grammar = grammar;
		this.input = input;
		// Table[n][n][r]
		this.table = new Boolean[input.length()][input.length()][grammar.getGrammar().size()];
		this.iterationBottomUp = 0;
	}
	
	/**
	 * Resolve the CKY algorithm by the bottom up version.
	 * @return boolean
	 */
	public boolean cky() {
		// input string containing n chartacters
		int n = this.input.length();
		// Grammar contains R non terminal symbols
		int r = this.grammar.getGrammar().size();
		
		// First step is to initialize all elements of table to false.
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				for(int k = 0; k < r; k++) {
					this.table[i][j][k] = false;
				}
			}
		}
		
		/*
		 * The second step is :
		 * The first layer is for words of size 1. It means that it is for symbols (terminals). 
		 * We store true in the cell table[i][i][A] if there is a rule Na-> input[i].
		 */
		for (int i = 0; i < n; i++) {
			// For all rules Na -> input[i][i] do
			for (Map.Entry<String, ArrayList<String>> entry : this.grammar.getGrammar().entrySet()) {
			    String key = entry.getKey();
			    ArrayList<String> values = entry.getValue();
			    
		    	for (String a : values) {
					// if a == input[i]
		    		if(a.length() == 1 && a.equals(String.valueOf(this.input.charAt(i)))) {
		    			this.table[i][0][getKeyIndex(key)] = true;
		    		}
				}
			}
		}
		
		// The length of the sub-string
		for (int l = 1; l < n; l++) {
			// Start index
			for (int i = 0; i < n - l; i++) {
				// Partitions loop 
				for (int k = 0; k < l; k++) {
					// for all production Na->Nb Nc
					for (Map.Entry<String, ArrayList<String>> entry : this.grammar.getGrammar().entrySet()) {
					    String key = entry.getKey();
					    ArrayList<String> values = entry.getValue();
					    
					    for (String nt : values) {
					    	if(nt.length() == 2) {
								if(this.table[i][k][getKeyIndex(nt.substring(0, 1))] && this.table[i + k + 1][l - k - 1][getKeyIndex(nt.substring(1, 2))]) {
									this.table[i][l][getKeyIndex(key)] = true;
								}
							}
					    	this.iterationBottomUp++;
						}
					}
				}
			}
		}
		
		return this.table[0][n-1][r-1];
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
	 * @return the iterationBottomUp
	 */
	public int getIterationBottomUp() {
		return iterationBottomUp;
	}
	
}

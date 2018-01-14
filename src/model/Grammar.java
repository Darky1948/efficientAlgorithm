package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grammar {
	
	/**
	 * This is the start symbol for this grammar.
	 */
	private String startSymbol;
	
	/**
	 * This is the grammar under the form Na->NbNc.
	 */
	private Map<String, ArrayList<String>> grammar;
	
	/**
	 * Default constructor.
	 */
	public Grammar() {
		this.startSymbol = "";
		this.grammar = new HashMap<>();
	}

	/**
	 * @return the startSymbol
	 */
	public String getStartSymbol() {
		return startSymbol;
	}

	/**
	 * @param startSymbol the startSymbol to set
	 */
	public void setStartSymbol(String startSymbol) {
		this.startSymbol = startSymbol;
	}

	/**
	 * @return the grammar
	 */
	public Map<String, ArrayList<String>> getGrammar() {
		return grammar;
	}

	/**
	 * @param grammar the grammar to set
	 */
	public void setGrammar(Map<String, ArrayList<String>> grammar) {
		this.grammar = grammar;
	}
	
	/**
	 * To string of the Grammar object.
	 */
	public String toString() {
		String tmp = "";
		
		tmp += "The start Symbol is : " + this.startSymbol + "\n";
		
		for (Map.Entry<String, ArrayList<String>> entry : this.grammar.entrySet()) {
		    String key = entry.getKey();
		    ArrayList<String> values = entry.getValue();
		    
		    tmp += key + " -> ";
		    for (String string : values) {
				tmp += string + " ";
			}
		    tmp += "\n";
		}
		
		return tmp;
	}
	
}

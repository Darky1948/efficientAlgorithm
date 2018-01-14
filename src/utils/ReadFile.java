package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class parse the given file and create the object Model Grammar.
 * @author Kristen VIGUIER
 */
public class ReadFile {
	
	/**
	 * Contain all the lines parsed from the grammar file.
	 */
	private ArrayList<String[]> allLines;
	
	/**
	 * String = non terminal
	 * String [] = right side terminal or non terminal
	 */
	private Map<String, ArrayList<String>> rulesMap;
	
	/**
	 * Default constructor.
	 */
	public ReadFile() {
		allLines = new ArrayList<String[]>();
		rulesMap = new HashMap<String, ArrayList<String>>();
	}
	
	/**
	 * Parse the grammar file and build an ArrayList in order to store non
	 * terminals and terminals.
	 * @param pPath
	 */
	public void readFile(final String pPath)  {
		try {
			FileReader fr = new FileReader(pPath);
			BufferedReader br = new BufferedReader(fr);
			// Get the first line
			String currentLine = br.readLine();
			while(currentLine != null){
				// Splitting in order to get the rule
				this.allLines.add(currentLine.toString().split(" "));
				// Read the next line
				currentLine = br.readLine();
			}
		} catch (Exception e) {
			System.out.println("Error occured : " + e.getMessage());
		}
		
		//Function that fill our map when we finished to parse our grammar file.
		fillMap();
	}
	
	/**
	 * Fill our rulesMap with allLines.
	 */
	private void fillMap() {
		if(!this.allLines.isEmpty()) {
			for(int i = 0; i < this.allLines.size(); i++) {
				this.rulesMap.put(this.allLines.get(i)[0], searchLeftSideForNonTerminal(this.allLines.get(i)[0]));
			}
		}
	}
	
	/**
	 * Looking if exists rules for the non terminals
	 * @param N
	 * @return ArrayList<String>
	 */
	private ArrayList<String> searchLeftSideForNonTerminal(String N) {
		ArrayList<String> tmp = new ArrayList<String>();
		
		for (int i = 0; i < this.allLines.size(); i++) {
			if(this.allLines.get(i)[0].equals(N)) {
				if(this.allLines.get(i).length > 2){
					tmp.add(this.allLines.get(i)[1] + this.allLines.get(i)[2]);
				}else {
					tmp.add(this.allLines.get(i)[1]);
				}
				
			}
		}
		return tmp;
	}

	/**
	 * @return the rulesMap
	 */
	public Map<String, ArrayList<String>> getRulesMap() {
		return rulesMap;
	}

	/**
	 * @param rulesMap the rulesMap to set
	 */
	public void setRulesMap(Map<String, ArrayList<String>> rulesMap) {
		this.rulesMap = rulesMap;
	}
}

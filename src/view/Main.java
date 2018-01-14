package view;

import java.util.ArrayList;
import java.util.Map;

import controller.TopDown;
import model.Grammar;
import utils.ReadFile;

public class Main {

	/**
	 * Path to load grammar.
	 */
	private static final String GRAMMAR = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\grammar.txt";
	/**
	 * Word to test for grammar.
	 */
	private static final String INPUT = "baaba";

	public static void main(String[] args) {
		ReadFile readFile = new ReadFile();
		readFile.readFile(GRAMMAR);

		Map<String, ArrayList<String>> map = readFile.getRulesMap();

		// Define the grammar
		Grammar grammar = new Grammar();
		grammar.setStartSymbol("S");
		grammar.setGrammar(map);
		
		System.out.println(grammar.toString());

		// Define the CKY Version
		TopDown topDown = new TopDown(grammar, INPUT);
		
		System.out.println(topDown.naive("S", 0, INPUT.length()-1));
	}

}

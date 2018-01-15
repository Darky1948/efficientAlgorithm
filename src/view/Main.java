package view;

import java.util.ArrayList;
import java.util.Map;

import controller.TopDown;
import model.Grammar;
import utils.ReadFile;

/**
 * 
 * @author Kristen VIGUIER
 *
 */
public class Main {

	/**
	 * Path to load grammar.
	 */
	private static final String GRAMMAR = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\grammar.txt";
	/**
	 * Word to test for grammar.
	 */
	private static final String INPUT = "baabaabaabaaba";
	
	/**
	 * Path to load language1.
	 */
	private static final String LANGUAGE_1 = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\language1.txt";
	/**
	 * Word to test for language1.
	 */
	private static final String WORD_LANGUAGE_1 = "()()";
	
	/**
	 * Path to load language2.
	 */
	private static final String LANGUAGE_2 = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\language2.txt";
	/**
	 * Word to test for language2.
	 */
	private static final String WORD_LANGUAGE_2 = "-10101.1e-101";
	
	/**
	 * Path to load language3.
	 */
	private static final String LANGUAGE_3 = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\language3.txt";
	/**
	 * Word to test for language3.
	 */
	private static final String WORD_LANGUAGE_3 = "babababababababababaabaab";
	
	/**
	 * Path to load language4.
	 */
	private static final String LANGUAGE_4 = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\language4.txt";
	/**
	 * Word to test for language4.
	 */
	private static final String WORD_LANGUAGE_4 = "ba";
	/**
	 * Path to load grammar.
	 */
	private static final String ASSIGNMENT4 = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\assignment4.txt";
	/**
	 * Word to test for grammar.
	 */
	private static final String INPUT4 = "ababca";

	public static void main(String[] args) {
		ReadFile readFile = new ReadFile();
		readFile.readFile(LANGUAGE_3);

		Map<String, ArrayList<String>> map = readFile.getRulesMap();

		// Define the grammar
		Grammar grammar = new Grammar();
		grammar.setStartSymbol("S");
		grammar.setGrammar(map);
		
		System.out.println(grammar.toString());

		// TopDown CKY Version
		TopDown topDown = new TopDown(grammar, WORD_LANGUAGE_3);
		
		System.out.println(topDown.naive("S", 0, WORD_LANGUAGE_3.length()-1) + " number of iterations " + topDown.getIterationTopDownNaive());
		System.out.println(topDown.memoization("S", 0, WORD_LANGUAGE_3.length()-1) + " number of iterations " + topDown.getIterationTopDownMemoization());
		
		// Bottom up CKY
		
	}

}

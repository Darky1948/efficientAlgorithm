package view;

import java.util.ArrayList;
import java.util.Map;

import controller.BottomUp;
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
	private static final String[] INPUT = {"baaba", "baabaaba", "baabaabaabaaba"}; 
	/**
	 * Path to load language1.
	 */
	private static final String LANGUAGE_1 = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\language1.txt";
	/**
	 * Word to test for language1.
	 */
	private static final String[] WORD_LANGUAGE_1 = {"()()", "((())(()))", "((()())())(())", "()())"};
	
	/**
	 * Path to load language2.
	 */
	private static final String LANGUAGE_2 = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\language2.txt";
	/**
	 * Word to test for language2.
	 */
	private static final String[] WORD_LANGUAGE_2 = {"-10101.1e-101", "+010101.1e-1011010", "+010101010101.10101e-101010101010", "-+010101.1e-1011010"};
	
	/**
	 * Path to load language3.
	 */
	private static final String LANGUAGE_3 = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\language3.txt";
	/**
	 * Word to test for language3.
	 */
	private static final String[] WORD_LANGUAGE_3 = {"baab", "babaab", "bababababababaab", "bababababababaaaab"};
	
	/**
	 * Path to load language4.
	 */
	private static final String LANGUAGE_4 = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\language4.txt";
	/**
	 * Word to test for language4.
	 */
	private static final String[] WORD_LANGUAGE_4 = {"ba", "ba", "baaaaaaaaaaaaaaaaa", "abbbbbbbbbbbbbbbbbbbbbbb"};
	/**
	 * Path to load grammar.
	 */
	private static final String ASSIGNMENT4 = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\assignment4.txt";
	/**
	 * Word to test for grammar.
	 */
	private static final String[] INPUT4 = {"ababaaabc"};

	public static void main(String[] args) {
		//testLanguage(GRAMMAR, INPUT, "S");
		testLanguage(LANGUAGE_1, WORD_LANGUAGE_1, "S");
//		testLanguage(LANGUAGE_2, WORD_LANGUAGE_2, "R");
//		testLanguage(LANGUAGE_3, WORD_LANGUAGE_3, "S");
//		testLanguage(LANGUAGE_4, WORD_LANGUAGE_4, "S");
//		testLanguage(ASSIGNMENT4, INPUT4, "S");
	}
	
	private static void testLanguage(String path, String[] input, String startSymbol) {
		ReadFile readFile = new ReadFile();
		readFile.readFile(path);

		Map<String, ArrayList<String>> map = readFile.getRulesMap();

		// Define the grammar
		Grammar grammar = new Grammar();
		grammar.setStartSymbol(startSymbol);
		grammar.setGrammar(map);
		
		System.out.println(grammar.toString());
		
		for (int i = 0; i < input.length; i++) {
			System.out.println("Word processed : " + input[i]);
			// TopDown CKY Version
			TopDown topDown = new TopDown(grammar, input[i]);
			
			long startTime = System.nanoTime();
			boolean naive = topDown.naive(grammar.getStartSymbol(), 0, input[i].length()-1);
			long stopTime = System.nanoTime();
			
			System.out.println(naive + " number of iterations " + topDown.getIterationTopDownNaive() + " executed in " + (stopTime - startTime) + " ns");
			
			startTime = System.nanoTime();
			boolean memoization = topDown.memoization(grammar.getStartSymbol(), 0, input[i].length()-1);
			stopTime = System.nanoTime();
			
			System.out.println(memoization + " number of iterations " + topDown.getIterationTopDownMemoization() + " executed in " + (stopTime - startTime) + " ns");
			
			// Bottom up CKY
			BottomUp bottomUp = new BottomUp(grammar, input[i]);
			
			startTime = System.nanoTime();
			boolean bottom = bottomUp.cky();
			stopTime = System.nanoTime();
			
			System.out.println(bottom + " number of iterations " + bottomUp.getIterationBottomUp() + " executed in " + (stopTime - startTime) + " ns" + "\n");
		}
		
	}

}

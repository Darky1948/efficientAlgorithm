package view;

import java.util.ArrayList;
import java.util.Map;

import controller.LinearBottomUp;
import controller.LinearTopDown;
import model.Grammar;
import utils.ReadFile;

public class Linear {

	/**
	 * Path to load grammar.
	 */
	private static final String TEST = "D:\\WORK\\Developpement\\java_ws\\efficientAlgorithm\\resources\\assignment4.txt";

	public static void main(String[] args) {
		simpleTestAssigment4();
	}

	private static void simpleTestAssigment4() {
		ReadFile readFile = new ReadFile();
		readFile.readFile(TEST);

		Map<String, ArrayList<String>> map = readFile.getRulesMap();

		// Define the grammar
		Grammar grammar = new Grammar();
		grammar.setStartSymbol("S");
		grammar.setGrammar(map);

		String input[] = { "aabbcc" };

		for (int i = 0; i < input.length; i++) {
			System.out.println("Word processed : " + input[i] + " de taille " + input[i].length());

			// Linear TopDown CKY Version
			LinearTopDown linearTopDown = new LinearTopDown(grammar, input[i]);
			// Linear BottomUp CKY Version
			LinearBottomUp linearBottomUp = new LinearBottomUp(grammar, input[i]);

			long startTime = System.nanoTime();
			boolean ltd = linearTopDown.linearTopDown(grammar.getStartSymbol(), 0, input[i].length() - 1);
			long stopTime = System.nanoTime();

			System.out.println(ltd + " number of iterations " + linearTopDown.getIteration()
					+ " executed in " + (stopTime - startTime) / 100000 + " ms");
			
			startTime = System.nanoTime();
			boolean lbt = linearBottomUp.linearBottomUp();
			stopTime = System.nanoTime();
			
			System.out.println(lbt + " number of iterations " + linearBottomUp.getIteration()
			+ " executed in " + (stopTime - startTime) / 100000 + " ms");
		}
	}
}

import java.util.*;
import java.io.*;

public class MastermindMain {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		StartNewGame(input);
	}
	
	public static void StartNewGame(Scanner input) {
		MastermindGame game = new MastermindGame();
		int length = game.getLength();
		String goal = game.getGoal();
		int turn = 0;
		String entered = "";
		while (turn < length && !entered.equals(goal)) {
			printGame(game);
			entered = input.next();
			while(!game.addLine(entered, turn)) {
                                System.out.println("That is an invalid move");
				entered = input.next();
                        }	
			turn++;
		}
		printGame(game);
		if (turn == length) {
			System.out.println("Sorry you lost!");
		} else {
			System.out.println("Congrats you won!");
		}
	}
	
	
	// print the current state of the game
	public static void printGame(MastermindGame game) {
		String possibleChars = game.getPossibleChars();
		System.out.println("Possible characters: " + possibleChars);
		String goal = game.getGoal();
      	//	System.out.println("Goal: " + goal); //print goal
		int spaces = game.getSpaces();	// get spaces per line
		int length = game.getLength(); // game length
		for (int i = length - 1; i >= 0; i--) { // for each line in the board print it out
			String line = game.getBoard()[i];
			if (line == null) { // if the game hasn't reached that turn print Xs
				for (int j = 0; j < spaces; j++) {
					System.out.print("X");	
				}
				System.out.println();
			} else {
				int correctP = getCorrectPosition(line, goal);
				int correctC = getCorrectColor(line, goal, possibleChars) - correctP;
				System.out.println(line + " Correct Positions: " + correctP + " Correct Colors: " + correctC);
			}
		}
		System.out.println(); 
	}

	public static int getCorrectPosition(String line, String goal) {
		int count = 0;
		for (int i = 0; i < goal.length(); i++) {
			if (line.charAt(i) == goal.charAt(i)) {
				count++;
			}
		}
		return count;
	}

	public static int getCorrectColor(String line, String goal, String possibleChars) {
		int count = 0;
		int[] lineCharCount = new int[possibleChars.length()];
		int[] goalCharCount = new int[possibleChars.length()];
		for (int i = 0; i < line.length(); i++) {
			lineCharCount[possibleChars.indexOf(line.charAt(i))]++;
		}
		for (int i = 0; i < goal.length(); i++) {
                       	goalCharCount[possibleChars.indexOf(goal.charAt(i))]++;
                }
		for (int i = 0; i < possibleChars.length(); i++) {
			count += Math.min(lineCharCount[i], goalCharCount[i]);
		}
		return count;
	}
}

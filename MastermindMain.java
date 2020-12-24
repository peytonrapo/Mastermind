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
		for (int turn = 0; turn < length; turn++) {
			printGame(game);
			while(!game.addLine(input.next(), turn)) {
				System.out.println("That is an invalid move");
			}
		}
		printGame(game);
	
	}
	
	
	// print the current state of the game
	public static void printGame(MastermindGame game) {
		System.out.println("Goal: " + game.getGoal()); //print goal
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
				System.out.println(line);
			}
		}
		System.out.println(); 
	}

	
}

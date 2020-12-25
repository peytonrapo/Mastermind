import java.util.*;
import java.io.*;

public class MastermindMain {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		PrintStream out = new PrintStream(new File("output.txt"));
		NeuralNetwork nn = new NeuralNetwork(240, 100, 16);
		for (int i = 0; i < 50000; i++) {
			StartNewGame(input, true, nn, out);
		}
	}
	
	public static void StartNewGame(Scanner input, boolean ai, NeuralNetwork nn, PrintStream out) {
		MastermindGame game = new MastermindGame();
		int length = game.getLength();
		int spaces = game.getSpaces();
		String possible = game.getPossibleChars();
		char[] possibleArr = possible.toCharArray();
		String goal = game.getGoal();
		int turn = 0;
		String entered = "";
		double[] gameData = new double[length * spaces * (possible.length() + 2)];
		// NeuralNetwork nn = new NeuralNetwork(gameData.length, 16, spaces * possible.length());
		double[] goalArr = new double[spaces * possible.length()];
		if (ai) {
			for (int i = 0; i < spaces; i ++) {
				goalArr[i * possible.length() + possible.indexOf(goal.charAt(i))] = 1;
			}
		}
		while (turn < length && !entered.equals(goal)) {
		//	printGame(game);
			if (ai) {
				entered = "";
				List<Double> guess = nn.predict(gameData);
				// System.out.println(guess);
				List<Double> guessMax = new ArrayList<Double>();
                        	for (int i = 0; i < spaces * possibleArr.length; i += possibleArr.length) {
                                	int inputIndex = i; 
                                	for (int j = 0; j < possibleArr.length; j++) {
                                        	if (guess.get(i + j) > guess.get(i)) {
                                                	inputIndex = j;
                                        	}
                                	}
					//System.out.println("input index max " + inputIndex);
					for (int j = 0; j < possibleArr.length; j++) {
						if (j == inputIndex % 4) {
							guessMax.add(1.0);
						} else {
							guessMax.add(0.0);
						}
					}	 
                                	entered += possibleArr[inputIndex % 4];
				}
				for (int i = 0; i < guess.size(); i++) {
					gameData[spaces * (possible.length() + 2) * turn + i] = guessMax.get(i);
				}
				for (int i = 0; i < getCorrectPosition(entered, goal); i++) {
					gameData[spaces * (possible.length() + 2) * turn + guess.size() + i] = 1;
				}
				for (int i = 0; i < getCorrectColor(entered, goal, possible); i++) {
					gameData[spaces * (possible.length() + 2) * turn + guess.size() + spaces + i] = 1;
				}
				// System.out.println(entered);
			} else {
				entered = input.next();
			}
			while(!game.addLine(entered, turn)) {
                                System.out.println("That is an invalid move");
				entered = input.next();
                        }	
			turn++;
		}
		// printGame(game);
		if(ai) {
			/* System.out.print("[");
			for (int i = 0; i < gameData.length; i++) {
				System.out.print(gameData[i] + ",");
			}
			System.out.println("]");
			System.out.print("[");
                        for (int i = 0; i < goalArr.length; i++) {
                                System.out.print(goalArr[i] + ",");
                        }
                        System.out.println("]"); */
			nn.train(gameData, goalArr);
		}
		out.println(turn);
		/*if (turn == length) {
			out.println("0");
			System.out.println("Sorry you lost!");
		} else {
			out.println("1");
			System.out.println("Congrats you won!");
		}*/
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

import java.util.*;

// Manages a game of mastermind
public class MastermindGame {
	private String[] board; // grid of guesses
	private int length; // how long the game goes for
	private String goal; // String trying to be made
	private int spaces; //how many spaces to enter per turn
	private char[] possibleChar; // possible characters
	
	// possible characters to use
	public static final String POSSIBLE_CHARACTERS = "RGBY"; 
	public static final int DEFAULT_SPACES = 4;
	public static final int DEFAULT_LENGTH = 10;

	// creates a new game at the beginning with a random goal string
	public MastermindGame() {
		length = DEFAULT_LENGTH;
		goal = "";
		possibleChar = POSSIBLE_CHARACTERS.toCharArray();
		spaces = DEFAULT_SPACES;
		for (int i = 0; i < spaces; i++) {
			goal += possibleChar[(int)(4 * Math.random())];
		}
		board = new String[length];
	}

	public boolean addLine(String line, int turn) {
		if (line.length() == spaces && turn < length) {
			char[] letters = line.toCharArray();
			for (int i = 0; i < letters.length; i++) {
				if (POSSIBLE_CHARACTERS.indexOf(letters[i]) == -1) {
					return false;
				}
			}
			board[turn] = line;
			return true;
		} else {
			return false;
		}	
	}

	public String getGoal() {
		return goal;
	}
	
	public String[] getBoard() {
		return board;
	}

	public int getLength() {
		return length;
	}

	public int getSpaces() {
		return spaces;
	}

	public String getPossibleChars() {
		return POSSIBLE_CHARACTERS;
	}
}

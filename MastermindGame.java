import java.util.*;

// Manages a game of mastermind
public class MastermindGame {
	private String[] board; // grid of guesses
	private int length; // how long the game goes for
	private String goal; // String trying to be made
	private int spaces; //how many spaces to enter per turn
	
	// possible characters to use
	public static final char[] POSSIBLE_CHARACTERS = ['R','G','B','Y']; 
	public static final int DEFAULT_SPACES = 4;

	// creates a new game at the beginning with a random goal string
	public MastermindGame(int length) {
		this.length = length;
		goal = '';
		spaces = DEFAULT_SPACES;
		for (int i = 0; i < spaces; i++) {
			goal += POSSIBLE_CHARACTERS[4 * Math.random()];
		}
		board = new String[length];
	}


}

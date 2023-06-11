
/* The purpose of this program is to recreate the board game Battleship
 * Note: I am working towards making the program more user friendly,
 * 		such as less case-sensitive inputs, being able to change ship placements, 
 * 		a help menu, allowing users to determine the board size and adjusting
 * 		the ship number accordingly, and much more.
 */

import java.util.*;

public class GameProgram {
	public static final int shipNum = 5;
	public static void main(String[] args) {
		Scanner console = new Scanner(System.in);
		intro();
		Player player1 = new Player(names(1, console));
		Player player2 = new Player(names(2, console));
		
		System.out.println();
		shipRules();
		
		// Have players place their ships and hide them from the other player
		shipPlacement(player1, player2, console);
		System.out.println();
		System.out.println("Press any key and enter for " + player2.getName() + " to place their ships: ");
		console.nextLine();
		for (int i = 0; i < 50; i++) {
			System.out.println("\n");
		}
		shipPlacement(player2, player1, console);
		System.out.println("Press any key and enter to start the game: ");
		console.nextLine();
		for (int i = 0; i < 50; i++) {
			System.out.println("\n");
		}
		
		gameRules();
		
		
		Player winner = player2;
		Player loser = player1;
		// Alternate between each player until all of one player's ships have been sunk
		while (player1.getShipBoard().getShips().size() != 0 && player2.getShipBoard().getShips().size() != 0) {
			guessShips(player1, player2, console);

			if (player2.getShipBoard().getShips().size() == 0) {
				winner = player1;
				loser = player2;
				continue;
			}
			guessShips(player2, player1, console);
		}
		
		System.out.println("Congratulations " + winner.getName() + " on destroying all of " + loser.getName() + "'s ships and winning the game!");
		
	}
	
	public static void intro() {
		System.out.println("Welcome to Battleship!");
		System.out.println();
		System.out.println("In this game, you will place your ships on your battle board. \nBe strategic because you don't want your opponent to find and sink them all!");
		System.out.println("Next, you will take turns guessing coordinates on each other's board to sink each other's ships. \nHits are indicated by X, and misses are indicated by O.");
		//System.out.println("To pull up the help menu, type \"help\".");
		System.out.println();
	}
	
	public static void shipRules() {
		System.out.println("It is now time to place ships!");
		System.out.println("Each player will have 5 ships to place. Each ship is 1 to 5 coordinates long, and can be placed vertically or horizontally, but not diagonally");
		System.out.println("When it is your turn to place a ship, you will enter the starting coordinate (top left side) and the orientation (vertical or horizontal) of the ship");
		System.out.println("The horizontal coordinates are by letter, and the vertical coordinates are by number as this board shows");
		System.out.println("Now let's get started!");
		System.out.println();
	}
	
	public static void gameRules() {
		System.out.println("Now let the game commence!");
		System.out.println();
		System.out.println("Players will take turns entering a coordinate to shoot");
		System.out.println("Coordinates should be entered in the format (x coordinate, y coordinate)");
		System.out.println("For example, attacking the position B3 should be entered as \"B 3\"");
		System.out.println("All letter values must be entered in uppercase and you may not hit the same position twice");
		System.out.println("Any invalid entrees will must be re-entered until a valid position is entered");
		System.out.println();
	}
	
	// Get player name
	public static String names(int num, Scanner console) {
		System.out.println("Player " + num + ", please enter your name: ");
		String name = console.nextLine().trim();
		return name;
	}
	
	// Place ships on the ship board
	public static void shipPlacement(Player first, Player second, Scanner console) {
		System.out.println("It is " + first.getName() + "'s turn to place their ships. Make sure to hide their locations from " + second.getName() + "!");
		System.out.println("Please enter the ship location as (x coordinate, y coordinate, orientation)");
		System.out.println("For example, a ship at position A2 placed horizontally would be entered as \"A 2 H\"");
		System.out.println("A ship at position I5 placed vertically would be entered as \"I 5 V\"");
		System.out.println("All letter values must be entered in uppercase. Any invalid orientations will default to vertical");
		System.out.println();
		
		int i = shipNum;
		while (i > 0) {
			System.out.println("This is what your board looks like");
			first.printShipBoard();
			System.out.println();
			System.out.println("Where would you like the ship of length " + i + " to be?");
			String placement = console.nextLine().trim();
			if (testPlacement(placement, first, i)) {
				first.getShipBoard().addShip(createShip(placement, i));
				i--;
			}
		}
		first.printShipBoard();
	}
	
	// Return whether placement input is valid or not
	public static boolean testPlacement(String placement, Player player, int length) {
		Board shipB = player.getShipBoard();
		
		StringTokenizer tokens = new StringTokenizer(placement);
		int tokenCount = 0;
		while (tokens.hasMoreTokens()) {
			tokenCount++;
			tokens.nextToken();
		}
		
		// Test for valid input length
		if (tokenCount != 3) {
			System.out.println("Invalid Input");
			return false;
		}
		
		StringTokenizer token = new StringTokenizer(placement);
		String letterString = token.nextToken();
		char letter = letterString.charAt(0);
		int number = Integer.valueOf(token.nextToken());
		String orientation = token.nextToken();
		
		// If input coordinates exist
		if (!testPlacementString(placement, player, letterString, number)) {
			System.out.println("Invalid Input");
			return false;
		}

		// Ship out of bounds
		if (orientation.equals("H")) {
			if (letter - 64 + length > shipB.getColumns()) {
				System.out.println("Your ship fell off the board!");
				return false;
			}
		}
		else {
			if (number + length - 1 > shipB.getRows()) {
				System.out.println("Your ship fell off the board!");
				return false;
			}
		}
		
		// Ship collides with another
		int index = 0;
		for (int i = 1; i < shipB.getColumns(); i++) {
			if (shipB.getBoard()[0][i].equals("" + letter)) {
				index = i;
				break;
			}
		}
		if (orientation.equals("H")) {
			for (int i = index; i < length + index; i++) {
				if (shipB.getBoard()[number][i].equals("+")) {
					System.out.println("Your ship collides with another ship!");
					return false;
				}
			}
		}
		else {
			for (int i = number; i < number + length; i++) {
				if (shipB.getBoard()[i][index].equals("+")) {
					System.out.println("Your ship colldies with another ship!");
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean testPlacementString(String placement, Player player, String letter, int row) {
		if (placement.length() != 5 && placement.length() != 6) {
			return false;
		}
		return testCoordinates(player, letter, row);
	}
	
	// Make sure input coordinates exist on the board
	public static boolean testCoordinates(Player player, String letter, int rowNum) {
		boolean column = false;
		boolean row = false;
		Board test = player.getShipBoard();
		for (int i = 0; i < test.getColumns(); i++) {
			if (letter.equals(test.getBoard()[0][i])) {
				column = true;
			}
		}
		String rowString = rowNum + "";
		for (int i = 1; i < test.getRows(); i++) {
			if (rowString.equals(test.getBoard()[i][0])) {
				row = true;
			}
		}
		return column && row;
	}
	
	public static Ship createShip(String placement, int length) {
		StringTokenizer tokens = new StringTokenizer(placement);
		String let = tokens.nextToken();
		char letter = let.charAt(0);
		int number = Integer.valueOf(tokens.nextToken());
		String orientation = tokens.nextToken();
		
		boolean o = false;
		if (orientation.equals("H")) {
			o = true;
		}
		return new Ship(length, o, letter - 64, number);
	}
	
	// Allow Player to take a turn and guess their opponent's ship
	public static void guessShips(Player attacker, Player defense, Scanner console) {
		attacker.getPlayingBoard().toString();
		System.out.println();
		System.out.println(attacker.getName() + ", where would you like to attack? ");
		String position = console.nextLine().trim();
		position = position.trim();
		//System.out.println(position);
		while (tokenCount(position) != 2 || !testAttackString(position, attacker)) {
			if (tokenCount(position) != 2) {
				System.out.println("Invalid Input");
			}
			System.out.println(attacker.getName() + ", where would you like to attack? ");
			position = console.nextLine().trim();
		}
		updateBoards(attacker, defense, position);
	}
	
	public static int tokenCount(String input) {
		StringTokenizer tokens = new StringTokenizer(input);
		int tokenCount = 0;
		while (tokens.hasMoreTokens()) {
			tokenCount++;
			tokens.nextToken();
		}
		return tokenCount;
	}
	
	public static boolean testAttackString(String position, Player player) {
		// string length
		// if coordinates are on the board
		// if position is not already guessed
		
		if (position.length() != 3 && position.length() != 4) {
			System.out.println("Invalid Input");
			return false;
		}
		
		StringTokenizer token = new StringTokenizer(position);
		String letterString = token.nextToken();
		int row = Integer.valueOf(token.nextToken());
		System.out.println("Letter: " + letterString + ", number: " + row);
		
		if (!testCoordinates(player, letterString, row)) {
			System.out.println("That position is not on the board");
			return false;
		}
		
		int column = 0;
		for (int i = 1; i < player.getPlayingBoard().getColumns(); i++) {
			if (player.getPlayingBoard().getBoard()[0][i].equals(position.substring(0,1))) {
				column = i;
			}
		}
		
		if (player.getPlayingBoard().getBoard()[row][column].equals("X") || player.getPlayingBoard().getBoard()[row][column].equals("O")) {
			System.out.println("You already attacked that position");
			return false;
		}
		
		return true;
	}
	
	public static void updateBoards(Player attacker, Player defense, String position) {
		int column = 0;
		for (int i = 1; i < attacker.getPlayingBoard().getColumns(); i++) {
			if (attacker.getPlayingBoard().getBoard()[0][i].equals(position.substring(0,1))) {
				column = i;
			}
		}

		StringTokenizer token = new StringTokenizer(position);
		token.nextToken();
		int row = Integer.valueOf(token.nextToken());
		
		// Check if attacker hit or missed and update boards accordingly
		if (defense.getShipBoard().getBoard()[row][column].equals("+")) {
			attacker.getPlayingBoard().getBoard()[row][column] = "X";
			attacker.printPlayingBoard();
			System.out.println("You hit " + defense.getName() + "'s ship!");
			hitAction(defense, column, row);			
		}
		else {
			attacker.getPlayingBoard().getBoard()[row][column] = "O";
			attacker.printPlayingBoard();
			System.out.println("You missed!");
		}
		System.out.println();
	}

	// Update ship status - which ship and coordinate hit, whether ship sank
	public static void hitAction(Player defense, int column, int row) {
		ArrayList<Ship> allShips = defense.getShipBoard().getShips();
		
		// Run through all ships
		for (int i = 0; i < allShips.size(); i++) {
			Ship s = allShips.get(i);
			
			// Identify which ship is hit and update the ship's status
			if (s.horizontal()) {
				for (int j = s.getX(); j < s.getX() + s.getLength(); j++) {
					if (row == s.getY() && column == j) {
						s.updateShip(j);
						break;
					}
				}
			}
			
			// Test the vertical ships
			else {
				for (int j = s.getY(); j < s.getY() + s.getLength(); j++) {
					if (column == s.getX() && row == j) {
						s.updateShip(j);
						break;
					}
				}
			}
			
			// Remove ship if sunk
			if (s.checkSunk()) {
				defense.getShipBoard().removeShip(i);
			}
			
		}

	}
	
}

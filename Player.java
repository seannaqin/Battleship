
public class Player {
	private String name;
	private Board playingBoard;
	private Board shipBoard;
	
	public Player(String n) {
		name = n;
		playingBoard = new Board();
		shipBoard = new Board();
	}
	
	public void printPlayingBoard() {
		playingBoard.toString();
	}
	
	public void printShipBoard() {
		shipBoard.toString();
	}
	
	public String getName() {
		return name;
	}
	
	public Board getPlayingBoard() {
		return playingBoard;
	}
	
	public Board getShipBoard() {
		return shipBoard;
	}
}

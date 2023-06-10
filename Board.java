import java.util.ArrayList;

public class Board {
	private String[][] board;
	private ArrayList<Ship> allShips;
	
	public Board() {
		board = new String[11][11];
		allShips = new ArrayList<Ship>();
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = "-";
			}
		}
		
		for (int i = 1; i < board.length; i++) {
			board[0][i] = String.valueOf((char) (64 + i));
			board[i][0] = "" + i;
		}
		
		board[0][0] = " ";
	}
	
	public String[][] getBoard() {
		return board;
	}
	
	public int getRows() {
		return board.length;
	}
	
	public int getColumns() {
		return board[0].length;
	}
	
	public String toString() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j]);
				if (!(j == 0 && i >= 10)) {
					System.out.print(" ");
				}
		
			}
			System.out.println();
		}
		return "";
	}
	
	public void updatePlaceBoard() {
		
	}
	
	public void addShip(Ship s) {
		allShips.add(s);
		addShipToBoard();
	}
	
	public void removeShip(int index) {
		allShips.remove(index);
		
	}
	
	private void addShipToBoard() {
		Ship s = allShips.get(allShips.size() - 1);
		if (s.horizontal()) {
			for (int i = s.getX(); i < s.getX() + s.getLength(); i++) {
				board[s.getY()][i] = "+";
			}
		}
		else {
			for (int i = s.getY(); i < s.getY() + s.getLength(); i++) {
				board[i][s.getX()] = "+";
			}
		}
	}
	
	public ArrayList<Ship> getShips() {
		return allShips;
	}
}

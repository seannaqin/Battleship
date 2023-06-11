
public class Ship {
	private boolean horizontal;
	private int length;
	private int x;
	private int y;
	private String[] ship;
	
	// Basic Ship
	public Ship (int length) {
		this.length = length;
		horizontal = true;
		x = 0;
		y = 0;
		ship = new String[length];
		for (int i = 0; i < length; i++) {
			ship[i] = ".";
		}
	}
	
	// Customized ship
	public Ship (int length, boolean h, int x, int y) {
		this.length = length;
		horizontal = h;
		this.x = x;
		this.y = y;
		ship = new String[length];
		for (int i = 0; i < length; i++) {
			ship[i] = ".";
		}
	}
	
	// Update coordinate of ship that is hit
	public void updateShip(int xCoord) {
		if (horizontal) {
			ship[xCoord-x] = "X";
		}
		else {
			ship[xCoord-y] = "X";
		}
	}
	
	// Determine if all coordinates of the ship has been hit
	public boolean checkSunk() {
		for (int i = 0; i < ship.length; i++) {
			if (!ship[i].equals("X")) {
				return false;
			}
		}
		System.out.println("Ship sunk!");
		return true;
	}
	
	// Return variables
	public boolean horizontal() {
		return horizontal;
	}
	
	public int getLength() {
		return length;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
		
	public String[] getShip() {
		return ship;
	}
}

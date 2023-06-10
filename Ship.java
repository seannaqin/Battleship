
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
	
	// Ship after placement
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
	
	public void updateShip(int xCoord) {
		if (horizontal) {
			ship[xCoord-x] = "X";
		}
		else {
			ship[xCoord-y] = "X";
		}
	}
	
	public String[] getShip() {
		return ship;
	}
}

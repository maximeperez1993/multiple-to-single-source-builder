package fr.mperez.tictactoe;

public class Move {
	private final int x;
	private final int y;

	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return this.x + " " + this.y;
	}
}


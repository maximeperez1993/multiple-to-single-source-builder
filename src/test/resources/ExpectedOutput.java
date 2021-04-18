import java.util.Scanner;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
* Built at : 1989/01/13 00:00:00
*/


class Player {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		MoveParser moveParser = new MoveParser(in);
		// game loop
		for (int z = 0; z < 100000; z++) {
			Move previousOpponentMove = moveParser.parse();
			int opponentRow = in.nextInt();
			int opponentCol = in.nextInt();
			System.err.println("opponentRowaa=" + opponentRow);
			System.err.println("opponentCol=" + opponentCol);

			List<Move> possibleMoves = new ArrayList<>();
			int validActionCount = in.nextInt();
			for (int i = 0; i < validActionCount; i++) {
				possibleMoves.add(moveParser.parse());
			}

			System.out.println(possibleMoves.get(0));
		}
	}
	
	static class Move {
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
	
	
		
		
		
	static class MoveParser {
	
		private final Scanner in;
	
		public MoveParser(Scanner in) {this.in = in;}
	
		public Move parse() {
			return new Move(this.in.nextInt(), this.in.nextInt());
		}
	}
	
}

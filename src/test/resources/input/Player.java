package fr.mperez.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.mperez.tictactoe.parser.MoveParser;

public class Player {

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
}

package fr.mperez.tictactoe.parser;

import java.util.Scanner;

import fr.mperez.tictactoe.Move;

public class MoveParser {

	private final Scanner in;

	public MoveParser(Scanner in) {this.in = in;}

	public Move parse() {
		return new Move(this.in.nextInt(), this.in.nextInt());
	}
}

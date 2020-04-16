package mipepe.music;

import java.util.Random;

public class DirectionNode {
	enum Direction {
		DOWN, RIGHT, UP, LEFT
	}
	@Override
	public String toString() {
		switch (direction) {
		case DOWN:
			return "DOWN";
		case RIGHT:
			return "RIGHT";
		case UP:
			return "UP";
		case LEFT:
			return "LEFT";
		default:
			System.out.println("ERROR: Invalid direction...");
			System.exit(0);
		}
		return null;
	}
	public Direction direction;
	public DirectionNode next;
	public DirectionNode previous;
	public int value;
	public static Direction generateRandomDirection(Random random) {
		int key = random.nextInt(4);
		switch (key) {
			case 0:
				return Direction.DOWN;
			case 1:
				return Direction.RIGHT;
			case 2:
				return Direction.UP;
			case 3:
				return Direction.LEFT;
			default:
				System.out.println("ERROR: invalid key...");
				System.exit(0);
		}
		return Direction.DOWN;
	}
}

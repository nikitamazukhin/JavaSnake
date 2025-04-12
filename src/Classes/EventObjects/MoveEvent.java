package Classes.EventObjects;

import Classes.Coordinate2i;
import Enums.Direction;

import java.util.EventObject;

public class MoveEvent extends EventObject {
    private final Coordinate2i[] snakeBodyCoordinates;
    private final Direction snakeHeadDirection;
    private final int score;

    public MoveEvent(Object source, Coordinate2i[] snakeBodyCoordinates, Direction snakeHeadDirection, int score) {
        super(source);
        this.snakeBodyCoordinates = snakeBodyCoordinates;
        this.snakeHeadDirection = snakeHeadDirection;
        this.score = score;
    }

    public Coordinate2i[] getSnakeBodyCoordinates() {
        return snakeBodyCoordinates;
    }

    public Direction getSnakeHeadDirection() {
        return snakeHeadDirection;
    }

    public int getScore() {
        return score;
    }
}

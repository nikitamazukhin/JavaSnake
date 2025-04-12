package Classes;

import Classes.EventObjects.CollisionEvent;
import Classes.EventObjects.FoodEvent;
import Classes.EventObjects.MoveEvent;
import Classes.HighscoreSystem.HighscoreSystem;
import Enums.Direction;
import Interfaces.SnakeControlListener;
import Interfaces.SnakeGameplayListener;

import java.awt.event.KeyEvent;

public class SnakeBoard implements SnakeControlListener {
    private final int[][] grid;
    private final Snake snake;
    private Direction previousMoveDirection;
    private final Thread gameplayThread;
    private final Coordinate2i foodCoordinate;
    private SnakeGameplayListener gameplayListener;

    public SnakeBoard(int width, int height) {
        grid = new int[height][width];
        snake = new Snake(width, height);
        previousMoveDirection = snake.getMoveDirection();
        foodCoordinate = new Coordinate2i(0, 0);
        generateFood();

        gameplayThread = new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    while (true) {
                        try {
                            //move the snake
                            snake.move();

                            //check if a snake moved to an apple tile
                            if (snake.checkCoordCollision(foodCoordinate.x, foodCoordinate.y)) {
                                snake.eatFood();
                                generateFood();
                                //tell graphics that we ate food
                                fireFoodEvent();
                            }

                            //check if the snake hit a wall
                            if (snake.checkWallCollision(width, height)) {
                                //tell graphics we hit a wall
                                int score = snake.getFoodCount();
                                fireCollisionEvent(true, score, HighscoreSystem.checkForHighScore(score));
                            }
                            //check if the snake hit itself
                            else if (snake.checkSegmentCollision()) {
                                //tell graphics we hit ourselves
                                int score = snake.getFoodCount();
                                fireCollisionEvent(false, score, HighscoreSystem.checkForHighScore(score));
                            }

                            //tell graphics we moved the snake
                            if (!isInterrupted()) {
                                fireMoveEvent();
                                previousMoveDirection = snake.getMoveDirection();
                            }

                            sleep(150);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            }
        };
    }

    public void setGameplayListener(SnakeGameplayListener listener) {
        gameplayListener = listener;
    }

    public void startGame() {
        fireMoveEvent();
        fireFoodEvent();
        gameplayThread.start();
    }

    private void generateFood() {
        do {
            foodCoordinate.x = (int) (Math.random() * grid[0].length);
            foodCoordinate.y = (int) (Math.random() * grid.length);
        }
        while (!snake.checkCoordAvailability(foodCoordinate.x, foodCoordinate.y));
    }

    public void fireFoodEvent() {
        gameplayListener.consumeFoodEvent(new FoodEvent(this, foodCoordinate));
    }

    public void fireMoveEvent() {
        gameplayListener.consumeMoveEvent(new MoveEvent(this, snake.getBodyCoordinates(), snake.getMoveDirection(), snake.getFoodCount()));
    }

    public void fireCollisionEvent(boolean hitWall, int score, boolean isHighScore) {
        gameplayListener.consumeCollisionEvent(new CollisionEvent(this, hitWall, score, isHighScore));
        gameplayThread.interrupt();
    }

    @Override
    public void consumeKeyEvent(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> {
                if (previousMoveDirection != Direction.DOWN)
                    snake.setDirection(Direction.UP);
            }
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> {
                if (previousMoveDirection != Direction.UP)
                    snake.setDirection(Direction.DOWN);
            }
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> {
                if (previousMoveDirection != Direction.RIGHT)
                    snake.setDirection(Direction.LEFT);
            }
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> {
                if (previousMoveDirection != Direction.LEFT)
                    snake.setDirection(Direction.RIGHT);
            }
        }
    }
}

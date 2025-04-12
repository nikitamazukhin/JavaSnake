package Classes.Graphics;

import Classes.Coordinate2i;
import Classes.EventObjects.CollisionEvent;
import Classes.EventObjects.FoodEvent;
import Classes.EventObjects.MoveEvent;
import Enums.TileType;
import Interfaces.SnakeControlListener;
import Interfaces.SnakeGameplayListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeGraphics extends JFrame implements SnakeGameplayListener {
    enum WindowState {
        GAME, END
    }

    private WindowState windowState;
    private SnakeTable snakeTable;
    private ScorePanel scorePanel;
    private final Thread drawThread;
    private SnakeControlListener controlListener;
    private SnakeEndScreen endScreen;

    public SnakeGraphics(int gridWidth, int gridHeight, int tileSize, Color backgroundColor, Color foregroundColor, Font font) {
        super("Classes.Snake");
        setLayout(new BorderLayout());
        setSize(new Dimension(gridWidth * tileSize, gridHeight * tileSize + (int) ((gridHeight * tileSize) * 0.2)));
        setBackground(backgroundColor);
        setForeground(foregroundColor);
        setFont(font);

        setResizable(false);
        setLocationRelativeTo(null);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (windowState) {
                    case GAME -> {
                        if (controlListener != null)
                            controlListener.consumeKeyEvent(e);
                    }
                    case END -> {
                        if (endScreen != null)
                            endScreen.consumeKeyEvent(e);
                    }
                }
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        drawThread = new Thread() {
            @Override
            public void run() {
                synchronized (this) {
                    while (true) {
                        validate();
                        repaint();
                        try {
                            sleep(33);
                        } catch (InterruptedException e) {
                            System.out.println("Game over");
                            return;
                        }
                    }
                }
            }
        };
    }

    public void setSnakeControlListener(SnakeControlListener a) {
        controlListener = a;
    }

    public void setSnakeTable(SnakeTable snakeTable) {
        this.snakeTable = snakeTable;
        add(snakeTable, BorderLayout.CENTER);
    }

    public void setScorePanel(ScorePanel scorePanel) {
        this.scorePanel = scorePanel;
        add(scorePanel, BorderLayout.PAGE_END);
    }

    @Override
    public void consumeMoveEvent(MoveEvent e) {
        switch (e.getSnakeHeadDirection()) {
            case UP -> snakeTable.updateSnakeTiles(e.getSnakeBodyCoordinates(), TileType.SNAKE_HEAD_UP);
            case DOWN -> snakeTable.updateSnakeTiles(e.getSnakeBodyCoordinates(), TileType.SNAKE_HEAD_DOWN);
            case LEFT -> snakeTable.updateSnakeTiles(e.getSnakeBodyCoordinates(), TileType.SNAKE_HEAD_LEFT);
            case RIGHT -> snakeTable.updateSnakeTiles(e.getSnakeBodyCoordinates(), TileType.SNAKE_HEAD_RIGHT);
        }
        scorePanel.updateScore(e.getScore());
    }

    @Override
    public void consumeFoodEvent(FoodEvent e) {
        Coordinate2i foodCoordinate = e.getFoodCoordinate();
        if (snakeTable.getValueAt(foodCoordinate.y, foodCoordinate.x) != TileType.FOOD) {
            snakeTable.setValueAt(TileType.FOOD, foodCoordinate.y, foodCoordinate.x);
        }
    }

    @Override
    public void consumeCollisionEvent(CollisionEvent e) {
        getContentPane().removeAll();
        windowState = WindowState.END;
        endScreen = new SnakeEndScreen(getWidth(), getHeight(), getBackground(), getForeground(), getFont(), e.getReason(), e.getScore(), e.isHighScore());
        add(endScreen);
    }

    public void startDrawing() {
        windowState = WindowState.GAME;
        drawThread.start();
    }
}

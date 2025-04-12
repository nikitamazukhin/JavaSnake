import Classes.*;
import Classes.Graphics.*;

import java.awt.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        int gridWidth = 25;
        int gridHeight = 16;
        int tileSize = 45;
        Font joystix;
        try {
            joystix = Font.createFont(Font.TRUETYPE_FONT, new File("joystix.ttf")).deriveFont(gridHeight * tileSize * 0.07f);
        } catch (FontFormatException | IOException e) {
            joystix = new Font("Arial", Font.PLAIN, (int)(gridHeight * tileSize * 0.07f));
        }
        try (FileOutputStream outStr = new FileOutputStream("highscores.bin", true)) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Color backgroundColor = new Color(167, 201, 100);
        Color foregroundColor = new Color(39, 47, 23);

        SnakeBoard board = new SnakeBoard(gridWidth, gridHeight);
        SnakeGraphics graphics = new SnakeGraphics(gridWidth, gridHeight, tileSize, backgroundColor, foregroundColor, joystix);

        SnakeTable snakeTable = new SnakeTable(gridWidth, gridHeight, tileSize);
        snakeTable.setModel(new SnakeTableModel(gridWidth, gridHeight));
        snakeTable.setDefaultRenderer(snakeTable.getColumnClass(0), new TileRenderer(tileSize, backgroundColor, foregroundColor));

        ScorePanel scorePanel = new ScorePanel(gridWidth, gridHeight, tileSize, backgroundColor, foregroundColor, joystix);

        graphics.setSnakeTable(snakeTable);
        graphics.setScorePanel(scorePanel);

        board.setGameplayListener(graphics);
        graphics.setSnakeControlListener(board);
        board.startGame();
        graphics.startDrawing();
    }
}


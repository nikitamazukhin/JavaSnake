package Classes.Graphics;

import Classes.Coordinate2i;
import Enums.TileType;

import javax.swing.*;
import java.awt.*;

public class SnakeTable extends JTable {
    private Coordinate2i previousLastBodyTile;

    public SnakeTable(int gridWidth, int gridHeight, int tileSize) {
        previousLastBodyTile = null;
        setFocusable(false);
        setRowHeight(tileSize);
        setPreferredSize(new Dimension(gridWidth * tileSize, gridHeight * tileSize));
    }

    public void updateSnakeTiles(Coordinate2i[] snakeCoordinates, TileType headDirection) {
        setValueAt(headDirection, snakeCoordinates[0].getY(), snakeCoordinates[0].getX());

        for (int i = 1; i < snakeCoordinates.length; i++)
            setValueAt(TileType.SNAKE_BODY, snakeCoordinates[i].getY(), snakeCoordinates[i].getX());

        if (previousLastBodyTile != null)
            setValueAt(TileType.EMPTY, previousLastBodyTile.getY(), previousLastBodyTile.getX());

        previousLastBodyTile = new Coordinate2i(snakeCoordinates[snakeCoordinates.length - 1]);
    }
}

package Classes.Graphics;

import Enums.TileType;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;

public class SnakeTableModel extends AbstractTableModel {
    private final TileType[][] tiles;

    public SnakeTableModel(int width, int height) {
        tiles = new TileType[height][width];

        for (TileType[] tile : tiles) {
            Arrays.fill(tile, TileType.EMPTY);
        }
    }

    @Override
    public int getRowCount() {
        return tiles.length;
    }

    @Override
    public int getColumnCount() {
        return tiles[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return tiles[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        tiles[rowIndex][columnIndex] = (TileType) aValue;
    }
}

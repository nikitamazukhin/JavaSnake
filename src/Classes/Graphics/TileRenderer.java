package Classes.Graphics;

import Enums.TileType;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TileRenderer implements TableCellRenderer {
    private final int tileSize;
    private final Color backgroundColor;
    private final Color foregroundColor;

    public TileRenderer(int tileSize, Color backgroundColor, Color foregroundColor) {
        this.tileSize = tileSize;
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return switch ((TileType) value) {
            case SNAKE_HEAD_UP -> new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(backgroundColor);
                    g.fillRect(0, 0, tileSize, tileSize);

                    g.setColor(foregroundColor);
                    g.fillRect(0, tileSize / 2, tileSize, tileSize / 2);
                    g.fillPolygon(
                            new int[]{0, tileSize / 2, tileSize},
                            new int[]{tileSize / 2, 0, tileSize / 2},
                            3
                    );

                    g.setColor(backgroundColor);
                    g.drawLine(
                            (int) (tileSize * 0.25f),
                            (int) (tileSize * 0.45f),
                            (int) (tileSize * 0.45f),
                            (int) (tileSize * 0.25f)
                    );
                    g.drawLine(
                            (int) (tileSize * 0.75f),
                            (int) (tileSize * 0.45f),
                            (int) (tileSize * 0.55f),
                            (int) (tileSize * 0.25f)
                    );
                }
            };

            case SNAKE_HEAD_DOWN -> new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(backgroundColor);
                    g.fillRect(0, 0, tileSize, tileSize);

                    g.setColor(foregroundColor);
                    g.fillRect(0, 0, tileSize, tileSize / 2);
                    g.fillPolygon(
                            new int[]{0, tileSize / 2, tileSize},
                            new int[]{tileSize / 2, tileSize, tileSize / 2},
                            3
                    );

                    g.setColor(backgroundColor);
                    g.drawLine(
                            (int) (tileSize * 0.25f),
                            (int) (tileSize * 0.65f),
                            (int) (tileSize * 0.45f),
                            (int) (tileSize * 0.85f)
                    );
                    g.drawLine(
                            (int) (tileSize * 0.75f),
                            (int) (tileSize * 0.65f),
                            (int) (tileSize * 0.55f),
                            (int) (tileSize * 0.85f)
                    );
                }
            };

            case SNAKE_HEAD_LEFT -> new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(backgroundColor);
                    g.fillRect(0, 0, tileSize, tileSize);

                    g.setColor(foregroundColor);
                    g.fillRect(tileSize / 2, 0, tileSize / 2, tileSize);
                    g.fillPolygon(
                            new int[]{tileSize / 2, 0, tileSize / 2},
                            new int[]{0, tileSize / 2, tileSize},
                            3
                    );

                    g.setColor(backgroundColor);
                    g.drawLine(
                            (int) (tileSize * 0.35f),
                            (int) (tileSize * 0.25f),
                            (int) (tileSize * 0.15f),
                            (int) (tileSize * 0.45f)
                    );
                    g.drawLine(
                            (int) (tileSize * 0.35f),
                            (int) (tileSize * 0.75f),
                            (int) (tileSize * 0.15f),
                            (int) (tileSize * 0.55f)
                    );
                }
            };

            case SNAKE_HEAD_RIGHT -> new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(backgroundColor);
                    g.fillRect(0, 0, tileSize, tileSize);

                    g.setColor(foregroundColor);
                    g.fillRect(0, 0, tileSize / 2, tileSize);
                    g.fillPolygon(
                            new int[]{tileSize / 2,
                                    tileSize, tileSize / 2},
                            new int[]{0, tileSize / 2, tileSize},
                            3
                    );

                    g.setColor(backgroundColor);
                    g.drawLine(
                            (int) (tileSize * 0.65f),
                            (int) (tileSize * 0.25f),
                            (int) (tileSize * 0.85f),
                            (int) (tileSize * 0.45f)
                    );
                    g.drawLine(
                            (int) (tileSize * 0.65f),
                            (int) (tileSize * 0.75f),
                            (int) (tileSize * 0.85f),
                            (int) (tileSize * 0.55f)
                    );
                }
            };

            case SNAKE_BODY -> new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(foregroundColor);
                    g.fillRect(0, 0, tileSize, tileSize);
                }
            };

            case FOOD -> new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(backgroundColor);
                    g.fillRect(0, 0, tileSize, tileSize);

                    g.setColor(foregroundColor);
                    g.fillRect(
                            0,
                            tileSize / 2 - (int) (tileSize / 6.66f),
                            tileSize,
                            (int) (tileSize / 3.33f)
                    );
                    g.fillRect(
                            tileSize / 2 - (int) (tileSize / 6.66f),
                            0,
                            (int) (tileSize / 3.33f),
                            tileSize
                    );

                    g.setColor(backgroundColor);
                    g.fillRect(
                            tileSize / 2 - tileSize / 8,
                            tileSize / 2 - tileSize / 8,
                            tileSize / 4,
                            tileSize / 4
                    );
                }
            };

            case EMPTY -> new JComponent() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.setColor(backgroundColor);
                    g.fillRect(0, 0, tileSize, tileSize);
                }
            };
        };
    }
}

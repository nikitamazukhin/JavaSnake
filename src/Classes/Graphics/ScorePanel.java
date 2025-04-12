package Classes.Graphics;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private int score;

    public ScorePanel(int gridWidth, int gridHeight, int tileSize, Color backgroundColor, Color textColor, Font font) {
        setPreferredSize(new Dimension(gridWidth * tileSize, (int) (gridHeight * tileSize * 0.146f)));
        setBackground(backgroundColor);
        setForeground(textColor);
        setFont(font);
        setFocusable(false);
        score = 0;
    }

    public void updateScore(int n) {
        score = n;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString(
                "Score: " + score,
                5,
                getHeight() / 2
        );
    }
}

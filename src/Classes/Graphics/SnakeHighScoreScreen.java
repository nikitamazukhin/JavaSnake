package Classes.Graphics;

import Classes.HighscoreSystem.HighscoreSystem;

import javax.swing.*;
import java.awt.*;

public class SnakeHighScoreScreen extends JComponent {
    private final String[] playerNames;
    private final int[] highScores;

    public SnakeHighScoreScreen(int width, int height, Color backgroundColor, Color textColor, Font font) {
        setPreferredSize(new Dimension(width, height));
        setBackground(backgroundColor);
        setForeground(textColor);
        setFont(font);

        playerNames = HighscoreSystem.getNamesFromFile();
        highScores = HighscoreSystem.getScoresFromFile();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(getForeground());
        g.drawString(
                "High Scores",
                getWidth() / 2 - g.getFontMetrics().stringWidth("High Scores") / 2,
                (int) (g.getFontMetrics().getStringBounds("H", g).getHeight())
        );

        g.setFont(getFont().deriveFont(getFont().getSize() * 0.70f));
        for (int i = 0; i < highScores.length; i++) {
            if (playerNames[i] == null || playerNames[i].isEmpty())
                g.drawString(
                        "EMPTY",
                        getWidth() / 2 - g.getFontMetrics().stringWidth("EMPTY") / 2,
                        (int) (getHeight() * 0.15f + getHeight() * 0.085f * i)
                );
            else
                g.drawString(
                        playerNames[i] + " - " + highScores[i],
                        getWidth() / 2 - g.getFontMetrics().stringWidth(playerNames[i] + " - " + highScores[i]) / 2,
                        (int) (getHeight() * 0.15f + getHeight() * 0.085f * i)
                );
        }
    }
}

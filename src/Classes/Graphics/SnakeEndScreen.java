package Classes.Graphics;

import Classes.HighscoreSystem.HighscoreSystem;
import Interfaces.SnakeControlListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class SnakeEndScreen extends JComponent implements SnakeControlListener {
    private final String reason;
    private final int score;
    private final boolean isHighScore;
    private String playerName;

    public SnakeEndScreen(int width, int height, Color backgroundColor, Color textColor, Font font, String reason, int score, boolean isHighScore) {
        setPreferredSize(new Dimension(width, height));
        setBackground(backgroundColor);
        setForeground(textColor);
        setFont(font);
        this.reason = reason;
        this.score = score;
        this.isHighScore = isHighScore;
        playerName = "";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(getForeground());
        g.drawString(
                "Game Over!",
                getWidth() / 2 - g.getFontMetrics().stringWidth("Game Over!") / 2,
                (int) (g.getFontMetrics().getStringBounds("G", g).getHeight())
        );
        g.setFont(getFont().deriveFont(getFont().getSize() / 2f));
        g.drawString(
                reason,
                getWidth() / 2 - g.getFontMetrics().stringWidth(reason) / 2,
                (int) (getHeight() * 0.25f)
        );
        g.setFont(getFont());
        g.drawString(
                "Final score: " + score,
                getWidth() / 2 - g.getFontMetrics().stringWidth("Final score: " + score) / 2,
                (int) (getHeight() * 0.5f)
        );
        if (isHighScore) {
            g.drawString(
                    "Enter your name",
                    getWidth() / 2 - g.getFontMetrics().stringWidth("Enter your name") / 2,
                    (int) (getHeight() * 0.75f)
            );
            g.drawString(
                    playerName,
                    getWidth() / 2 - g.getFontMetrics().stringWidth(playerName) / 2,
                    (int) (getHeight() * 0.85f)
            );
        } else {
            g.drawString(
                    "Press ENTER to continue",
                    getWidth() / 2 - g.getFontMetrics().stringWidth("Press ENTER to continue") / 2,
                    (int) (getHeight() * 0.75f)
            );
        }
    }

    @Override
    public void consumeKeyEvent(KeyEvent e) {
        if (isHighScore) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER -> {
                    if (!playerName.isEmpty()) {
                        HighscoreSystem.writeHighScoreToFile(playerName, score);
                        getParent().add(new SnakeHighScoreScreen(getWidth(), getHeight(), getBackground(), getForeground(), getFont()));
                        getParent().remove(this);
                    }
                }
                case KeyEvent.VK_BACK_SPACE -> {
                    if (!playerName.isEmpty()) {
                        playerName = playerName.substring(0, playerName.length() - 1);
                    }
                }
                default -> {
                    char keyChar = e.getKeyChar();
                    if (Character.isLetterOrDigit(keyChar))
                        playerName += keyChar;
                }
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                getParent().add(new SnakeHighScoreScreen(getWidth(), getHeight(), getBackground(), getForeground(), getFont()));
                getParent().remove(this);
            }
        }
    }
}

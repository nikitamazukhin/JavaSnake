package Classes.HighscoreSystem;

public class HighScoreData {
    String[] playerNames;
    int[] highScores;

    public HighScoreData(String[] playerNames, int[] highScores) {
        this.playerNames = playerNames;
        this.highScores = highScores;
    }

    public String[] getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public int[] getHighScores() {
        return highScores;
    }

    public void setHighScores(int[] highScores) {
        this.highScores = highScores;
    }
}

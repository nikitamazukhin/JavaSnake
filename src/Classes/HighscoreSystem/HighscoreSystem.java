package Classes.HighscoreSystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class HighscoreSystem {
    private static byte[] intToByteArray(int n) {
        byte[] result = new byte[4];

        for (int i = 3; i > 0; i--)
            result[i] = (byte)(n >> 8 * (3 - i) & 0xFF);

        return result;
    }
    private static int byteArrayToInt(byte[] encodedInt) {
        return   encodedInt[3] & 0xFF |
                (encodedInt[2] & 0xFF) << 8 |
                (encodedInt[1] & 0xFF) << 16 |
                (encodedInt[0] & 0xFF) << 24;
    }

    public static String[] getNamesFromFile() {
        try (FileInputStream inputStream = new FileInputStream("highscores.bin")) {
            String[] playerNames = new String[10];
            int nameLen;

            for (int i = 0; i < 10; i++) {
                nameLen = inputStream.read();

                if (nameLen != -1) {
                    playerNames[i] = new String(inputStream.readNBytes(nameLen * 2), StandardCharsets.UTF_16BE);
                    inputStream.readNBytes(4);
                }
                else
                    break;
            }
            inputStream.close();
            return playerNames;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int[] getScoresFromFile() {
        try (FileInputStream inputStream = new FileInputStream("highscores.bin")) {
            int[] highScores = new int[10];
            int nameLen;

            for (int i = 0; i < 10; i++) {
                nameLen = inputStream.read();

                if (nameLen != -1) {
                    inputStream.readNBytes( nameLen * 2);
                    highScores[i] = byteArrayToInt(inputStream.readNBytes(4));
                }
                else
                    break;
            }
            inputStream.close();
            return highScores;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkForHighScore(int score) {
        int[] highScores = getScoresFromFile();
        for (int highScore : highScores)
            if(score > highScore)
                return true;
        return false;
    }

    private static HighScoreData getUpdatedScoreData(String playerName, int score) {
        String[] playerNames = getNamesFromFile();
        String[] newPlayerNames = new String[playerNames.length];
        int[] highScores = getScoresFromFile();
        int[] newHighScores = new int[highScores.length];

        int newHighScoreIndex = 0;
        for (int i = 0; i < highScores.length; i++) {
            if (score > highScores[i]) {
                newHighScoreIndex = i;
                break;
            }
        }

        System.arraycopy(playerNames, 0, newPlayerNames, 0, newHighScoreIndex);
        System.arraycopy(highScores, 0, newHighScores, 0, newHighScoreIndex);

        newPlayerNames[newHighScoreIndex] = playerName;
        newHighScores[newHighScoreIndex] = score;

        if (newHighScoreIndex != newHighScores.length - 1) {
            System.arraycopy(playerNames, newHighScoreIndex, newPlayerNames, newHighScoreIndex + 1, playerNames.length - newHighScoreIndex - 1);
            System.arraycopy(highScores, newHighScoreIndex, newHighScores, newHighScoreIndex + 1, highScores.length - newHighScoreIndex - 1);
        }

        return new HighScoreData(newPlayerNames, newHighScores);
    }

    public static void writeHighScoreToFile (String playerName, int score) {
        try {
            HighScoreData highScoreData = getUpdatedScoreData(playerName, score);
            String[] playerNames = highScoreData.getPlayerNames();
            int[] highScores = highScoreData.getHighScores();

            FileOutputStream outputStream = new FileOutputStream("highscores.bin");

            byte[] nameLengths = new byte[highScores.length];
            byte[][] namesBytes = new byte[highScores.length][];

            for (int i = 0; i < highScores.length; i++) {
                String name = playerNames[i];

                if (name != null) {
                    nameLengths[i] = (byte)(name.length());
                    namesBytes[i] = name.getBytes(StandardCharsets.UTF_16BE);
                }
                else {
                    nameLengths[i] = 0;
                    namesBytes[i] = new byte[0];
                }

                outputStream.write(nameLengths[i]);
                outputStream.write(namesBytes[i]);
                outputStream.write(intToByteArray(highScores[i]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

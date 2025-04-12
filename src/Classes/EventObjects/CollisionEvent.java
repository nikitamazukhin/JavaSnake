package Classes.EventObjects;

import java.util.EventObject;

public class CollisionEvent extends EventObject {
    private final String reason;
    private final int score;
    private final boolean isHighScore;

    public CollisionEvent(Object source, boolean hitWall, int score, boolean isHighScore) {
        super(source);
        if (hitWall)
            switch ((int) (Math.random() * 5)) {
                case 0 -> reason = "You ran head first into a wall.";
                case 1 -> reason = "You thought the wall was a delicious snack.";
                case 2 -> reason = "You didn't see that wall coming.";
                case 3 -> reason = "You broke your fangs on a solid wall.";
                case 4 -> reason = "You suffered a head injury by ramming into a wall.";
                default -> reason = "You hit a wall.";
            }
        else
            switch ((int) (Math.random() * 5)) {
                case 0 -> reason = "You mistook your tail for food.";
                case 1 -> reason = "You thought your tail was a delicious snack.";
                case 2 -> reason = "You got too hungry.";
                case 3 -> reason = "You tried to eat yourself.";
                case 4 -> reason = "You sank your own fangs into yourself.";
                default -> reason = "You bit yourself.";
            }
        this.score = score;
        this.isHighScore = isHighScore;
    }

    public String getReason() {
        return reason;
    }

    public int getScore() {
        return score;
    }

    public boolean isHighScore() {
        return isHighScore;
    }
}

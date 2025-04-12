package Classes;

import Enums.Direction;

public class Snake {
    private Coordinate2i[] segments;
    private Direction moveDirection;
    private boolean ateRecently;

    public Snake(int gridWidth, int gridHeight) {
        int gridCenterX = gridWidth / 2;
        int gridCenterY = gridHeight / 2;

        segments = new Coordinate2i[]{
                new Coordinate2i(gridCenterX, gridCenterY)
        };

        moveDirection = Direction.DOWN;
        ateRecently = false;
    }

    private void addSegment(int x, int y) {
        Coordinate2i[] newSegments = new Coordinate2i[segments.length + 1];
        System.arraycopy(segments, 0, newSegments, 0, segments.length);
        newSegments[newSegments.length - 1] = new Coordinate2i(x, y);
        segments = newSegments;
    }

    public void eatFood() {
        ateRecently = true;
    }

    public void move() {
        int prevX = segments[0].x;
        int prevY = segments[0].y;

        if (segments.length > 1) {
            int tempX;
            int tempY;

            for (int i = 1; i < segments.length; i++) {
                tempX = segments[i].x;
                tempY = segments[i].y;

                segments[i].x = prevX;
                segments[i].y = prevY;

                prevX = tempX;
                prevY = tempY;
            }
        }

        switch (moveDirection) {
            case UP -> segments[0].y--;
            case DOWN -> segments[0].y++;
            case LEFT -> segments[0].x--;
            case RIGHT -> segments[0].x++;
        }

        if (ateRecently) {
            addSegment(prevX, prevY);
            ateRecently = false;
        }
    }

    public boolean checkWallCollision(int gridWidth, int gridHeight) {
        return segments[0].x < 0 || segments[0].x > gridWidth - 1 ||
                segments[0].y < 0 || segments[0].y > gridHeight - 1;
    }

    public boolean checkSegmentCollision() {
        if (segments.length > 1) {
            for (int i = 1; i < segments.length; i++) {
                if (segments[0].x == segments[i].x && segments[0].y == segments[i].y)
                    return true;
            }
        }
        return false;
    }

    public boolean checkCoordCollision(int x, int y) {
        return segments[0].x == x && segments[0].y == y;
    }

    public boolean checkCoordAvailability(int x, int y) {
        for (Coordinate2i segment : segments) {
            if (segment.x == x && segment.y == y)
                return false;
        }
        return true;
    }

    public int getFoodCount() {
        return segments.length - 1;
    }

    public Coordinate2i[] getBodyCoordinates() {
        Coordinate2i[] body = new Coordinate2i[segments.length];
        System.arraycopy(segments, 0, body, 0, body.length);

        return body;
    }

    public void setDirection(Direction direction) {
        moveDirection = direction;
    }

    public Direction getMoveDirection() {
        return moveDirection;
    }
}

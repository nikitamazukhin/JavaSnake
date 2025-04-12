package Classes;

public class Coordinate2i {
    int x;
    int y;

    public Coordinate2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate2i(Coordinate2i coordinate) {
        this.x = coordinate.x;
        this.y = coordinate.y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

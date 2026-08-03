public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point translate(Point other) {
        return new Point(x + other.getX(), y + other.getY());
    }

    public Point translate(int x, int y) {
        return new Point(this.x + x, this.y + y);
    }

    public void set(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    public void addX(int dx) {
        x += dx;
    }

    public void addY(int dy) {
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point copy() {
        return new Point(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
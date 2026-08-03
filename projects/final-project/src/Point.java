public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(java.awt.Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public Point translate(Point other) {
        return new Point(x + other.getX(), y + other.getY());
    }

    public Point translate(int x, int y) {
        return new Point(this.x + x, this.y + y);
    }

    public double distance(Point other) {
        return Math.sqrt(Math.pow(x - other.getX(), 2) + Math.pow(y - other.getY(), 2));
    }

    public double distanceToLine(Point one, Point two) {
        return Math.abs((two.getY() - one.getY()) * x - (two.getX() - one.getX()) * y + two.getX()*one.getY() - two.getY() - one.getX())/
                Math.sqrt(Math.pow(one.getX() - two.getX(), 2) + Math.pow(one.getY() - two.getY(), 2));
    }

    public boolean near(Point other, int distance) {
        return distance(other) < distance;
    }

    public boolean nearLine(Point one, Point two, int distance) {
        return distanceToLine(one, two) < distance;
    }

    public void set(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    public java.awt.Point toAwt() {
        return new java.awt.Point(x, y);
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
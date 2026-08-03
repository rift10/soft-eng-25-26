public class Point {
    
    private int x;
    private int y;

    private static double multiplier = 300;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point scale(double scalar) {
        x = (int) (x * scalar);
        y = (int) (y * scalar);
        return this;
    }

    public Point scale() {
        double dist = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        if (dist == 0) return new Point(0, 0);
        // this is the actual hyperbolic formula
        scale(multiplier * Math.tanh(dist/2)/dist);
        return this;
    }

    public Point translate(Point other) {
        return new Point(x + other.getX(), y + other.getY());
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

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
}

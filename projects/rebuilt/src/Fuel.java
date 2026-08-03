import java.awt.Rectangle;
import java.util.function.Supplier;

public class Fuel {

    private Point currentPos;
    private boolean isPickedUp = false;
    private Rectangle bounds = new Rectangle(Rebuilt.FUEL_DIAMETER, Rebuilt.FUEL_DIAMETER);
    private int slow = 1;
    private int vx = 0;
    private int vy = 0;
    private boolean isIntaked;
    private Supplier<Point> playerPos;

    public Fuel(Point start) {
        this.currentPos = start;
    }

    public void periodic() {
        if (isIntaked) {
            currentPos.set(playerPos.get());
            return;
        }
        currentPos.addX(vx);
        currentPos.addY(vy);
        bounds.setLocation(currentPos.getX(), currentPos.getY());
        vx -= slow;
        vy -= slow;
        if (vx < 0) vx = 0;
        if (vy < 0) vy = 0;
    }

    public void updateVel(int vx, int vy) {
        this.vx += vx;
        this.vy += vy;
    }

    public void getIntaked(Supplier<Point> playerPos) {
        isIntaked = true;
        this.playerPos = playerPos;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public Point getCurrentPos() {
        return currentPos;
    }

    public int getVX() {
        return vx;
    }

    public int getVY() {
        return vy;
    }
}

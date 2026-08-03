import java.awt.Color;
import java.awt.Rectangle;

public class NPC implements Robot {

    private final Color ALLIANCE = Rebuilt.RED_COLOR;
    private final double MOVE_PROBABILITY = 0.25;

    private Rectangle bounds = new Rectangle(Rebuilt.ROBOT_WIDTH, Rebuilt.ROBOT_HEIGHT);

    private Point currentPos = Rebuilt.ORIGIN.copy().translate(400, 400);
    private double currentDirection = 0;

    public void periodic() {
        if (Math.random() < MOVE_PROBABILITY) {
            int toAdd = (int) (Math.random() * 20) - 10;
            if (currentPos.getX() + toAdd > 0 && currentPos.getX() + toAdd < Rebuilt.FIELD_WIDTH)
                currentPos.addX(toAdd);    
        }
        if (Math.random() < MOVE_PROBABILITY) {
            int toAdd = (int) (Math.random() * 20) - 10;
            if (currentPos.getY() + toAdd > 0 && currentPos.getY() + toAdd < Rebuilt.FIELD_HEIGHT)
                currentPos.addY(toAdd);
        }
        bounds.setLocation(currentPos.getX(), currentPos.getY());
        // currentDirection += (Math.random() * 2) - 1;
    }

    @Override
    public Color getAlliance() {
        return ALLIANCE;
    }

    @Override
    public Point getPosition() {
        return currentPos;
    }

    @Override
    public double getDirection() {
        return currentDirection;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    // TODO

    @Override
    public int getVX() {
        return 1;
    }

    @Override
    public int getVY() {
        return 1;
    }
}

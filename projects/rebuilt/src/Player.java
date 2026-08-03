import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Player extends KeyAdapter implements Robot {

    private final int MOVE_AMOUNT = 3;
    private final double ROTATE_AMOUNT = 0.035; // ~2 degrees
    private final Color ALLIANCE = Rebuilt.BLUE_COLOR;

    private Rectangle bounds = new Rectangle(Rebuilt.ROBOT_WIDTH, Rebuilt.ROBOT_HEIGHT);
    
    private Point currentPos = Rebuilt.ORIGIN.copy();
    private double currentDirection = 0;
    
    private int currentKeyCode, previousKeyCode;

    private int dx, dy;
    private double dtheta;

    private boolean isIntaking;

    public void periodic() {
        // update coordinates with input from player
        if (currentPos.getX() + dx > 0 && currentPos.getX() + dx < Rebuilt.FIELD_WIDTH)
            currentPos.addX(dx);
        if (currentPos.getY() + dy > 0 && currentPos.getY() + dy < Rebuilt.FIELD_HEIGHT)
            currentPos.addY(dy);
        currentDirection += dtheta;
        bounds.setLocation(currentPos.getX() - Rebuilt.ROBOT_WIDTH/2, currentPos.getY() - Rebuilt.ROBOT_HEIGHT / 2);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentKeyCode = e.getKeyCode();
        // use WASD for translational movement
        if (isLeft(currentKeyCode)) {
            dx = -MOVE_AMOUNT;
        } else if (isRight(currentKeyCode)) {
            dx = MOVE_AMOUNT;
        } else if (isUp(currentKeyCode)) {
            dy = -MOVE_AMOUNT;
        } else if (isDown(currentKeyCode)) {
            dy = MOVE_AMOUNT;
        }
        // use left/right arrow keys for rotational movement
        else if (isTurningLeft(currentKeyCode)) {
            dtheta = -ROTATE_AMOUNT;
        } else if (isTurningRight(currentKeyCode)) {
            dtheta = ROTATE_AMOUNT;
        }

        isIntaking = currentKeyCode == KeyEvent.VK_SPACE;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        previousKeyCode = e.getKeyCode();

        // reset dx and dy when a key is released
        if (isLeft(previousKeyCode) || isRight(previousKeyCode))
            dx = 0;
        if (isUp(previousKeyCode) || isDown(previousKeyCode))
            dy = 0;
        if (isTurningLeft(previousKeyCode) || isTurningRight(previousKeyCode))
            dtheta = 0;
    }

    /* ------------------ helpers --------------------- */

    private boolean isUp(int key) {
        return key == KeyEvent.VK_W;
    }

    private boolean isDown(int key) {
        return key == KeyEvent.VK_S;
    }

    private boolean isLeft(int key) {
        return key == KeyEvent.VK_A;
    }

    private boolean isRight(int key) {
        return key == KeyEvent.VK_D;
    }

    private boolean isTurningLeft(int key) {
        return key == KeyEvent.VK_LEFT;
    }

    private boolean isTurningRight(int key) {
        return key == KeyEvent.VK_RIGHT;
    }

    /* ------------------ getters --------------------- */

    @Override
    public Color getAlliance() {
        return ALLIANCE;
    }

    @Override
    public Point getPosition() {
        return Rebuilt.ORIGIN.translate(currentPos);
    }

    @Override
    public double getDirection() {
        return currentDirection;
    }

    public int getCurrentKeyCode() {
        return currentKeyCode;
    }

    public int getReleasedKeyCode() {
        return previousKeyCode;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public int getVX() {
        return dx;
    }

    @Override
    public int getVY() {
        return dy;
    }

    public boolean isIntaking() {
        return isIntaking;
    }

}
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Player extends KeyAdapter {

    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;
    private final int MOVE_AMOUNT = 2;

    private Point pos = new Point(0, 0);

    private int dx, dy;

    private int currentKeyCode;
    private int previousKeyCode;

    private int currentDirection = 0;

    private List<Point> points = new ArrayList<>();

    public void periodic() {
        // update coordinates with input from player
        pos.addX(-dx);
        pos.addY(-dy);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        currentKeyCode = e.getKeyCode();

        // use WASD + arrow keys to move
        if (isLeft(currentKeyCode)) {
            currentDirection = 3;
            dx = -MOVE_AMOUNT;
        } else if (isRight(currentKeyCode)) {
            currentDirection = 1;
            dx = MOVE_AMOUNT;
        } else if (isUp(currentKeyCode)) {
            currentDirection = 2;
            dy = -MOVE_AMOUNT;
        } else if (isDown(currentKeyCode)) {
            currentDirection = 0;
            dy = MOVE_AMOUNT;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        previousKeyCode = e.getKeyCode();

        // reset dx and dy when a key is released
        if (isLeft(previousKeyCode) || isRight(previousKeyCode))
            dx = 0;
        if (isUp(previousKeyCode) || isDown(previousKeyCode))
            dy = 0;
    }

    /* ------------------ helpers --------------------- */

    private boolean isUp(int key) {
        return key == KeyEvent.VK_UP || key == KeyEvent.VK_W;
    }

    private boolean isDown(int key) {
        return key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S;
    }

    private boolean isLeft(int key) {
        return key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A;
    }

    private boolean isRight(int key) {
        return key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D;
    }

    /* ------------------ getters --------------------- */

    public Point getPosition() {
        return pos;
    }

    public int getDirection() {
        return currentDirection;
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getCurrentKeyCode() {
        return currentKeyCode;
    }

    public int getReleasedKeyCode() {
        return previousKeyCode;
    }

}

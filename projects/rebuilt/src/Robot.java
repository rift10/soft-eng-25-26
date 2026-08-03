import java.awt.Color;
import java.awt.Rectangle;

public interface Robot {

    public Point getPosition();

    public double getDirection();

    public Rectangle getBounds();
    
    public Color getAlliance();

    public int getVX();

    public int getVY();

    default boolean isBlueAlliance() {
        return getAlliance() == Rebuilt.BLUE_COLOR;
    }
}

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Rebuilt extends JPanel {

    public static final double MULTIPLIER = 2;
    public static final int FIELD_WIDTH = (int) (651 * MULTIPLIER);
    public static final int FIELD_HEIGHT = (int) (318 * MULTIPLIER);

    public static final Point BLUE_HUB_CENTER = new Point((int) (182 * MULTIPLIER), FIELD_HEIGHT/2);
    public static final Point RED_HUB_CENTER = new Point(FIELD_WIDTH - (int) (182 * MULTIPLIER), FIELD_HEIGHT/2);

    public static final Point FUEL_START = new Point(FIELD_WIDTH/2 - 36, FIELD_HEIGHT/2 - 91);
    public static final Point FUEL_END = new Point(FIELD_WIDTH/2 + 36, FIELD_HEIGHT/2 + 91);

    public static final Point ORIGIN = new Point(0, 0);

    public static final Color BLUE_COLOR = new Color(70, 100, 200);
    public static final Color RED_COLOR = new Color(210, 80, 100);
    public static final Color FUEL_COLOR = new Color(220, 180, 50);

    public static final int ROBOT_MASS = 135;
    public static final int FUEL_MASS = 2;
    
    public static final int ROBOT_WIDTH = 80;
    public static final int ROBOT_HEIGHT = 80;
    public static final int FUEL_DIAMETER = 20;
    
    public static final int HUB_SIDE = 200;
    public static final int HUB_X = 200;

    private final Player player = new Player();
    private final NPC npc = new NPC();
    private final List<Fuel> fuels = new ArrayList<>();
    
    public Rebuilt() {
        addKeyListener(player);
        setFocusable(true);
        setVisible(true);
        Timer timer = new Timer(17, (ActionEvent e) -> {
            periodic();
        });
        timer.start();
        for (int i = FUEL_START.getX(); i < FUEL_END.getX(); i += FUEL_DIAMETER) {
            for (int j = FUEL_START.getY(); j < FUEL_END.getY(); j += FUEL_DIAMETER) {
                fuels.add(new Fuel(new Point(i, j)));
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Rebuilt gui = new Rebuilt();
        frame.add(gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FIELD_WIDTH, FIELD_HEIGHT);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.gray);
        g2d.fillRect(ORIGIN.getX(), ORIGIN.getY(), FIELD_WIDTH, FIELD_HEIGHT);
        g2d.setColor(Color.black);
        fillQuad(g2d, BLUE_HUB_CENTER, HUB_SIDE, HUB_SIDE);
        fillQuad(g2d, RED_HUB_CENTER, HUB_SIDE, HUB_SIDE);
        drawRobot(g2d, player);
        drawRobot(g2d, npc);
        g2d.setColor(FUEL_COLOR);
        for (Fuel fuel : fuels) {
            g2d.fillOval(fuel.getCurrentPos().getX(), fuel.getCurrentPos().getY(), FUEL_DIAMETER, FUEL_DIAMETER);
        }
    }

    private void periodic() {
        repaint();
        player.periodic();
        npc.periodic();
        for (Fuel fuel : fuels) {
            fuel.periodic();
            if (fuel.getBounds().intersects(player.getBounds())) {
                if (player.isIntaking()) {
                    fuel.getIntaked(() -> player.getPosition());
                } else {
                    fuel.updateVel(player.getVX(), player.getVY());
                }
            }
        }
    }

    private void drawRobot(Graphics2D g2d, Robot robot) {
        g2d.setColor(robot.getAlliance());
        g2d.rotate(robot.getDirection(), robot.getPosition().getX(), robot.getPosition().getY());
        fillQuad(g2d, robot.getPosition(), ROBOT_WIDTH, ROBOT_HEIGHT);
        g2d.rotate(-robot.getDirection(), robot.getPosition().getX(), robot.getPosition().getY());
    }

    private void fillQuad(Graphics2D g2d, Point center, int width, int height) {
        g2d.fillPolygon(
                new int[] {
                        ORIGIN.getX() + center.getX() - width / 2,
                        ORIGIN.getX() + center.getX() - width / 2,
                        ORIGIN.getX() + center.getX() + width / 2,
                        ORIGIN.getX() + center.getX() + width / 2,
                },
                new int[] {
                        ORIGIN.getY() + center.getY() - height / 2,
                        ORIGIN.getY() + center.getY() + height / 2,
                        ORIGIN.getY() + center.getY() + height / 2,
                        ORIGIN.getY() + center.getY() - height / 2,
                },
        4);
    }
}
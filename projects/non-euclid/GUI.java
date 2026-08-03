import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUI extends JPanel {
    
    public static final int WIDTH = 500;
    public static final int HEIGHT = 800;
                
    private final Player player = new Player();
    private final Grid grid = new Grid(player);
    // private final JLabel label = new JLabel();

    public GUI() {
        // label.setBounds(20, 50, 300, 50);
        // grid.add(label);
        addKeyListener(player);
        add(grid);
        setFocusable(true);
        setVisible(true);
        Timer timer = new Timer(17, (ActionEvent e) -> {
           periodic();
        });
        timer.start();
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GUI gui = new GUI();
        frame.add(gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        grid.paintComponent(g);
        g2d.setColor(Color.black);
        g2d.rotate(player.getDirection()* (-Math.PI/2), Grid.ORIGIN.getX(), Grid.ORIGIN.getY());
        g2d.drawPolygon(
            new Polygon(
                new int[] {
                        Grid.ORIGIN.getX() -Player.WIDTH / 2,
                        Grid.ORIGIN.getX(),
                        Grid.ORIGIN.getX() + Player.WIDTH / 2},
                new int[] {
                        Grid.ORIGIN.getY() -Player.HEIGHT / 2,
                        Grid.ORIGIN.getY(),
                        Grid.ORIGIN.getY() -Player.HEIGHT / 2},
                        3));
    }
    
    private void periodic() {
        repaint();
        player.periodic();
        // if (player.getCurrentKeyCode() != 0)
        // System.out.println("current key code: " + player.getCurrentKeyCode());
        // System.out.println("(" + player.getX() + ", " + player.getY() + ")");
    }
}

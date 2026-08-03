import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;

import javax.swing.JPanel;

public class Grid extends JPanel {

    private int x = 0;
    private int y = 0;

    private final Player player;
    private final Color[][] grid;
    private static final int ROWS = 20;
    private static final int COLUMNS = 20;
    private static final int CELL_HEIGHT = 80;
    private static final int CELL_BASE = 80;
    private static final int SIDE = 1000;
    
    public static final Point ORIGIN = new Point(600, 600);

    public Grid(Player player) {
        this.player = player;
        grid = new Color[ROWS][COLUMNS];
        setColors();
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        paintGrid(g2d);
    }

    private void paintGrid(Graphics2D g) {
        x = -SIDE/2;
        y = -SIDE/2;
        int height = CELL_HEIGHT;
        for (int c = 0; c < COLUMNS; c++) {
            y = -SIDE/2;
            x += CELL_BASE;
            for (int r = 0; r < ROWS; r++) {
                y += height;
                List<Point> points = List.of();
                points = List.of(new Point(x, y), new Point(x, y - height),
                        new Point(x + CELL_BASE, y - height), new Point(x + CELL_BASE, y));
                // paintCell(r, c, points, g);
                points = points.stream().map(p -> p.translate(player.getPosition()).scale().translate(ORIGIN)).toList();
                paintCell(r, c, points, g);
            }
        }
    }

    public void paintCell(int row, int column, List<Point> points, Graphics2D g) {
        int[] xs = points.stream().mapToInt(p -> p.getX()).toArray();
        int[] ys = points.stream().mapToInt(p -> p.getY()).toArray();
        Polygon p = new Polygon(xs, ys, 4);
        
        g.setColor(Color.black);
        g.drawPolygon(p);
        g.setColor(grid[row][column]);
        g.fillPolygon(p);

    }

    private void setColors() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLUMNS; c++) {
                grid[r][c] = color(r, c);
            }
        }
    }

    private Color color(int row, int col) {
        return new Color(Math.min(row * 20, 255), Math.min(col * 20, 255), 0, 100);
    }

}

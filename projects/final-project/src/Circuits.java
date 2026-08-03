import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Circuits extends JPanel {

    public static final double MULTIPLIER = 2;
    public static final int PANEL_WIDTH = (int) (651 * MULTIPLIER);
    public static final int PANEL_HEIGHT = (int) (318 * MULTIPLIER);

    public static final Point ORIGIN = new Point(0, 0);

    public static final Color BLUE_COLOR = new Color(70, 100, 200);
    public static final Color RED_COLOR = new Color(210, 80, 100);

    public static final int GATE_WIDTH = 80;
    public static final int GATE_HEIGHT = 40;

    public static final int WIRE_RADIUS = 5;
    public static final int SNAP_TOL = 20;

    public static List<Wire> wires = new ArrayList<>();
    public static List<Gate> gates = new ArrayList<>();

    public static List<Port> ports = new ArrayList<>();

    public static List<List<Boolean>> halfAdder = new ArrayList<>();

    private JLabel labelTop;
    private JLabel labelZero;
    private JLabel labelOne;
    private JLabel labelTwo;
    private JLabel labelThree;
    private JLabel labelFour;

    public Circuits() {
        labelTop = new JLabel();
        labelZero = new JLabel();
        labelOne = new JLabel();
        labelTwo = new JLabel();
        labelThree = new JLabel();
        labelFour = new JLabel();
        labelTop.setLocation(400, 100);
        labelZero.setLocation(50, 400);
        labelOne.setLocation(50, 415);
        labelTwo.setLocation(50, 430);
        labelThree.setLocation(50, 445);
        labelFour.setLocation(50, 460);
        Dimension dim = new Dimension(200, 30);
        labelTop.setSize(dim);
        labelZero.setSize(dim);
        labelOne.setSize(dim);
        labelTwo.setSize(dim);
        labelThree.setSize(dim);
        labelFour.setSize(dim);
        labelTop.setText("Half Adder Circuit");
        labelZero.setText("In 1    In 2    Out 1   Out 2");
        add(labelTop);
        add(labelZero);
        add(labelOne);
        add(labelTwo);
        add(labelThree);
        add(labelFour);

        for (int i = 1; i <= 6; i++) {
            wires.add(new Wire(new Point(50, 20 * i), new Point(100, 20 * i)));
        }
        
        gates.add(new Gate(GateType.AND, new Point(1100, 40)));
        gates.add(new Gate(GateType.NOT, new Point(1100, 80)));
        gates.add(new Gate(GateType.OR, new Point(1100, 120)));
        gates.add(new Gate(GateType.XOR, new Point(1100, 160)));
        
        for (Gate gate : gates) add(gate);

        ports.add(new Port("A", new Point(200, 200)));
        ports.add(new Port("B", new Point(200, 300)));
        ports.add(new Port("S", new Point(1000, 200)));
        ports.add(new Port("C", new Point(1000, 300)));

        setLayout(null);

        Timer timer = new Timer(17, (ActionEvent e) -> {
            periodic();
        });
        timer.start();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point mousePos = new Point(getMousePosition());
                for (Wire wire : wires) {
                    if (snapWire(wire, mousePos, 1)) return;
                    if (snapWire(wire, mousePos, 2)) return;
                }
                for (Gate gate : gates) {
                    if (gate.isWaiting()) {
                        gate.setPos(mousePos);
                        gate.setWaiting(false);
                        // label.setText("moved " + gate.type().toString());
                        return;
                    }
                    if (gate.getBounds().contains(e.getPoint())) {
                        gate.setWaiting(true);
                        // label.setText(gate.type().toString());
                    }
                }
            }
        });

        halfAdder.add(List.of(Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false), Boolean.valueOf(false)));
        halfAdder.add(List.of(Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false)));
        halfAdder.add(List.of(Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true), Boolean.valueOf(false)));
        halfAdder.add(List.of(Boolean.valueOf(true), Boolean.valueOf(true), Boolean.valueOf(false), Boolean.valueOf(true)));

        setFocusable(true);
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Circuits gui = new Circuits();
        frame.add(gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ports.forEach(port -> {
            fillCircle(g2d, port.point(), WIRE_RADIUS);
        });
        wires.forEach(wire -> {
            wire.periodic();
            if (wire.getPos(1).getY() != wire.getPos(2).getY()) {
                Point one = wire.getLowPoint();
                Point two = wire.getHighPoint();
                g2d.drawLine(one.getX(), one.getY(), one.getX(), two.getY());
                g2d.drawLine(one.getX(), two.getY(), two.getX(), two.getY());
            } else {
                g2d.drawLine(wire.getPos(1).getX(), wire.getPos(1).getY(), wire.getPos(2).getX(), wire.getPos(2).getY());
            }
            for (int i = 1; i <= 2; i++) {
                if (wire.isWaiting(i)) {
                    g2d.setColor(Color.red);
                }
                fillCircle(g2d, wire.getPos(i), WIRE_RADIUS);
                g2d.setColor(Color.black);
            }
        });
    }
    
    private void periodic() {
        revalidate();
        repaint();
        gates.forEach(gate -> gate.periodic());
        checkCircuit();
    }

    private void fillCircle(Graphics2D g2d, Point center, int radius) {
        g2d.fillOval(center.getX()-radius, center.getY()-radius, 2*radius, 2*radius);
    }

    private boolean snapWire(Wire wire, Point mousePos, int num) {
        Point point = wire.getPos(num);
        if (wire.isWaiting(num)) {
            wire.setPos(num, mousePos);
            wire.setWaiting(num, false);
            for (Port port : ports) {
                if (point.near(port.point(), SNAP_TOL)) {
                    Connectable otherCon = wire.getOtherCon(num);
                    if (otherCon != null) port.connect(otherCon);
                    wire.connect(num, port, () -> port.point());
                    return true;
                }
            }
            for (Gate gate : gates) {
                if (point.near(gate.outputPoint(), SNAP_TOL)) {
                    Connectable otherCon = wire.getOtherCon(num);
                    if (otherCon != null) gate.connect(otherCon);
                    wire.connect(num, gate, () -> gate.outputPoint());
                    return true;
                }
                if (gate.type() == GateType.NOT
                        && point.near(gate.inputOnePoint(), SNAP_TOL)) {
                    wire.connect(num, gate, () -> gate.inputOnePoint());
                    return true;
                }
                double oneDist = Integer.MAX_VALUE;
                double twoDist = Integer.MAX_VALUE;
                if (point.near(gate.inputOnePoint(), SNAP_TOL)) {
                    oneDist = point.distance(gate.inputOnePoint());
                }
                if (point.near(gate.inputTwoPoint(), SNAP_TOL)) {
                    twoDist = point.distance(gate.inputTwoPoint());
                }
                if (!(oneDist == Integer.MAX_VALUE && twoDist == Integer.MAX_VALUE)) {
                    if (wire.getOtherCon(num) != null) wire.getOtherCon(num).connect(gate);
                    wire.connect(num, gate, Math.min(oneDist, twoDist) == oneDist ? () -> gate.inputOnePoint()
                            : () -> gate.inputTwoPoint());
                    return true;
                }
            }
            if (wire.getCon(num) != null) {
                wire.getCon(num).disconnect(wire.getOtherCon(num));
            }
            if (wire.getOtherCon(num) != null) {
                wire.getOtherCon(num).disconnect(wire.getCon(num));
            }
            wire.disconnect(num);
            return false;
        }
        if (point.near(mousePos, WIRE_RADIUS * 2)) {
            wire.setWaiting(num, true);
        }
        return false;
    }

    public boolean checkCircuit() {
        if (ports.get(0).getConnects().size() < 2) return false;
        if (ports.get(1).getConnects().size() < 2) return false;
        if (ports.get(2).getConnects().size() < 1) return false;
        if (ports.get(3).getConnects().size() < 1) return false;
        Gate one = (Gate) ports.get(0).getConnects().get(0);
        Gate two = (Gate) ports.get(0).getConnects().get(1);
        List<List<Boolean>> table = new ArrayList<>();
        record(table, one, two, true, true);
        record(table, one, two, true, false);
        record(table, one, two, false, true);
        record(table, one, two, false, false);
        String strOne = new String();
        String strTwo = new String();
        String strThree = new String();
        String strFour = new String();
        for (int i = 0; i < table.get(0).size(); i++) {
            strOne += table.get(0).get(i).toString();
            if (table.get(0).get(i)) strOne += " ";
            strOne += "  ";
        }
        for (int i = 0; i < table.get(1).size(); i++) {
            strTwo += table.get(1).get(i).toString();
            if (table.get(1).get(i)) strTwo += " ";
            strTwo += "  ";
        }
        for (int i = 0; i < table.get(2).size(); i++) {
            strThree += table.get(2).get(i).toString();
            if (table.get(2).get(i)) strThree += " ";
            strThree += "  ";
        }
        for (int i = 0; i < table.get(3).size(); i++) {
            strFour += table.get(3).get(i).toString();
            if (table.get(3).get(i)) strFour += " ";
            strFour += "  ";
        }
        labelOne.setText(strOne);
        labelTwo.setText(strTwo);
        labelThree.setText(strThree);
        labelFour.setText(strFour);
        return matches(table, halfAdder);
    }

    // public List<Connectable> getNext(Connectable con) {
    //     List<Connectable> list = new ArrayList<>();
    //     if (con instanceof Gate) {
    //         Gate gate = (Gate) con;
    //         Connectable next = gate.getOutputCon();
    //         if (next instanceof Port) {
    //             return list;
    //         } else {
    //             return getNext(next);
    //         }
    //     }
    //     return null;
    // }

    public void record(List<List<Boolean>> list, Gate one, Gate two, boolean a, boolean b) {
        list.add(List.of(Boolean.valueOf(a), Boolean.valueOf(b), Boolean.valueOf(one.run(a, b)), Boolean.valueOf(two.run(a, b))));
    }

    public boolean matches(List<List<Boolean>> test, List<List<Boolean>> ref) {
        return new HashSet<List<Boolean>>(ref).equals(new HashSet<List<Boolean>>(test));
    }

}
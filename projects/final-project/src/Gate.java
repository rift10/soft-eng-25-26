import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Gate extends JLabel implements Connectable {

    private GateType type;
    private ImageIcon icon;
    private ImageIcon redIcon;
    private Point pos;
    private boolean waiting;
    private Connectable outputCon;

    public Gate(GateType type, Point start) {
        pos = start;
        this.type = type;
        icon = new ImageIcon(new ImageIcon("resources/" + type.name().toLowerCase() + ".png").getImage().getScaledInstance(Circuits.GATE_WIDTH, Circuits.GATE_HEIGHT, ABORT));
        setIcon(icon);
        redIcon = new ImageIcon(new ImageIcon("resources/" + type.name().toLowerCase() + "Red.png").getImage().getScaledInstance(Circuits.GATE_WIDTH, Circuits.GATE_HEIGHT, ABORT));
        setBounds(new Rectangle(icon.getIconWidth(), icon.getIconHeight()));
        setVisible(true);
    }

    public void periodic() {
        setLocation(pos.getX(), pos.getY());
        repaint();
    }

    public Point outputPoint() {
        return pos.translate(icon.getIconWidth(), icon.getIconHeight()/2);
    }

    public Point inputOnePoint() {
        if (type.equals(GateType.NOT)) {
            return pos.translate(0, icon.getIconHeight()/2);
        }
        return pos.translate(0, icon.getIconHeight()/4);
    }
    
    public Point inputTwoPoint() {
        if (type.equals(GateType.NOT)) {
            return pos.translate(0, icon.getIconHeight()/2);
        }
        return pos.translate(0, 3*icon.getIconHeight()/4);
    }

    public void setPos(Point point) {
        pos.set(point);
    }
    
    public void setWaiting(boolean wait) {
        waiting = wait;
        if (wait) {
            setIcon(redIcon);
        } else {
            setIcon(icon);
        }
    }

    public boolean isWaiting() {
        return waiting;
    }

    public GateType type() {
        return type;
    }

    public Point getPos() {
        return pos;
    }

    public boolean run(boolean a, boolean b) {
        return switch (type) {
            case AND -> a && b;
            case NOT -> !a;
            case OR -> a || b;
            case XOR -> a ^ b;
        };
    }

    @Override
    public void connect(Connectable con) {
        outputCon = con;
    }

    public Connectable getOutputCon() {
        return outputCon;
    }

    @Override
    public void disconnect(Connectable con) {
        outputCon = null;
    }

}
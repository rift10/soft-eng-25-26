import java.util.function.Supplier;

public class WireEnd {

    private Supplier<Point> sup;
    private Wire wire;
    private Point location;

    public WireEnd(Wire wire, Point p) {
        this.wire = wire;
        location = p;
    }

    public void periodic() {
        if (sup != null) {
            setLocation(sup.get());
        }
    }

    public void setSupplier(Supplier<Point> sup) {
        this.sup = sup;
    }

    public void setLocation(Point loc) {
        location.set(loc);
    }

    public Point getLocation() {
        return location;
    }
}

import java.util.function.Supplier;

public class Wire {

    private WireEnd one;
    private WireEnd two;

    private boolean waitingOne;
    private boolean waitingTwo;

    private Connectable conOne;
    private Connectable conTwo;

    public Wire(Point p1, Point p2) {
        one = new WireEnd(this, p1);
        two = new WireEnd(this, p2);
    }

    public void periodic() {
        one.periodic();
        two.periodic();
    }
    
    public void connect(int port, Connectable con, Supplier<Point> sup) {
        if (port == 1) {
            one.setSupplier(sup);
        } else if (port == 2) {
            two.setSupplier(sup);
        }
        connect(con, port);
    }

    public void connect(Connectable con, int port) {
        if (port == 1) {
            conOne = con;
        } else if (port == 2) {
            conTwo = con;
        }
    }

    public void disconnect(int port) {
        if (port == 1) {
            conOne = null;
        } else if (port == 2) {
            conTwo = null;
        }
    }

    public void setPos(int num, Point loc) {
        if (num == 1) {
            one.setSupplier(null);
            one.setLocation(loc);
        } else if (num == 2) {
            two.setSupplier(null);
            two.setLocation(loc);            
        }
    }

    public void setWaiting(int num, boolean wait) {
        if (num == 1) {
            waitingOne = wait;
        } else if (num == 2) {
            waitingTwo = wait;
        }
    }

    public Point getPos(int num) {
        if (num == 1) {
            return one.getLocation();
        } else if (num == 2) {
            return two.getLocation();
        }
        return null;
    }

    public Connectable getCon(int num) {
        if (num == 1) {
            return conOne;
        } else if (num == 2) {
            return conTwo;
        }
        return null;
    }

    public Connectable getOtherCon(int num) {
        if (num == 1) {
            return conTwo;
        } else if (num == 2) {
            return conOne;
        }
        return null;
    }

    /** Lower Y but technically higher point */
    public Point getLowPoint() {
        return getPos(1).getY() < getPos(2).getY() ? getPos(1) : getPos(2);
    }
    
    /** Higher Y but technically lower point */
    public Point getHighPoint() {
        return getPos(1).getY() > getPos(2).getY() ? getPos(1) : getPos(2);
    }
    
    public boolean isWaiting(int num) {
        if (num == 1) {
            return waitingOne;
        } else if (num == 2) {
            return waitingTwo;
        }
        return false;
    }
}

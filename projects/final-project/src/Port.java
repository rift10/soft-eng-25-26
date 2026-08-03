import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Port implements Connectable {

    private Point point;
    private List<Connectable> connects;
    private String id;

    public Port(String id, Point point) {
        this.id = id;
        connects = new ArrayList<>();
        this.point = point;
    }

    public Point point() {
        return point;
    }
    
    public List<Connectable> getConnects() {
        return connects;
    }

    public String getId() {
        return id;
    }

    @Override
    public void connect(Connectable con) {
        connects.add(con);
    }

    @Override
    public void disconnect(Connectable con) {
        connects.remove(con);
    }

}

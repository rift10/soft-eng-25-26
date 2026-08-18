import java.util.List;
import java.util.Objects;

public class Day9 implements Day {

    private class Coord {
        private int x, y;

        public Coord(String[] ss) {
            this.x = Integer.parseInt(ss[0]);
            this.y = Integer.parseInt(ss[1]);;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Coord)) return false;
            Coord other = (Coord) obj;
            return (other.x() == x && other.y() == y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }


    @Override
    public boolean isPartTwo() {
        return false;
    }

    @Override
    public boolean isTesting() {
        return false;
    }

    private List<Coord> points;

    public static void main(String[] args) {
        new Day9().run(args[0]);
    }

    @Override
    public void run(String day) {
        points = getLines(day).map(line -> new Coord(line.split(","))).toList();
        print();
    }

    @Override
    public long partOne() {
        long max = 0;
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if (points.get(i).equals(points.get(j))) continue;
                // System.out.println(points.get(i) + " and " + points.get(j) + " = "+ (Math
                //         .abs(points.get(i).x() - points.get(j).x()) + 1) + "*" + 
                //         (Math.abs(points.get(i).y() - points.get(j).y()) + 1));
                if ((Math.abs(points.get(i).x() - points.get(j).x()) + 1) *
                        (Math.abs(points.get(i).y() - points.get(j).y()) + 1) > max) {
                            System.out.println(points.get(i) + " and " + points.get(j));
                        }
                max = Math.max(max, (Math.abs(points.get(i).x() - points.get(j).x()) + 1)*
                    (Math.abs(points.get(i).y() - points.get(j).y()) + 1));
            }
        }
        // 2147271168 too low
        return max;
    }

    @Override
    public long partTwo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'partTwo'");
    }

}
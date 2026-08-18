import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Day8 implements Day {

    private class Coord {
        private int x, y, z;

        public Coord(String[] ss) {
            this.x = Integer.parseInt(ss[0]);
            this.y = Integer.parseInt(ss[1]);;
            this.z = Integer.parseInt(ss[2]);;
        }

        public double getDist(Coord other) {
            return Math.sqrt(Math.pow(other.x() - x, 2) + Math.pow(other.y() - y, 2) + Math.pow(other.z() - z, 2));
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        public int z() {
            return z;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Coord)) return false;
            Coord other = (Coord) obj;
            return (other.x() == x && other.y() == y && other.z() == z);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ", " + z + ")";
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
    private List<List<Coord>> pairs = new ArrayList<>();
    private List<List<Coord>> circuits = new ArrayList<>();

    public static void main(String[] args) {
        new Day8().run(args[0]);
    }

    @Override
    public void run(String day) {
        points = getLines(day).map(line -> new Coord(line.split(","))).toList();
        print();
    }

    @Override
    public long partOne() {
        double maxDist = Integer.MAX_VALUE;
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if (points.get(i).equals(points.get(j))) continue;
                if (pairs.contains(List.of(points.get(i), points.get(j))) || pairs
                        .contains(List.of(points.get(j), points.get(i)))) continue;
                double currentDist = points.get(i).getDist(points.get(j));
                if (pairs.size() < 12) {
                    pairs.add(List.of(points.get(i), points.get(j)));
                    // TODO: fix
                } else if (currentDist < maxDist) {
                    pairs.add(List.of(points.get(i), points.get(j)));
                    maxDist = Math.max(maxDist, currentDist);
                }
            }
            System.out.println("done with " + i);
        }
        System.out.println("first checkpoint");
        pairs.sort(Comparator.comparing(pair -> pair.get(0).getDist(pair.get(1))));
        System.out.println("second checkpoint");
        for (List<Coord> pair : pairs) {
            System.out.println(pair.get(0) + " and " + pair.get(1));
        }
        for (int i = 0; i < pairs.size(); i++) {
            boolean added = false;
            List<Coord> pair = pairs.get(i);
            for (int j = 0; j < circuits.size(); j++) {
                if (circuits.get(j).containsAll(pair)) {
                    added = true;
                    break;
                } else if (circuits.get(j).contains(pair.get(0))) {
                    added = true;
                    circuits.get(j).add(pair.get(1));
                    break;
                } else if (circuits.get(j).contains(pair.get(1))) {
                    added = true;
                    circuits.get(j).add(pair.get(0));
                    break;
                } 
            }
            if (!added) circuits.add(new ArrayList<>(pair));
            System.out.println("done with " + i);
        }
        System.out.println(circuits.get(0).size() + "*" + circuits.get(1).size() + "*" + circuits.get(2).size());
        circuits.sort(Comparator.comparing(circuit -> -circuit.size()));
        for (List<Coord> circuit : circuits) {
            for (Coord coord : circuit) {
                System.out.print(coord + " ");
            }
            System.out.println();
        }
        return circuits.get(0).size() * circuits.get(1).size() * circuits.get(2).size();
    }

    @Override
    public long partTwo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'partTwo'");
    }
    
}

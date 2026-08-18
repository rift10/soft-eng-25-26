public class Day12 implements Day {

    @Override
    public boolean isPartTwo() {
        return false;
    }

    @Override
    public boolean isTesting() {
        return true;
    }

    public static void main(String[] args) {
        new Day12().run(args[0]);
    }

    @Override
    public void run(String day) {
        getLines(day);
        print();
    }

    @Override
    public long partOne() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'partOne'");
    }

    @Override
    public long partTwo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'partTwo'");
    }

}

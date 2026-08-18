import java.util.List;

public class Day7 implements Day {

    @Override
    public boolean isPartTwo() {
        return true;
    }

    @Override
    public boolean isTesting() {
        return true;
    }

    private List<String> lines;

    public static void main(String[] args) {
        new Day7().run(args[0]);
    }

    @Override
    public void run(String day) {
        lines = getLines(day).toList();
        print();
    }

    @Override
    public long partTwo() {
        long timelines = 1;
        boolean[][] fires = new boolean[2][lines.get(0).length()];
        System.out.println(lines.size());
        fires[0][lines.get(0).indexOf("S")] = true;
        for (int i = lines.size() - 2; i >= 0; i--) {
            System.out.println();
            for (int j = 0; j < fires[0].length; j++) {
                fires[1 - i % 2][j] = false;
            }
            for (int j = 0; j < fires[0].length; j++) {
                if (!fires[i % 2][j] && !fires[1 - i % 2][j])
                    continue;
                if (lines.get(i + 1).substring(j, j + 1).equals("^")) {
                    // System.out.println("^ at " + (i + 1)+ ", " + (j));
                    timelines ++;
                    fires[1 - i % 2][j - 1] = true;
                    fires[1 - i % 2][j] = false;
                    fires[1 - i % 2][j + 1] = true;
                    // System.out.println("setting " + (j-1) + " and " + (j+1));
                } else {
                    fires[1 - i % 2][j] = true;
                }
            }
            for (int j = 0; j < fires[0].length; j++) {
                System.out.print(fires[1 - i % 2][j] ? "|" : ".");
            }
        }
        return timelines;
    }

    @Override
    public long partOne() {
        long splits = 0;
        boolean[][] fires = new boolean[2][lines.get(0).length()];
        System.out.println(lines.size());
        fires[0][lines.get(0).indexOf("S")] = true;
        for (int i = 0; i < lines.size() - 1; i++) {
            System.out.println();
            for (int j = 0; j < fires[0].length; j++) {
                fires[1-i%2][j] = false;
            }
            for (int j = 0; j < fires[0].length; j++) {
                if (!fires[i%2][j] && !fires[1 - i % 2][j]) continue;
                if (lines.get(i+1).substring(j, j+1).equals("^")) {
                    // System.out.println("^ at " + (i + 1)+ ", " + (j));
                    splits++;
                    fires[1-i%2][j-1] = true;
                    fires[1-i%2][j] = false;
                    fires[1-i%2][j+1] = true;
                    // System.out.println("setting " + (j-1) + " and " + (j+1));
                } else {
                    fires[1-i%2][j] = true;
                }
            }
            for (int j = 0; j < fires[0].length; j++) {
                System.out.print(fires[1-i%2][j] ? "|" : ".");
            }
        }
        return splits;
    }
    
}

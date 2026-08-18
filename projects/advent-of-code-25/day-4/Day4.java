import java.util.ArrayList;
import java.util.List;

public class Day4 implements Day {

    private static List<String> rows;

    @Override
    public boolean isPartTwo() {
        return true;
    }

    @Override
    public boolean isTesting() {
        return false;
    }

    public static void main(String[] args) {
        new Day4().run(args[0]);
    }

    @Override
    public void run(String day) {
        rows = new ArrayList<>(getLines(day).toList());
        print();
    }

    @Override
    public long partOne() {
        return getSum();
    }

    @Override
    public long partTwo() {
        int total = 0;
        int add = getSum();
        total += add;
        while (add > 0) {
            // for (int i = 0; i < rows.size(); i++) {
            // System.out.println(rows.get(i));
            // }
            add = getSum();
            total += add;
        }
        return total;
    }

    private static int getSum() {
        int sum = 0;
        int rowLength = rows.get(0).length();
        List<String> newRows = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            String toAdd = rows.get(i);
            j: for (int j = 0; j < rowLength; j++) {
                if (!rows.get(i).substring(j, j + 1).equals("@")) continue;
                int paper = 0;
                for (int k = -1; k <= 1; k++) {
                    if ((i == 0 && k == -1) || ((i == rows.size() - 1) && k == 1)) continue;
                    for (int l = -1; l <= 1; l++) {
                        if ((j == 0 && l == -1) || (j == rowLength - 1 && l == 1)) continue;
                        if (k == 0 && l == 0) continue;
                        if (rows.get(i + k).substring(j + l, j + l + 1).equals("@")) {
                            paper++;
                        }
                        if (paper >= 4) {
                            // System.out.println("continuing " + i + " " + j + " paper: " + paper);
                            continue j;
                        }
                    }
                }
                // System.out.println("paper for " + i + " " + j + ": " + paper);
                if (paper < 4) {
                    toAdd = toAdd.substring(0, j) + "." + toAdd.substring(j + 1);
                    // System.out.println("replacing " + i + rows.get(i).substring(0, j) + "." + rows.get(i).substring(j + 1));
                    sum++;
                }
            }
            newRows.add(toAdd);
        }
        rows = newRows;
        // System.out.println("adding " + sum);
        return sum;
    }
}
import java.util.ArrayList;
import java.util.List;

public class Day2 implements Day {

    
    @Override
    public boolean isTesting() {
        return false;
    }
    
    @Override
    public boolean isPartTwo() {
        return true;
    }
    
    private static String[] ranges;
    
    public static void main(String[] args) {
        new Day2().run(args[0]);
    }
    
    @Override
    public void run(String day) {
        ranges = getLines(day).findFirst().get().split(",");
        print();
    }

    @Override
    public long partTwo() {
        long sum = 0;
        // iterating through the list of ranges
        for (int i = 0; i < ranges.length; i++) {
            String[] range = ranges[i].split("-");
            System.out.println(range[0] + "-" + range[1]);
            // iterating through numbers in a specific range
            j: for (long j = Long.parseLong(range[0]); j <= Long.parseLong(range[1]); j++) {
                String string = String.valueOf(j);
                // iterating through the possible lengths of repeated sections
                k: for (int k = 1; k <= string.length() / 2; k++) {
                    if (string.length() % k != 0) continue;
                    List<String> substrings = new ArrayList<>();
                    // iterating through the indexes of where the substrings will start
                    for (int l = 0; l < string.length(); l += k) {
                        substrings.add(string.substring(l, (l + k)));
                    }
                    for (String s : substrings) {
                        if (!s.equals(substrings.get(0))) continue k;
                    }
                    System.out.println("adding " + j);
                    sum += j;
                    continue j;
                }
            }
        }
        return sum;
    }

    @Override
    public long partOne() {
        long sum = 0;
        for (int i = 0; i < ranges.length; i++) {
            String[] range = ranges[i].split("-");
            System.out.println(range[0] + "-" + range[1]);
            for (long j = Long.parseLong(range[0]); j <= Long.parseLong(range[1]); j++) {
                String string = String.valueOf(j);
                if (string.length() % 2 == 1)
                    continue;
                if (string.substring(0, string.length() / 2).equals(string.substring(string.length() / 2))) {
                    System.out.println("adding " + j);
                    sum += j;
                }
            }
        }
        return sum;
    }
}
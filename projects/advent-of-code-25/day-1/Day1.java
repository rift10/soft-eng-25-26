public class Day1 implements Day {

    @Override
    public boolean isPartTwo() {
        return true;
    }

    @Override
    public boolean isTesting() {
        return false;
    }

    private static int dial = 50;
    private int[] nums;

    public static void main(String[] args) {
        new Day1().run(args[0]);
    }

    @Override
    public void run(String day) {
        nums = getLines(day).mapToInt(line -> Integer.parseInt(line.substring(1)) * (line.substring(0, 1).equals("L") ? -1 : 1)).toArray();
        // must be in range 5640 - 7209
        // 6513 incorrect
        // 6354 incorrect
        // 6318 incorrect
        // result for lochlan's data: 6638
        print();
    }

    @Override
    public long partTwo() {
        long result = 0;
        int previous = dial;
        for (int i : nums) {
            dial += i;
            // System.out.println("previous /: " + previous / 100 + " current /: " + dial /
            // 100);
            System.out.println(previous + " to " + dial);
            if ((((previous < 0 & dial > 0) || (previous > 0 & dial < 0)) && Math.abs(previous) < 100
                    && Math.abs(dial) < 100) || dial == 0 || Math.abs(dial) == 100) {
                System.out.println("adding 1");
                // System.out.println("previous /: " + previous / 100 + " current /: " + dial /
                // 100);
                result++;
            } else if ((dial / 101 != previous / 101)) {
                System.out.println("adding " + Math.abs(dial / 101 - previous / 101));
                System.out.println(dial / 101 + " - " + previous / 101);
                result += Math.abs(dial / 101 - previous / 101);
                if (((previous < 0 & dial > 0) || (previous > 0 & dial < 0)))
                    result++;
            }
            System.out.println();
            previous = dial;
        }
        return result;
    }

    @Override
    public long partOne() {
        long result = 0;
        System.out.println(dial);
        for (int i : nums) {
            dial += i;
            while (dial < 0) {
                dial += 100;
            }
            while (dial > 99) {
                dial -= 100;
            }
            if (dial == 0)
                result++;
            System.out.println(dial);
        }
        return result;
    }
}

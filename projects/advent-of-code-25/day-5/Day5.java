public class Day5 implements Day {
    
    @Override
    public boolean isPartTwo() {
        return true;
    }

    @Override
    public boolean isTesting() {
        return true;
    }

    private static String[] ranges;
    private static String[] numbers;
    private static long[][] fresh;

    public static void main(String[] args) {
        new Day5().run(args[0]);
    }
    
    @Override
    public void run(String day) {
        ranges = getLines(day).filter(line -> line.indexOf("-") != -1).toArray(String[]::new);
        numbers = getLines(day).filter(line -> line.indexOf("-") == -1 && line.length() > 0).toArray(String[]::new);
        fresh = new long[ranges.length][2];

        // System.out.println("total: " + partOne(fresh));
        // 319555905163730 too low
        // 357682539398346 too high
        System.out.println("total: " + partTwo());
    }

    @Override
    public long partTwo() {
        long sum = 0;
        for (int i = 0; i < ranges.length; i++) {
            String[] range = ranges[i].split("-");
            System.out.println(range[0] + "-" + range[1]);
            fresh[i] = new long[] { Long.parseLong(range[0]), Long.parseLong(range[1]) };
            long start = fresh[i][0];
            long end = fresh[i][1];
            for (int j = 0; j < i; j++) {
                if (fresh[j][0] < end && fresh[j][0] > start) {
                    System.out.println(fresh[j][0] + " < " + end);
                    end = fresh[j][0];
                    System.out.println("new end: " + end);
                }
                if (fresh[j][1] > start && fresh[j][1] < end) {
                    System.out.println(fresh[j][1] + " > " + start);
                    start = fresh[j][1];
                    System.out.println("new start: " + start);
                }
            }
            System.out.println("adding " + end + "-" + start);
            // if both are original bounds, add one
            // if both are new bounds, subtract one
            long add = (start == fresh[i][0] && end == fresh[i][1]) ? 1 : (start != fresh[i][0] && end != fresh[i][1]) ? -1 : 0;
            sum += end - start + add;
        }
        return sum;
    }

    @Override
    public long partOne() {
        int sum = 0;
        for (int i = 0; i < ranges.length; i++) {
            String[] range = ranges[i].split("-");
            // System.out.println(range[0] + "-" + range[1]);
            fresh[i] = new long[] { Long.parseLong(range[0]), Long.parseLong(range[1]) };
        }
        for (int i = 0; i < numbers.length; i++) {
            if (isFresh(fresh, Long.parseLong(numbers[i]))) {
            // System.out.println("adding");
            sum++;
            }
        }
        return sum;
    }

    private static boolean isFresh(long[][] fresh, long x) {
        for (int i = 0; i < fresh.length; i++) {
            // System.out.println(x + "? " + fresh[i][0] + " " + fresh[i][1]);
            if (x >= fresh[i][0] && x <= fresh[i][1]) return true; 
        }
        return false;
    }
}
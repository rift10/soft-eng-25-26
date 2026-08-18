import java.util.HashMap;
import java.util.List;

public class Day11 implements Day {

    @Override
    public boolean isPartTwo() {
        return true;
    }

    @Override
    public boolean isTesting() {
        return true;
    }

    private List<String[]> map;
    private HashMap<Integer, Long> hashmap = new HashMap<>();

    public static void main(String[] args) {
        new Day11().run(args[0]);
    }

    @Override
    public void run(String day) {
        map = getLines(day).map(line -> line.split(": ")).toList();
        print();
    }
    
    @Override
    public long partTwo() {
        long sum = 0;
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i)[0].equals("svr")) {
                sum += addTotal(i, map.get(i)[1], false, false);
            }
        }
        return sum;
    }

    private long addTotal(int index, String input, boolean hasFoundDac, boolean hasFoundFft) {
        if (hashmap.containsKey(index)) return hashmap.get(index);
        long sum = 0;
        String[] links = input.split(" ");
        boolean dac = hasFoundDac;
        boolean fft = hasFoundFft;
        for (String s : links) {
            if (s.equals("dac")) dac = true;
            if (s.equals("fft")) fft = true;
            
            if (s.equals("out") && hasFoundDac && hasFoundFft)
                sum++;
            else
                for (int i = 0; i < map.size(); i++) {
                    if (map.get(i)[0].equals(s)) {
                        System.out.println("looking at " + map.get(i)[0] + ": " + map.get(i)[1] + " from " + s);
                        long x = addTotal(i, map.get(i)[1], dac, fft);
                        System.out.println("at " + map.get(i)[0] + ": " + map.get(i)[1] + " putting " + x);
                        hashmap.put(i, x);
                        sum += x;
                    }
                }
        }
        return sum;
    }

    @Override
    public long partOne() {
        long sum = 0;
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i)[0].equals("you")) {
                sum += add(map.get(i)[1]);
            }
        }
        return sum;
    }

    private long add(String input) {
        long sum = 0;
        String[] links = input.split(" ");
        for (String s : links) {
            if (s.equals("out")) sum++;
            else for (int i = 0; i < map.size(); i++) {
                if (map.get(i)[0].equals(s)) {
                    sum += add(map.get(i)[1]);
                }
            }
        }
        return sum;
    }

}

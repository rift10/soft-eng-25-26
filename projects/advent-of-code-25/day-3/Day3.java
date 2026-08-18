import java.util.ArrayList;
import java.util.List;

public class Day3 implements Day {

    @Override
    public boolean isPartTwo() {
        return true;
    }

    @Override
    public boolean isTesting() {
        return false;
    }

    private static List<String> banks;
    private static List<List<Integer>> digits = new ArrayList<>();

    public static void main(String[] args) {
        new Day3().run(args[0]);
    }

    @Override
    public void run(String day) {
        banks = getLines(day).toList();
        for (String bank : banks) {
            digits.add(list(bank));
        }
        print();
    }

    @Override
    public long partTwo() {
        long sum = 0;
        for (int i = 0; i < digits.size(); i++) {
            long next = getNext(i);
            System.out.println(next);
            sum += next;
        }
        return sum;
    }
    
    private long getNext(int i) {
        long sum = 0;
        int startIndex = 0;
        for (int j = 1; j < 13; j++) {
            double add = (getStart(startIndex, i, j) * Math.pow(10, (12-j)));
            System.out.println(j + ": " + getStart(startIndex, i, j));
            sum += (long) add;
            startIndex = getStartIndex(startIndex, i, j);
        }
        return sum;
    }

    private int getStart(int startIndex, int i, int time) {
        int start = 0;
        for (int j = startIndex; j < digits.get(i).size() - (12-time); j++) {
            if (digits.get(i).get(j) > start) {
                start = digits.get(i).get(j);
            }
        }
        return start;
    }
    
    private int getStartIndex(int startIndex, int i, int time) {
        int start = 0;
        int index = 0;
        for (int j = startIndex; j < digits.get(i).size() - (12-time); j++) {
            if (digits.get(i).get(j) > start) {
                start = digits.get(i).get(j);
                index = j;
            }
        }
        return index + 1;
    }

    @Override
    public long partOne() {
        long sum = 0;
        for (int i = 0; i < digits.size(); i++) {
            sum += getNum(i);
        }
        return sum;
    }

    private static int getNum(int x) {
        int max = 0;
        for (int i = 0; i < digits.get(x).size(); i++) {
            for (int j = i+1; j < digits.get(x).size(); j++) {
                int add = Integer.valueOf(String.valueOf(digits.get(x).get(i)) + String.valueOf(digits.get(x).get(j)));
                if (add > max) max = add;
            }
        }
        System.out.println("adding " + max);
        return max;
    }

    private static List<Integer> list(String s) {
        List<Integer> result = new ArrayList<>();
        String[] ss = s.split("");
        for (String c : ss) {
            result.add(Integer.parseInt(c));
        }
        return result;
    }
}
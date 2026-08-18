import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day6 implements Day {
    @Override
    public boolean isPartTwo() {
        return false;
    }

    @Override
    public boolean isTesting() {
        return true;
    }

    private static List<List<String>> temp;
    private static List<String> tempSigns;
    private static List<List<String>> tokens;
    private static List<String> signs;

    public static void main(String[] args) {
        new Day6().run(args[0]);
    }

    @Override
    public void run(String day) {
        temp = getLines(day).map((String line) -> {
            Matcher matcher = Pattern.compile("\\d").matcher(line);
            if (matcher.find()) {
                return List.of(line.substring(matcher.start()).split("[^\\d\\n*+]+"));
            }
            return List.of("");
        }).toList();
        tempSigns = getLines(day).filter(line -> Pattern.compile("[*+]+").matcher(line).find()).toList();
        
        signs = new ArrayList<>(Arrays.asList(tempSigns.get(0).split("[^\\d\\n*+]+")));

        tokens = new ArrayList<>(temp);
        tokens.remove(tokens.size() - 1);
        print();
        // 50798374594 too low
    }

    @Override
    public long partOne() {
        long total = 0;
        // for (int i = 0; i < tokens.size(); i++) {
        //     for (int j = 0; j < tokens.get(i).size(); j++) {
        //         System.out.println("i: " + i + " j: " + j + ": " + tokens.get(i).get(j));
        //     }
        // }
        for (int i = 0; i < tokens.get(0).size(); i++) {
            // System.out.println("on column: " + tokens.get(0).get(i));
            if (signs.get(i).equals("+")) {
                int sum = 0;
                for (int j = 0; j < tokens.size(); j++) {
                    System.out.println("token: " + tokens.get(j).get(i));
                    sum += Integer.parseInt(tokens.get(j).get(i));
                }
                System.out.println("sum: " + sum);
                total += sum;
            } else if (signs.get(i).equals("*")) {
                int product = 1;
                // System.out.println("on column: " + tokens.get(0).get(i));
                for (int j = 0; j < tokens.size(); j++) {
                    System.out.println("token: " + tokens.get(j).get(i));
                    product *= Integer.parseInt(tokens.get(j).get(i));
                }
                System.out.println("product: " + product);
                total += product;
            }
        }
        return total;
    }

    @Override
    public long partTwo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'partTwo'");
    }
}
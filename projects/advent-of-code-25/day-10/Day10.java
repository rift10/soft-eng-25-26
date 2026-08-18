import java.util.ArrayList;
import java.util.List;

public class Day10 implements Day {

    @Override
    public boolean isPartTwo() {
        return false;
    }

    @Override
    public boolean isTesting() {
        return true;
    }

    private List<String> lights;
    private List<List<List<Integer>>> buttons;
    private List<String> joltages;


    public static void main(String[] args) {
        new Day10().run(args[0]);
    }

    @Override
    public void run(String day) {
        lights = getLines(day).toList();
        lights.forEach(line -> line = line.substring(0, line.indexOf("]")));
        buttons = getLines(day).map((String line) -> {
            List<List<Integer>> thing = new ArrayList<>();
            System.out.println(line);
            for (String paren : line.substring(line.indexOf("("), line.lastIndexOf(")") + 1).split(" ")) {
                List<Integer> thing2 = new ArrayList<>();
                String[] thing3 = paren.split(",");
                for (String s : thing3) {System.out.println(s);}
                System.out.println();
                if (thing3.length == 1) {
                    thing2.add(Integer.parseInt(thing3[thing3.length - 1].substring(1, thing3[thing3.length - 1].length() - 1)));
                } else {
                    thing2.add(Integer.parseInt(thing3[0].substring(1)));
                    for (int i = 1; i < thing3.length - 1; i++) {
                        System.out.println(thing3[i]);
                        thing2.add(Integer.parseInt(thing3[i]));
                    }
                    System.out.println("thing3: " + thing3[thing3.length - 1]);
                    thing2.add(Integer.parseInt(thing3[thing3.length - 1].substring(0, thing3[thing3.length - 1].length() - 1)));
                }
                System.out.println(thing2);
                thing.add(thing2);
            }
            return thing;
        }).toList();
        
        for (int i = 0; i < buttons.size(); i++) {
            for (int j = 0; j < buttons.get(i).size(); i++) {
                for (int k = 0; k < buttons.get(i).get(j).size(); k++) {
                    System.out.println(buttons.get(i).get(j).get(k));
                }
                System.out.println();
            }
        }
        joltages = getLines(day).toList();
        joltages.forEach(line -> line = line.substring(line.indexOf("{")));
        print();
    }

    @Override
    public long partOne() {
        long result = 0;
        for (int i = 0; i < lights.size(); i++) {

        }
        return result;
    }

    @Override
    public long partTwo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'partTwo'");
    }

}
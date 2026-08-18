import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface Day {
    default Stream<String> getLines(String day) {
        try {
            return Files.lines(Path.of("day-" + day + "/" + (isTesting() ? "test" : "input") + ".txt"));
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    default void print() {
        System.out.println("result: " + (isPartTwo() ? partTwo() : partOne()));
    }

    public boolean isPartTwo();
    public boolean isTesting();

    public void run(String day);

    public long partOne();
    public long partTwo();
}

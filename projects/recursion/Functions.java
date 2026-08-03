import java.util.HashMap;
import java.util.List;

public class Functions {

    private static HashMap<Change, Long> map = new HashMap<>();

    public static int factorial(int n) {
        if (n <= 1) return n;
        return n * factorial(n-1);
    }

    public static int fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n-1) + fibonacci(n - 2);
    }

    public static int iterativeFibonacci(int n) {
        int result = 0;
        int first = 0;
        int second = 1;
        for (int i = 1; i < n; i++) {
            result = first + second;
            first = second;
            second = result;
        }
        return result;
    }

    public static <T> List<T> dropFirst(List<T> list) {
        return list.subList(1, list.size());
    }

    public static long change(int amount, List<Integer> coins) {
        if (amount < 0 || coins.size() == 0) return 0;
        if (amount == 0) return 1;
        if (map.containsKey(new Change(amount, coins))) return map.get(new Change(amount, coins));
        long result = change(amount, dropFirst(coins)) + change(amount - coins.get(0), coins);
        map.put(new Change(amount, coins), Long.valueOf(result));
        return result;
    }
    
    public static void main(String[] args) {
        System.out.println(change(100, List.of(1, 5, 10, 25, 50))); // should return 292
    }

    public record Change(int amount, List<Integer> coins) {}
}
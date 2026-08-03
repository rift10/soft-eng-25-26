import java.io.*;
import java.lang.reflect.*;
import java.util.Arrays;

public class Repl {

    private final String PINK = "\u001B[38;5;212m";
    private final String RESET = "\u001B[0m";

    private final BufferedReader in;
    private final PrintStream out;
    private Class<?> clazz;
    private Method[] methods;
    private String output;

    public Repl(InputStream in, OutputStream out) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new PrintStream(out);
    }
    
    private String askClass() {
        String line = "";
        while (line.length() <= 0) {
            try {
                print("Provide fully qualified class name");
                line = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return line;
    }

    private String askMethod() {
        String line = "";
        print("Provide method name with arguments");
        try {
            line = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public Class<?> parseClass(String line) throws ClassNotFoundException {
        Class<?> clazz;
        clazz = Class.forName(line);
        print("Method options:");
        methods = clazz.getMethods();
        for (Method m : clazz.getMethods()) {
            print(" - " + m);
        }
        return clazz;
    }

    public <T> void parseMethod(Class<T> clazz, String method) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InstantiationException {
        String[] words = method.split("\s");
        Method meth = null;
        for (Method m : methods) {
            if (m.getName().equals(words[0])) {
                meth = m;
            }
        }
        if (meth == null) {
            print("No such method");
            return;
        }
        
        if (Modifier.isStatic(meth.getModifiers())) {
            output = meth.invoke(null, (Object[]) (Arrays.copyOfRange(words, 1, words.length))).toString();
        } else {
            output = meth.invoke(clazz.getDeclaredConstructor().newInstance(), (Object[]) (Arrays.copyOfRange(words, 1, words.length))).toString();
        }

    }

    // public Class<?>[] parseArgs(String[] args) throws ClassNotFoundException {
    //     Class<?>[] result = new Class<?>[args.length];
    //     for (int i = 0; i < args.length; i++) {
    //         result[i] = parseClass(args[i]);
    //     }
    //     return result;
    // }

    private void run() {
        String classText = "";
        while (true) {
            try {
                classText = askClass();
                clazz = parseClass(classText);
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("trying again");
            }
        }
        String methodText = "";
        while (true) {
            try {
                methodText = askMethod();
                parseMethod(clazz, methodText);
                break;
            } catch (ReflectiveOperationException | IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println("trying again");
            }
        }
        print("Output:");
        out.println(output);
    }

    public void print(String string) {
        out.println(PINK + string + RESET);
    }

    public static void main(String[] args) {
        Repl repl = new Repl(System.in, System.out);
        repl.run();
    }
}
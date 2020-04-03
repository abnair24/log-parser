import parser.CustomLogFileParser;

public class Main {

    public static void main(String[] args) {

        new CustomLogFileParser("bugreport.txt")
                .parse(4667, "WARNING", "OOM", "OutOfMemoryError");
    }
}

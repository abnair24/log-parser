package parser.processor;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FatalExceptionProcessor {

    private final Map<String, Integer> exceptionMap = new TreeMap<>();
    Map<String, List<String>> exceptionLinesMap = new TreeMap<>();
    Map<String, List<String>> stackTraceMap = new TreeMap<>();

    List<String> messageLines = new ArrayList<>();
    List<String> stackTraceLines = new ArrayList<>();
    String exceptionMessage = "";
    boolean status = false;
    int counter = 0;

    public void processFatalException(String currentLogMessageLine) {

        String regex = "\\bFATAL EXCEPTION\\b";

        Matcher exceptionMatcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(currentLogMessageLine);

        while (exceptionMatcher.find()) {
            String[] messages = currentLogMessageLine.split(":");
            exceptionMessage = messages[2].trim();
            status = true;
            insertInToMap(exceptionMessage);
        }

        if (counter < 3 && status) {
            if (currentLogMessageLine.contains("AndroidRuntime")) {
                messageLines.add(currentLogMessageLine);
                counter++;
                exceptionLinesMap.put(exceptionMessage, messageLines);
            }

        } else if (status && currentLogMessageLine.contains("AndroidRuntime")) {
            stackTraceLines.add(currentLogMessageLine.split("\\:\\s+")[1]);
            stackTraceMap.put(exceptionMessage, stackTraceLines);
        } else {
            status = false;
            counter = 0;
            stackTraceLines = new ArrayList<>();
            messageLines = new ArrayList<>();
        }

    }

    private void insertInToMap(String message) {
        if (!exceptionMap.containsKey(message)) {
            exceptionMap.put(message,1);
        } else {
            exceptionMap.put(message, exceptionMap.get(message) + 1);
        }
    }

    public void getResults() {
        System.out.println("FATAL EXCEPTION message :");
        exceptionMap.forEach((k, v) -> System.out.println(k + " : " + v));
        System.out.println("Exception MessageLines :");
        exceptionLinesMap.forEach((k, v) -> System.out.println(k + " : " + v));
        System.out.println("Stacktrace: ");
        stackTraceMap.forEach((k, v) -> System.out.println(k + " : " + v));
    }
}

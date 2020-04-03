package com.github.parser.processor;

import com.github.parser.regex.RegexHelper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FatalExceptionProcessor {

    private static final String EXCEPTION_STRING_SEARCH = "AndroidRuntime";

    private final Map<String, Integer> exceptionMap = new TreeMap<>();
    private Map<String, List<String>> exceptionLinesMap = new TreeMap<>();
    private Map<String, List<String>> stackTraceMap = new TreeMap<>();

    private List<String> messageLines = new ArrayList<>();
    private List<String> stackTraceLines = new ArrayList<>();
    private String exceptionMessage = "";
    private boolean status = false;
    private int counter = 0;

    public void processFatalException(String currentLogMessageLine) {

       Matcher exceptionMatcher = new RegexHelper().findFatalExceptionMatcher(currentLogMessageLine);

        while (exceptionMatcher.find()) {
            String[] messages = currentLogMessageLine.split(":");
            exceptionMessage = messages[2].trim();
            status = true;
            insertInToMap(exceptionMessage);
        }

        if (counter < 3 && status) {
            if (currentLogMessageLine.contains(EXCEPTION_STRING_SEARCH)) {
                messageLines.add(currentLogMessageLine);
                counter++;
                exceptionLinesMap.put(exceptionMessage, messageLines);
            }

        } else if (status && currentLogMessageLine.contains(EXCEPTION_STRING_SEARCH)) {
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

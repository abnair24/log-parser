package com.github.parser.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexHelper {

    private String logMessageLine = "";
    private String exceptionMessage = "";
    private int count = 0;

    public String extractLogMessage(long processId, String currentLine) {

        String pid = String.valueOf(processId);
        String dateRegex = "(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])\\s([01][0-9]|2[0-3]):([0-5][0-9]:[0-5][0-9]).([0-9][0-9][0-9])\\s+(" + pid + ")";
        String logLevelRegex = "\\d{1,5}\\s\\b\\w\\b";

        Matcher dateMatcher = Pattern.compile(dateRegex).matcher(currentLine);

        while (dateMatcher.find()) {
            String[] array = currentLine.split(logLevelRegex);
            logMessageLine = array[1];
        }
        return logMessageLine;
    }

    public int extractString(String stringToBeSearched, String currentLine) {

        String regex = "_" + stringToBeSearched + "|" + "\\b" + stringToBeSearched + "\\b";

        Matcher stringMatcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(currentLine);

        while (stringMatcher.find()) {
            count++;
        }
        return count;
    }

    public String extractFatalException(String exception, String currentLine) {
        String regex = "\\b" + exception + "\\b";

        Matcher exceptionMatcher = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(currentLine);

        while (exceptionMatcher.find()) {
            String[] messages = currentLine.split(":");
            exceptionMessage = messages[2].trim();
        }
        return exceptionMessage;
    }

    public Matcher findFatalExceptionMatcher(String currentLine) {

        String regex = "\\bFATAL EXCEPTION\\b";
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(currentLine);
    }
}

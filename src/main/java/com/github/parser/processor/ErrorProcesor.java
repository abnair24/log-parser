package com.github.parser.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ErrorProcesor {

    private Map<String, Integer> frequencyErrorMap = new HashMap<>();
    private static final String ERROR = "error";

    public void processForErrors(String currentLogMessage) {

        Pattern pattern = Pattern.compile("\\.|\\s|\\:|\\;");

        if (currentLogMessage.toLowerCase().contains(ERROR)) {
            for (String word : pattern.split(currentLogMessage)) {
                insertIntoMap(word);
            }
        }
    }

    private void insertIntoMap(String word) {
        if (word.toLowerCase().contains(ERROR)) {
            if (!frequencyErrorMap.containsKey(word)) {
                frequencyErrorMap.put(word, 1);
            } else {
                frequencyErrorMap.put(word, frequencyErrorMap.get(word) + 1);
            }
        }
    }

    public void processResults() {
        System.out.println("=========================================== ErrorProcessor =========================================");
        frequencyErrorMap.forEach((k,v) -> System.out.println(k + "  : " +v));
    }

}

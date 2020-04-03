package com.github.parser.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ErrorProcesor {


    private final Map<String, Integer> frequencyMap = new HashMap<>();
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
            if (!frequencyMap.containsKey(word)) {
                frequencyMap.put(word, 1);
            } else {
                frequencyMap.put(word, frequencyMap.get(word) + 1);
            }
        }
    }

    public void processResults() {
        System.out.println("ErrorProcessor :");
        for(String key :frequencyMap.keySet())
        {
            System.out.println(key + " : "+ frequencyMap.get(key));
        }
    }

}

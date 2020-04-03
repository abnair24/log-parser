package parser.processor;

import regex.RegexHelper;

import java.util.Map;
import java.util.TreeMap;

public class StringSearchProcessor {

    private final Map<String, Integer> frequencyMap = new TreeMap<>();

    public void processStringSearch(String currentLogMessage, String[] stringsToBeSearched) {

        for (String eachString : stringsToBeSearched) {

            int count = new RegexHelper().extractString(eachString, currentLogMessage);
            if (count > 0) {
                if (!frequencyMap.containsKey(eachString + "#" + currentLogMessage)) {
                    frequencyMap.put(eachString + "#" + currentLogMessage, count);
                } else {
                    frequencyMap.put(eachString + "#" + currentLogMessage, frequencyMap.get(eachString + "#" + currentLogMessage) + count);
                }
            }
        }
    }

    public void getResults() {
        System.out.println("StringSearchProcessor :");
        frequencyMap.forEach((k,v) -> System.out.println(k + ":" + v));
    }
}


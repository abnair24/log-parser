package parser.processor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ErrorProcesor {

    private final Map<String, Integer> frequencyMap = new HashMap<>();

    public void processForErrors(String currentLogMessage) {
        String errorString = "error";

        Pattern pattern = Pattern.compile("\\.|\\s|\\:|\\;");

        if (currentLogMessage.toLowerCase().contains(errorString.toLowerCase())) {
            for (String word : pattern.split(currentLogMessage)) {
                insertIntoMap(word);
            }
        }
    }

    private void insertIntoMap(String word) {
        if (word.toLowerCase().contains("error")) {
            if (!frequencyMap.containsKey(word)) {
                frequencyMap.put(word, 1);
            } else {
                frequencyMap.put(word, frequencyMap.get(word) + 1);
            }
        }
    }

    public void getResults() {
        System.out.println("ErrorProcessor :");
        frequencyMap.forEach((k, v) -> System.out.println(k + ":" + v));
    }

}

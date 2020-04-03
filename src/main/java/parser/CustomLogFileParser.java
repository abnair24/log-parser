package parser;

import parser.processor.ErrorProcesor;
import parser.processor.FatalExceptionProcessor;
import parser.processor.StringSearchProcessor;
import regex.RegexHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CustomLogFileParser {

    private String fileName;

    public CustomLogFileParser(String fileName) {
        this.fileName = fileName;
    }

    public void parse(long processId, String... stringsToBeSearched) {

        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        String currentLine;

        RegexHelper regexHelper = new RegexHelper();
        ErrorProcesor errorProcesor = new ErrorProcesor();
        StringSearchProcessor stringSearchProcessor = new StringSearchProcessor();
        FatalExceptionProcessor fatalExceptionProcessor = new FatalExceptionProcessor();

        File file = new File(ClassLoader.getSystemClassLoader().getResource(this.fileName).getFile());

        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            boolean status = false;
            int counter = 0;
            Map<String, Integer> exceptionMap = new TreeMap<>();
            Map<String, List<String>> exceptionLinesMap = new TreeMap<>();
            Map<String, List<String>> stackTraceMap = new TreeMap<>();
            List<String> messageLines = new ArrayList<>();
            List<String> stackTraceLines = new ArrayList<>();
            String exceptionMessage = "";

            while ((currentLine = bufferedReader.readLine()) != null) {
                //contains fatal error ? flag=true
                //if count of lines post fatal error <3 || has the strack trace pattern
                //add to two freq maps 1) fatal error exception and count
                //2) fatal error exception and list<string> which is a stack trace
                //when it does not find the stack trace pattern flip the flag to false
                //if you complete the above then move that to a class


                String logMessage = regexHelper.extractLogMessage(processId, currentLine);

                errorProcesor.processForErrors(logMessage);
                stringSearchProcessor.processStringSearch(logMessage, stringsToBeSearched);
                fatalExceptionProcessor.processFatalException(logMessage);
            }

            errorProcesor.getResults();

            stringSearchProcessor.getResults();

            fatalExceptionProcessor.getResults();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

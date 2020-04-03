package com.github.parser;

import com.github.parser.processor.ErrorProcesor;
import com.github.parser.processor.FatalExceptionProcessor;
import com.github.parser.processor.StringSearchProcessor;
import com.github.parser.regex.RegexHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


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
            String logMessage = "";

            while ((currentLine = bufferedReader.readLine()) != null) {
                logMessage = regexHelper.extractLogMessage(processId, currentLine);
                errorProcesor.processForErrors(logMessage);
                stringSearchProcessor.processStringSearch(logMessage, stringsToBeSearched);
                fatalExceptionProcessor.processFatalException(logMessage);
            }

            errorProcesor.processResults();
            stringSearchProcessor.processResults();
            fatalExceptionProcessor.processResults();

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
               // ex.printStackTrace();
            }
        }
    }
}

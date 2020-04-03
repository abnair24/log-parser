package com.github.parser;

public class LogParser {

    public static void main(String[] args) {

        new CustomLogFileParser("BugReport.txt")
                .parse(4667, "WARNING", "OOM", "OutOfMemoryError");
    }
}

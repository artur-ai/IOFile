package com.maiboroda.fileAnalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileAnalyzer {
    public Result analyze(String path, String word) {
        validatePath(path);
        validateWord(word);
        String text = readPath(path);
        List<String> sentences = splitToSentences(text);
        List<String> serchedSentences = searchWordInSentences(sentences, word);
        int count = countWord(serchedSentences, word);
        return new Result(word, serchedSentences, count);
    }

    private void validateWord(String word) {
        if (word == null || word.length() == 0) {
            throw new IllegalArgumentException("Word cannot be null or empty");
        }
    }

    private void validatePath(String path) {
        if (path == null || path.length() == 0) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }
    }

    String readPath(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException exception) {
            System.out.println("No such file");
            exception.printStackTrace();
            return "";
        }
    }

    protected List<String> splitToSentences(String text) {
        String[] sentences = text.split("[!?.]");
        List<String> list = new ArrayList<>(List.of(sentences));
        return list;
    }

    protected List<String> searchWordInSentences(List<String> sentences, String word) {
        List<String> result = new ArrayList<>();
        String lowerCaseWord = word.toLowerCase();
        for (String sentence : sentences) {
            String[] words = sentence.toLowerCase().split("\\W+");
            for (String searchWord : words) {
                if (searchWord.equals(lowerCaseWord)) {
                    result.add(sentence.trim() + ".");
                }
            }

        }
        return result;
    }

    protected int countWord(List<String> searchedSentences, String word) {
        int count = 0;
        String lowerCaseWord = word.toLowerCase();
        for (String searchedSentence : searchedSentences) {
            String[] words = searchedSentence.toLowerCase().split("\\W+");
            for (String searchedWord : words) {
                if (searchedWord.equals(lowerCaseWord)) {
                    count++;
                }
            }
        }
        return count;
    }
}

package com.maiboroda;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileAnalyzerTest {

    FileAnalyzer fileAnalyzer = new FileAnalyzer();
    private final String path = "test/resources/text.txt";

    @Test
    void testValidateWordWithEmptyWord() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fileAnalyzer.analyze("src/", "");
        });
        assertEquals("Word cannot be null or empty", exception.getMessage());
    }

    @Test
    void testValidateWordWithNullWord() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fileAnalyzer.analyze("src/", null);
        });
        assertEquals("Word cannot be null or empty", exception.getMessage());
    }

    @Test
    void testValidatePathEmptyPath() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fileAnalyzer.analyze("", "java");
        });
        assertEquals("Path cannot be null or empty", exception.getMessage());
    }

    @Test
    void testValidatePathWithNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fileAnalyzer.analyze(null, "java");
        });
        assertEquals("Path cannot be null or empty", exception.getMessage());
    }

    @Test
    void testValidateWordAndPathWithTwoNormalArguments() {
        assertDoesNotThrow(() -> {
            fileAnalyzer.analyze(path, "java");
        });
    }

    @Test
    void testReadPathWhenFileExist() throws IOException {
        Path tempFile = Files.createTempFile("test-file", ".txt");
        Files.writeString(tempFile, "Hello Word");

        String result = fileAnalyzer.readPath(tempFile.toString());

        assertEquals("Hello Word", result);
        Files.delete(tempFile);
    }

    @Test
    void testReadPathWhenFileDoesNotExist() {
        String result = fileAnalyzer.readPath("NonExistFile.txt");
        assertEquals("", result);
    }

    @Test
    void testSplitToSentencesWithNormalText() {
        String text = "Hello. I love Java";
        List<String> result = fileAnalyzer.splitToSentences(text);

        assertEquals(2, result.size());
        assertEquals("Hello", result.get(0).trim());
        assertEquals("I love Java", result.get(1).trim());
    }

    @Test
    void testSplitToTextWithEmptyText() {
        List<String> result = fileAnalyzer.splitToSentences("");
        assertEquals(1, result.size());
        assertEquals("", result.get(0));
    }

    @Test
    void testSplitToTextWithNoDistribution() {
        String text = "Hello Word";
        List<String> result = fileAnalyzer.splitToSentences(text);
        assertEquals(1, result.size());
        assertEquals("Hello Word", result.get(0));
    }

    @Test
    void testSearchWordInSentencesWithNormalWords() {
        List<String> sentences = List.of(
                "Hello everyone",
                "I love java",
                "Java is the best language"
        );

        List<String> result = fileAnalyzer.searchWordInSentences(sentences, "java");
        assertEquals(2, result.size());
        assertTrue(result.contains("I love java."));
        assertTrue(result.contains("Java is the best language."));
    }

    @Test
    void testSearchWordInSentencesWithNoSearchingWord(){
        List<String> sentences = List.of(
                "Hello everyone",
                "I love java",
                "Java is the best language"
        );
        List<String> result = fileAnalyzer.searchWordInSentences(sentences, "car");
        assertTrue(result.isEmpty());
    }

    @Test
    void testSearchWordInSentencesWithThreeSameWord(){
        List<String> sentences = List.of(
                "Hello everyone",
                "I love JAVA",
                "Java is the best language",
                "JAvA!",
                "Study jaVA"
        );

        List<String> result = fileAnalyzer.searchWordInSentences(sentences, "java");
        assertEquals(4, result.size());

        assertTrue(result.contains("I love JAVA."));
        assertTrue(result.contains("Java is the best language."));
        assertTrue(result.contains("JAvA!."));
        assertTrue(result.contains("Study jaVA."));
    }

    @Test
    void testCountWordWithNormalText(){
        List<String> sentence = List.of(
                "Hello everyone",
                "I love JAVA",
                "Java is the best language",
                "JAvA!",
                "Study jaVA"
        );
        int count = fileAnalyzer.countWord(sentence, "java");
        assertEquals(4, count);
    }

    @Test
    void testCountWordWithNoSearchingWord(){
        List<String> sentence = List.of(
                "Hello everyone",
                "I love JAVA",
                "Java is the best language",
                "JAvA!",
                "Study jaVA"
        );
        int count = fileAnalyzer.countWord(sentence, "car");
        assertEquals(0, count);
    }

    @Test
    void testCountWordWithSameWords(){
        List<String> sentence = List.of(
                "java",
                "JAVA",
                "jAvA",
                "JaVa"
        );
        int count = fileAnalyzer.countWord(sentence, "java");
        assertEquals(4, count);
    }
}

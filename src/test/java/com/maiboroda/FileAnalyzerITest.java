package com.maiboroda;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FileAnalyzerITest {
    FileAnalyzer fileAnalyzer = new FileAnalyzer();
    private final String path = "src/test/resources/text.txt";

    @Test
    void testReadFile() {
        assertFalse(text.isEmpty());
        assertTrue(text.toLowerCase().contains("java"));
    }

    @Test
    void testSplitToSentences() {
        assertEquals(10, sentences.size());
    }

    @Test
    void testSearchWordInSentences() {
        List<String> result = fileAnalyzer.searchWordInSentences(sentences, "java");

        assertFalse(result.isEmpty());
        assertEquals(10, result.size());
    }

    @Test
    void testCountWord() {
        int count = fileAnalyzer.countWord(sentences, "java");

        assertEquals(10, count);
    }

    private String text = fileAnalyzer.readPath(path);
    private List<String> sentences = fileAnalyzer.splitToSentences(text);
}

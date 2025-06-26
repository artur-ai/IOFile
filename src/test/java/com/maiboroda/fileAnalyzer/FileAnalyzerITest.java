package com.maiboroda.fileAnalyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FileAnalyzerITest {
    FileAnalyzer fileAnalyzer;
    private final String PATH = "src/test/resources/text.txt";

    @BeforeEach
    void setUp(){
        fileAnalyzer = new FileAnalyzer();
    }

    @Test
    void testReadFile() {
        String text = fileAnalyzer.readPath(PATH);

        assertFalse(text.isEmpty());
        assertTrue(text.toLowerCase().contains("java"));
    }

    @Test
    void testSplitToSentences() {
        String text = fileAnalyzer.readPath(PATH);
        List<String> sentences = fileAnalyzer.splitToSentences(text);

        assertEquals(10, sentences.size());
    }

    @Test
    void testSearchWordInSentences() {
        String text = fileAnalyzer.readPath(PATH);
        List<String> sentences = fileAnalyzer.splitToSentences(text);
        List<String> result = fileAnalyzer.searchWordInSentences(sentences, "java");

        assertFalse(result.isEmpty());
        assertEquals(10, result.size());
    }

    @Test
    void testCountWord() {
        String text = fileAnalyzer.readPath(PATH);
        List<String> sentences = fileAnalyzer.splitToSentences(text);
        int count = fileAnalyzer.countWord(sentences, "java");

        assertEquals(10, count);
    }
}

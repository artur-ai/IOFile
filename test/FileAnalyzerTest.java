import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileAnalyzerTest {
    private final String text = "Hello. I love java!!! Java is the fantastic language. I study java. Is java the best language? Of course yes. JAVA!!!!!!";

    @Test
    void testOfSearchingWordsInText() {
        int count = FileAnalyzer.searchingWords(text, "java");
        assertEquals(5, count);
    }

    @Test
    void testOfSearchingSentencesWithWords(){
        ArrayList<String> sentences = FileAnalyzer.findSentencesWithWord(text,"java");
        assertTrue(sentences.get(0).toLowerCase().contains("java"));
        assertEquals(5, sentences.size());
    }

}

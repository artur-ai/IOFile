package com.maiboroda;
import java.util.List;

public class Result {
    private String word;
    private List<String> sentences;
    private int count;

    public String getWord() {
        return word;
    }

    public List<String> getSentences() {
        return sentences;
    }

    public int getCount() {
        return count;
    }

    public Result(String word, List<String> sentences, int count) {
        this.word = word;
        this.sentences = sentences;
        this.count = count;
    }
}

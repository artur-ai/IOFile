package com.maiboroda.fileAnalyzer;

public class StartForUser {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Arguments must be two!");
        }

        String path = args[0];
        String word = args[1];
        FileAnalyzer fileAnalyzer = new FileAnalyzer();


        try {
            Result result = fileAnalyzer.analyze(path, word);
            System.out.println("Word: " + result.getWord());
            System.out.println("Count in file: " + result.getCount());
            System.out.println("Sentances with word " + result.getWord() + ": ");
            for (String sentence : result.getSentences()){
                System.out.println("-" + sentence);
            }
        } catch (IllegalArgumentException exception){
            exception.printStackTrace();
        }
    }


}

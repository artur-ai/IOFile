import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class FileAnalyzer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the path to the file");
        String filePath = scanner.nextLine();

        System.out.println("Enter the word for searching");
        String searchWord = scanner.nextLine().toLowerCase();

        StringBuilder content = new StringBuilder();
        try {
            Scanner fileScanner = new Scanner(new File(filePath));
            while(fileScanner.hasNextLine()) {
                content.append(fileScanner.nextLine()).append(" ");
            }
        } catch (FileNotFoundException exception){
            System.out.println("File not found");
            exception.printStackTrace();
        }

        String text = content.toString();
        int counter = 0;

        String[] words = text.toLowerCase().split("\\W+");
        for (String word : words) {
            if (word.equals(searchWord)) {
                counter++;
            }
        }

        System.out.println("Number of words found: " + searchWord + ": " + counter);

        System.out.println("Sentences with this word: ");
        String[] sentences = text.split("[.?!]");
        for (String sentence : sentences) {
            if (sentence.toLowerCase().contains(searchWord)) {
                System.out.println(sentence.trim() + "." );
            }
        }
    }

    public static int searchingWords(String text, String searchWord) {
        String[] words = text.toLowerCase().split("\\W+");
        int count = 0;
        for (String word : words) {
            if (word.equals(searchWord.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    public static ArrayList<String> findSentencesWithWord(String text, String searchWord) {
        ArrayList<String> result = new ArrayList<>();
        String[] sentences = text.split("[.!?]");
        for (String sentence : sentences) {
            String[] words = sentence.toLowerCase().split("\\W+");
            for (String word : words) {
                if (word.equals(searchWord.toLowerCase())) {
                    result.add(sentence.trim() + ".");
                }
            }
        }
        return result;
    }

}
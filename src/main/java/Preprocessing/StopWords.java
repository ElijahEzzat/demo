package Preprocessing;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class StopWords {
    public StopWords(String filepath) throws Exception{
        // Define stop words list
        String[] stopWords = {"the", "a", "an", "and", "or", "of", "to"};

        // Iterate through each document in dataset folder
        File datasetFolder = new File(filepath);
        for (File file : datasetFolder.listFiles()) {
            // Read document contents into string
            String contents = new String(Files.readAllBytes(file.toPath()));

            // Tokenize the document
            StringTokenizer tokenizer = new StringTokenizer(contents);
            List<String> validTokens = new ArrayList<String>();
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().toLowerCase();
                // Remove stop words
                if (!Arrays.asList(stopWords).contains(token)) {
                    validTokens.add(token);
                }
            }

            // Write cleaned document to new file
            String folderPath = "./Preprocessing/StopWord_files/";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String filePath = folderPath + "cleaned_" + file.getName();
            FileWriter writer = new FileWriter(filePath);
            for (String token : validTokens) {
                writer.write(token + " ");
            }
            writer.close();
        }
    }
    public static void main(String[] args) throws IOException {
        // Define stop words list
        String[] stopWords = {"the", "a", "an", "and", "or", "of", "to"};

        // Iterate through each document in dataset folder
        File datasetFolder = new File("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
        for (File file : datasetFolder.listFiles()) {
            // Read document contents into string
            String contents = new String(Files.readAllBytes(file.toPath()));

            // Tokenize the document
            StringTokenizer tokenizer = new StringTokenizer(contents);
            List<String> validTokens = new ArrayList<String>();
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().toLowerCase();
                // Remove stop words
                if (!Arrays.asList(stopWords).contains(token)) {
                    validTokens.add(token);
                }
            }

            // Write cleaned document to new file
            String folderPath = "./cleaned_files/";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String filePath = folderPath + "cleaned_" + file.getName();
            FileWriter writer = new FileWriter(filePath);
            for (String token : validTokens) {
                writer.write(token + " ");
            }
            writer.close();
        }
    }
}
package Preprocessing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Tokenization {

    public Tokenization(String filepath) throws IOException {
        List<File> files = loadTextFiles(filepath);

        // Tokenize the text files
        List<List<String>> tokenizedTextFiles = tokenizeTextFiles(files);

        // Print the tokenized text files
        String folderPath = "./Preprocessing/tokenized_files/";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = folderPath + "tokenized_text.txt";
        FileWriter writer = new FileWriter(filePath);
        for (List<String> token : tokenizedTextFiles) {
            writer.write(token + " ");
        }
        writer.close();
    }
    public static void main(String[] args) throws IOException {
        // Load the text files
        List<File> files = loadTextFiles(args[0]);

        // Tokenize the text files
        List<List<String>> tokenizedTextFiles = tokenizeTextFiles(files);

        // Print the tokenized text files
        String folderPath = "./Preprocessing/tokenized_files/";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = folderPath + "tokenized_text.txt";
        FileWriter writer = new FileWriter(filePath);
        for (List<String> token : tokenizedTextFiles) {
            writer.write(token + " ");
        }
        writer.close();
    }

    // Load a list of text files from a folder
    public static List<File> loadTextFiles(String folderPath) {
        List<File> files = new ArrayList<>();
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                files.add(file);
            }
        }
        return files;
    }

    // Tokenize a list of text files
    public static List<List<String>> tokenizeTextFiles(List<File> files) throws IOException {
        List<List<String>> tokenizedTextFiles = new ArrayList<>();
        for (File file : files) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            List<String> tokenList = new ArrayList<>();
            while (line != null) {
                String[] tokens = line.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");
                for (String token : tokens) {
                    if (!token.isEmpty()) {
                        tokenList.add(token);
                    }
                }
                line = reader.readLine();
            }
            reader.close();
            tokenizedTextFiles.add(tokenList);
        }
        return tokenizedTextFiles;

    }
    }
package com.example.demo.BiwordIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiwordIndex {

    public static void main(String[] args) throws IOException {
        // Load the documents
        List<String> documents = loadDocuments("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");

        // Create the biword index
        Map<String, List<String>> biwordIndex = createBiwordIndex(documents);

        // Print the results
        for (String biword : biwordIndex.keySet()) {
            List<String> postings = biwordIndex.get(biword);
            System.out.println(biword + " = " + postings);
        }
    }

    // Load a list of documents from a folder
    public static List<String> loadDocuments(String folderPath) throws IOException {
        List<String> documents = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    sb.append(line);
                    line = reader.readLine();
                }
                documents.add(sb.toString());
                reader.close();
            }
        }
        return documents;
    }

    // Create a biword index from a list of documents
    public static Map<String, List<String>> createBiwordIndex(List<String> documents) {
        Map<String, List<String>> biwordIndex = new HashMap<>();
        for (int i = 0; i < documents.size(); i++) {
            String[] tokens = tokenize(documents.get(i));
            for (int j = 0; j < tokens.length - 1; j++) {
                String biword = tokens[j] + " " + tokens[j+1];
                if (!biwordIndex.containsKey(biword)) {
                    biwordIndex.put(biword, new ArrayList<>());
                }
                List<String> postings = biwordIndex.get(biword);
                String docName = "doc" + i;
                if (!postings.contains(docName)) {
                    postings.add(docName);
                }
            }
        }
        return biwordIndex;
    }

    // Tokenize a document
    public static String[] tokenize(String document) {
        return document.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");
    }

}
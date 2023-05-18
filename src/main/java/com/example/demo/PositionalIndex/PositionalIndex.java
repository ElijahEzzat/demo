package com.example.demo.PositionalIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class PositionalIndex {

    public static void main(String[] args) throws IOException {
        // Load the documents
        List<String> documents = loadDocuments("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\Preprocessing\\normalized");

        // Create the positional index
        Map<String, Map<String, List<Integer>>> positionalIndex = createPositionalIndex(documents);

        // Print the results
        List<String> sortedTerms = new ArrayList<>(positionalIndex.keySet());
        Collections.sort(sortedTerms);
        for (String term : sortedTerms) {
            Map<String, List<Integer>> postings = positionalIndex.get(term);
            int numDocs = postings.size();
            System.out.print("<"+term + ":" + numDocs + ";           ");
            for (String doc : postings.keySet()) {
                System.out.print(doc + ": ");
                List<Integer> positions = postings.get(doc);
                for (int pos : positions) {
                    System.out.print(pos + ",");
                }
                System.out.print(" ");
            }
            System.out.println(">");

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

    // Create a positional index from a list of documents
    public static Map<String, Map<String, List<Integer>>> createPositionalIndex(List<String> documents) {
        Map<String, Map<String, List<Integer>>> positionalIndex = new HashMap<>();
        for (int i = 0; i < documents.size(); i++) {
            String[] tokens = tokenize(documents.get(i));
            for (int j = 0; j < tokens.length; j++) {
                if (!positionalIndex.containsKey(tokens[j])) {
                    positionalIndex.put(tokens[j], new HashMap<>());
                }
                Map<String, List<Integer>> postings = positionalIndex.get(tokens[j]);
                String docName = "doc" + i;
                if (!postings.containsKey(docName)) {
                    postings.put(docName, new ArrayList<>());
                }
                List<Integer> positions = postings.get(docName);
                positions.add(j);
            }
        }
        return positionalIndex;
    }

    // Tokenize a document
    public static String[] tokenize(String document) {
        return document.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");
    }

}
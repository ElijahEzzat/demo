package com.example.demo.Invertedindex;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;


public class PostingList {

    public static void main(String[] args) throws IOException {

        // Load the documents
        List<String> documents = new ArrayList<>();
        File folder = new File("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                documents.add(loadDocument(file.getAbsolutePath()));
            }
        }

        // Create the inverted index
        Map<String, List<Integer>> invertedIndex = createInvertedIndex(documents);

        // Print the posting list of each term
        List<String> sortedTerms = new ArrayList<>(invertedIndex.keySet());
        Collections.sort(sortedTerms);
        for (String term : sortedTerms) {
            System.out.println(term + ": " + invertedIndex.get(term));
        }
    }

    private static String loadDocument(String filename) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        while (line != null) {
            sb.append(line).append("\n");
            line = reader.readLine();
        }
        reader.close();
        return sb.toString();
    }

    private static Map<String, List<Integer>> createInvertedIndex(List<String> documents) {
        Map<String, List<Integer>> invertedIndex = new HashMap<>();
        for (int i = 0; i < documents.size(); i++) {
            String[] tokens = tokenize(documents.get(i));
            for (String token : tokens) {
                if (!invertedIndex.containsKey(token)) {
                    invertedIndex.put(token, new ArrayList<>());
                }
                if (!invertedIndex.get(token).contains(i)) {
                    invertedIndex.get(token).add(i);
                }
            }
        }
        return invertedIndex;
    }

    private static String[] tokenize(String document) {
        return document.toLowerCase().split("[^a-zA-Z0-9]+");
    }

}

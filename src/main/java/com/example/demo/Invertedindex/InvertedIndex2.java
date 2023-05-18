package com.example.demo.Invertedindex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InvertedIndex2 {

    // Create a InvertedIndex from a list of documents
    public static Map<String, Map<String,Integer>> createInvertedIndex(List<String> documents) {
        // all stop words
        Set<String> stopWords = new HashSet<>(Arrays.asList("a", "an", "the", "and", "but", "or", "for", "nor", "on", "at", "to", "from", "by", "with", "in", "out", "of"));
        Map<String, Map<String,Integer>> invertedIndex = new HashMap<>();
        for (int i = 0; i < documents.size(); i++) {
            String[] tokens = documents.get(i).toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");
            for (int j = 0; j < tokens.length; j++) {
                String normalizedToken = tokens[j].trim();
                if (!normalizedToken.isEmpty() && !stopWords.contains(normalizedToken)) {
                    if (!invertedIndex.containsKey(normalizedToken)) {
                        invertedIndex.put(normalizedToken, new HashMap<>());
                    }
                    invertedIndex.get(normalizedToken).put("doc" + i, 0);
                }
            }
        }
        return invertedIndex;
    }

    // Load a list of documents from a folder
    public static List<String> loadDocuments(String folderPath) throws IOException {
        List<String> documents = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (line != null) {
                    documents.add(line);
                    line = reader.readLine();
                }
                reader.close();
            }
        }
        return documents;
    }

    public static void main(String[] args) throws IOException {

        // Load the documents
        List<String> documents = new ArrayList<>();
        documents.addAll(loadDocuments("C:\\Users\\eezt3\\Downloads\\demo\\Preprocessing\\lemmatized_files"));

        // Create the inverted index
        Map<String, Map<String,Integer>> invertedIndex = createInvertedIndex(documents);

        String query = args[0].toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");

        // all stop words
        Set<String> stopWords = new HashSet<>(Arrays.asList("a", "an", "the", "and", "but", "or", "for", "nor", "on", "at", "to", "from", "by", "with", "in", "out", "of"));
        String[] queryTokens = tokenize(query);
        List<String> relevantDocuments = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            boolean relevant = evaluateBooleanQuery(removeStopWords(queryTokens, stopWords), invertedIndex, i);
            if (relevant) {
                relevantDocuments.add("doc" + i);
            }
        }
        System.out.println("Relevant documents: " + relevantDocuments);
    }

    private static String[] removeStopWords(String[] queryTokens, Set<String> stopWords) {
        List<String> tokens = new ArrayList<>();
        for (String token : queryTokens) {
            if (!stopWords.contains(token)) {
                tokens.add(token);
            }
        }
        return tokens.toArray(new String[0]);
    }
    // Evaluate a boolean query for a given document index
    public static boolean evaluateBooleanQuery(String[] queryTokens, Map<String, Map<String, Integer>> invertedIndex, int docIndex) {
        boolean result = false;
        boolean op = false;
        for (String token : queryTokens) {
            if (token.equals("and")) {
                op = true;
            } else if (token.equals("or")) {
                op = false;
            } else {
                boolean termPresent = invertedIndex.containsKey(token) && invertedIndex.get(token).containsKey("doc" + docIndex);
                if (op) {
                    result = result && termPresent;
                } else {
                    result = result || termPresent;
                }
            }
        }
        return result;
    }

    public static String[] tokenize(String input) {
        return input.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");
    }
}

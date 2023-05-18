package com.example.demo.Invertedindex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class InvertedIndex {

    public static void main(String[] args) throws IOException {

        // Load the documents
        List<String> documents = new ArrayList<>();
        documents.addAll(loadDocuments("C:\\Users\\eezt3\\Downloads\\demo\\Preprocessing\\lemmatized_files"));

        // Create the inverted index
        Map<String, Map<String,Integer>> invertedIndex = createInvertedIndex(documents);

        // all stop words
        Set<String> stopWords = new HashSet<>(Arrays.asList("a", "an", "the", "and", "but", "or", "for", "nor", "on", "at", "to", "from", "by", "with", "in", "out", "of"));
        String query = args[0].toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
        String[] queryTokens = lemmatizeTokens(query.split("\\s+"));
        List<String> relevantDocuments = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            boolean relevant = evaluateBooleanQuery(removeStopWords(queryTokens, stopWords), invertedIndex, i);
            if (relevant) {
                relevantDocuments.add("doc" + i);
            }
        }
        System.out.println("Relevant documents: " + relevantDocuments);
    }

    // Create a InvertedIndex from a list of documents
    public static Map<String, Map<String,Integer>> createInvertedIndex(List<String> documents) {
        // all stop words
        Set<String> stopWords = new HashSet<>(Arrays.asList("a", "an", "the", "and", "but", "or", "for", "nor", "on", "at", "to", "from", "by", "with", "in", "out", "of"));
        Map<String, Map<String,Integer>> invertedIndex = new HashMap<>();
        for (int i = 0; i < documents.size(); i++) {
            String[] tokens = lemmatizeTokens(documents.get(i).toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+"));
            for (int j = 0; j < tokens.length; j++) {
                String normalizedToken = tokens[j].trim();
                if (!normalizedToken.isEmpty() && !stopWords.contains(normalizedToken)) {
                    String stemmedToken = stemToken(normalizedToken);
                    if (!invertedIndex.containsKey(stemmedToken)) {
                        invertedIndex.put(stemmedToken,new HashMap<>());
                    }
                    invertedIndex.get(stemmedToken).put("doc" + i, 0);
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

    private static String[] removeStopWords(String[] queryTokens, Set<String> stopWords) {
        List<String> tokens = new ArrayList<>();
        for (String token : queryTokens) {
            if (!stopWords.contains(token)) {
                tokens.add(stemToken(token));
            }
        }
        return tokens.toArray(new String[0]);
    }

    // Evaluate a boolean query for a given document index
    public static boolean evaluateBooleanQuery(String[] queryTokens, Map<String, Map<String,Integer>> invertedIndex, int docIndex) {
        boolean result = false;
        boolean op = false;
        for (String token : queryTokens) {
            if (token.equals("and")) {
                op = true;
            } else if (token.equals("or")) {
                op = false;
            } else {
                boolean termPresent = invertedIndex.containsKey(stemToken(token)) && invertedIndex.get(stemToken(token)).containsKey("doc" + docIndex);
                if (op) {
                    result = result && termPresent;
                } else {
                    result = result || termPresent;
                }
            }
        }
        return result;
    }

    // Stem a token using the Porter stemming algorithm
    public static String stemToken(String token) {
        char[] word = token.toCharArray();
        int length = word.length;
        Stemmer stemmer = new Stemmer();
        stemmer.add(word, length);
        stemmer.stem();
        return stemmer.toString();
    }

    // Lemmatize an array of tokens
    public static String[] lemmatizeTokens(String[] tokens) {
        String[] lemmatizedTokens = new String[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token.endsWith("s")) {
                lemmatizedTokens[i] = token.substring(0, token.length() - 1);
            } else if (token.endsWith("es")) {
                lemmatizedTokens[i] = token.substring(0, token.length() - 2);
            } else if (token.endsWith("ed")) {
                lemmatizedTokens[i] = token.substring(0, token.length() - 2);
            } else if (token.endsWith("ing")) {
                lemmatizedTokens[i] = token.substring(0, token.length() - 3);
            } else {
                lemmatizedTokens[i] = token;
            }
        }
        return lemmatizedTokens;
    }
}
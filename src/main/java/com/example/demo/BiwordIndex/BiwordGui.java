package com.example.demo.BiwordIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiwordGui {
    public BiwordGui(String s) throws IOException {

        // Load the documents
        List<String> documents = new ArrayList<>();
        documents.addAll(loadDocuments("C:\\Users\\Elbostan\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset"));

        // Create the term-document incidence matrix
        Map<String, Map<String, List<Integer>>> matrix = createBiwordMatrix(documents);

        // Allow the user to enter a boolean query and output relevant documents
        String query = s.toLowerCase();
        String[] queryTokens = tokenize(query);
        List<String> relevantDocuments = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            boolean relevant = evaluateBooleanQuery(queryTokens, matrix, i);
            if (relevant) {
                relevantDocuments.add("doc" + i);
            }
        }
        System.out.println("Relevant documents: " + relevantDocuments);
    }

    // Tokenize the input string
    public static String[] tokenize(String input) {
        input = input.toLowerCase().replaceAll("[^a-zA-Z0-9\\s\\*]", ""); // Remove characters that are not letters, numbers, spaces, or asterisks
        input = input.replaceAll("\\*", ".*"); // Replace asterisks with a regular expression matching any character
        return input.split("\\s+");
    }

    // Create a term-document incidence matrix from a list of documents
    public static Map<String, Map<String, List<Integer>>> createBiwordMatrix(List<String> documents) {
        Map<String, Map<String, List<Integer>>> matrix = new HashMap<>();
        for (int i = 0; i < documents.size(); i++) {
            String[] tokens = tokenize(documents.get(i));
            for (int j = 0; j < tokens.length - 1; j++) {
                String biword = tokens[j] + " " + tokens[j+1];
                if (!matrix.containsKey(biword)) {
                    matrix.put(biword, new HashMap<>());
                }
                if (!matrix.get(biword).containsKey("doc" + i)) {
                    matrix.get(biword).put("doc" + i, new ArrayList<>());

                }
                matrix.get(biword).get("doc" + i).add(j);
            }
        }
        return matrix;
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
        documents.addAll(loadDocuments("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset"));

        // Create the biword-document incidence matrix
        Map<String, Map<String, List<Integer>>> matrix = createBiwordMatrix(documents);

        // Allow the user to enter a boolean query and output relevant documents
        String query = args[0].toLowerCase();
        String[] queryTokens = tokenize(query);
        List<String> relevantDocuments = new ArrayList<>();

        for (int i = 0; i < documents.size(); i++) {
            boolean relevant = evaluateBooleanQuery(queryTokens, matrix, i);
            if (relevant) {
                relevantDocuments.add("doc" + i);
            }
        }
        System.out.println("Relevant documents: " + relevantDocuments);
    }

    // Evaluate a boolean query for a given document index
    public static boolean evaluateBooleanQuery(String[] queryTokens,  Map<String, Map<String, List<Integer>>> matrix, int docIndex) {
        boolean result = false;
        boolean op = false;
        for (int i = 0; i < queryTokens.length - 1; i++) {
            String biword = queryTokens[i] + " " + queryTokens[i+1];
            if (queryTokens[i].equals("and")) {
                op = true;
            } else if (queryTokens[i].equals("or")) {
                op = false;
            } else {
                boolean biwordPresent = false;
                if (biword.contains("*")) { // Handle wildcard tokens
                    for (Map.Entry<String, Map<String, List<Integer>>> entry : matrix.entrySet()) {
                        if (entry.getKey().matches(biword)) {
                            if (entry.getValue().containsKey("doc" + docIndex)) {
                                biwordPresent = true;
                                break;
                            }
                        }
                    }
                } else {
                    biwordPresent = matrix.containsKey(biword) && matrix.get(biword).containsKey("doc" + docIndex);
                }
                if (op) {
                    result = result && biwordPresent;
                } else {
                    result = result || biwordPresent;
                }
            }
        }
        return result;
    }
}
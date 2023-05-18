package com.example.demo.Term_doc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TermDocumentMatrix {
    public TermDocumentMatrix(String s) throws IOException {

        // Load the documents
        List<String> documents = new ArrayList<>();
        documents.addAll(loadDocuments("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\Preprocessing\\tokenized_files"));

        // Create the term-document incidence matrix
        Map<String, Map<String, Integer>> matrix = createIncidenceMatrix(documents);

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
        return input.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");
    }

    // Create a term-document incidence matrix from a list of documents
    public static Map<String, Map<String, Integer>> createIncidenceMatrix(List<String> documents) {
        Map<String, Map<String, Integer>> matrix = new HashMap<>();
        for (int i = 0; i < documents.size(); i++) {
            String[] tokens = tokenize(documents.get(i));
            for (int j = 0; j < tokens.length; j++) {
                if (!matrix.containsKey(tokens[j])) {
                    matrix.put(tokens[j], new HashMap<>());
                }
                if (!matrix.get(tokens[j]).containsKey("doc" + i)) {
                    matrix.get(tokens[j]).put("doc" + i, 0);
                }
                matrix.get(tokens[j]).put("doc" + i, matrix.get(tokens[j]).get("doc" + i) + 1);
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
        documents.addAll(loadDocuments("C:\\Users\\Elbostan\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset"));

        // Create the term-document incidence matrix
        Map<String, Map<String, Integer>> matrix = createIncidenceMatrix(documents);

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
    public static boolean evaluateBooleanQuery(String[] queryTokens, Map<String, Map<String, Integer>> matrix, int docIndex) {
        boolean result = false;
        boolean op = false;
        for (String token : queryTokens) {
            if (token.equals("and")) {
                op = true;
            } else if (token.equals("or")) {
                op = false;
            } else {
                boolean termPresent = matrix.containsKey(token) && matrix.get(token).containsKey("doc" + docIndex);
                if (op) {
                    result = result && termPresent;
                } else {
                    result = result || termPresent;
                }
            }
        }
        return result;
    }
}

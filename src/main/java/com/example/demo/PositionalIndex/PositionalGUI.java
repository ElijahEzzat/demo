
/* positional index is a type of inverted index where the postings for each term
   not only include the document identifiers but also the positions
   where the term appears in the document and this allows for more precise
   matching and ranking of search results
*/

package com.example.demo.PositionalIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PositionalGUI {

    // s is the user's search query
    public PositionalGUI (String s) throws IOException {

        // Load the documents
        List<String> documents = new ArrayList<>();
        documents.addAll(loadDocuments("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\Preprocessing\\normalized"));

        // Create the positional index
        Map<String, Map<String, List<Integer>>> index = createPositionalIndex(documents);

        // Allow the user to enter a boolean or phrase query and output relevant documents
        String query = s.toLowerCase();
        String[] queryTokens = tokenize(query);
        List<String> relevantDocuments = new ArrayList<>();

        if (query.contains("\"")) {
            // Phrase query
            String phraseQuery = query.replaceAll("\"", "");
            String[] phraseQueryTokens = phraseQuery.split("\\s+");
            relevantDocuments = evaluatePhraseQuery(phraseQueryTokens, index);
        } else {
            // Boolean query
            relevantDocuments = evaluateBooleanQuery(queryTokens, index);
        }

        System.out.println("Relevant documents: " + relevantDocuments);
    }

    // Tokenize the input string
    public static String[] tokenize(String input) {
        return input.toLowerCase().replaceAll("[^a-zA-Z0-9\\s\"]", "").split("\\s+");
    }

    // Create a positional index from a list of documents
    public static Map<String, Map<String, List<Integer>>> createPositionalIndex(List<String> documents) {
        Map<String, Map<String, List<Integer>>> index = new HashMap<>();
        for (int i = 0; i < documents.size(); i++) {
            String[] tokens = tokenize(documents.get(i));
            for (int j = 0; j < tokens.length; j++) {
                if (!index.containsKey(tokens[j])) {
                    index.put(tokens[j], new HashMap<>());
                }
                if (!index.get(tokens[j]).containsKey("doc" + i)) {
                    index.get(tokens[j]).put("doc" + i, new ArrayList<>());
                }
                index.get(tokens[j]).get("doc" + i).add(j);
            }
        }
        return index;
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

    // Evaluate a boolean query
    public static List<String> evaluateBooleanQuery(String[] queryTokens, Map<String, Map<String, List<Integer>>> index) {
        List<String> relevantDocuments = new ArrayList<>();
        for (int i = 0; i < index.keySet().size(); i++) {
            boolean relevant = evaluateBooleanQueryForDocument(queryTokens, index, i);
            if (relevant) {
                relevantDocuments.add("doc" + i);
            }
        }
        return relevantDocuments;
    }

    // Evaluate a boolean query for a given document index
    public static boolean evaluateBooleanQueryForDocument(String[] queryTokens, Map<String, Map<String, List<Integer>>> index, int docIndex) {
        boolean result = false;
        boolean op = false;
        for (String token : queryTokens) {
            if (token.equals("and")) {
                op = true;
            } else if (token.equals("or")) {
                op = false;
            } else {
                boolean termPresent = index.containsKey(token) && index.get(token).containsKey("doc" + docIndex);
                if (op) {
                    result = result && termPresent;
                } else {
                    result = result || termPresent;
                }
            }
        }
        return result;
    }

    // Evaluate a phrase query
    public static List<String> evaluatePhraseQuery(String[] phraseQueryTokens, Map<String, Map<String, List<Integer>>> index) {
        List<String> relevantDocuments = new ArrayList<>();
        for (int i = 0; i < index.keySet().size(); i++) {
            boolean relevant = evaluatePhraseQueryForDocument(phraseQueryTokens, index, i);
            if (relevant) {
                relevantDocuments.add("doc" + i);
            }
        }
        return relevantDocuments;
    }

    // Evaluate a phrase query for a given document index
    public static boolean evaluatePhraseQueryForDocument(String[] phraseQueryTokens, Map<String, Map<String, List<Integer>>> index, int docIndex) {
        boolean result = false;
        List<List<Integer>> phrasePositions = new ArrayList<>();

        // Get the positions of each token in the phrase query
        for (String token : phraseQueryTokens) {
            if (index.containsKey(token) && index.get(token).containsKey("doc" + docIndex)) {
                phrasePositions.add(index.get(token).get("doc" + docIndex));
            } else {
                return false;
            }
        }

        // Check if the positions form a consecutive sequence
        for (int i = 0; i < phrasePositions.get(0).size(); i++) {
            int startPos = phrasePositions.get(0).get(i);
            boolean sequenceFound = true;
            for (int j = 1; j < phrasePositions.size(); j++) {
                if (!phrasePositions.get(j).contains(startPos + j)) {
                    sequenceFound = false;
                    break;
                }
            }
            if (sequenceFound) {
                result = true;
                break;
            }
        }

        return result;
    }

}

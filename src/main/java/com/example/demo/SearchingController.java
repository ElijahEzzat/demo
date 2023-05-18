package com.example.demo;

import com.example.demo.Invertedindex.InvertedIndex;
import com.example.demo.Invertedindex.InvertedIndex2;
import com.example.demo.PositionalIndex.PositionalGUI;
import com.example.demo.Term_doc.TermDocumentMatrix;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SearchingController implements Initializable {

    @FXML
    private TextField textSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private ScrollBar labelResult;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private CheckBox Tokenization;
    @FXML
    private CheckBox Normalization;
    @FXML
    private CheckBox StopWords;
    @FXML
    private CheckBox Lemetization;
    @FXML
    private CheckBox Stemming;
    @FXML
    private Button BackToHome;
    private Map<String, Map<String, Integer>> matrix;
    private Map<String, Map<String,Integer>> matrix3;
    private Map<String, Map<String,Integer>> matrix2;
    private Map<String, Map<String, List<Integer>>> matrix4;

    // Load a list of documents from a text file
    private List<String> loadDocuments(String filename) throws IOException {
        List<String> documents = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        while (line != null) {
            documents.add(line);
            line = reader.readLine();
        }
        reader.close();
        return documents;
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        BackToHome.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = (Stage) BackToHome.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Load the documents
        List<String> documents = new ArrayList<>();
        try {
            File folder = new File("C:\\Users\\eezt3\\Downloads\\demo\\Preprocessing");
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    documents.addAll(loadDocuments(file.getAbsolutePath()));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create the term-document incidence matrix
        matrix = TermDocumentMatrix.createIncidenceMatrix(documents);
        matrix2 = InvertedIndex.createInvertedIndex(documents);
        matrix3 = InvertedIndex2.createInvertedIndex(documents);
        matrix4 = PositionalGUI.createPositionalIndex(documents);
        comboBox.setItems(FXCollections.observableArrayList("Lucene","Term-document", "Inverted index", "Positional index", "Bi-word index"));
        btnSearch.setOnAction(event -> {
            if(comboBox.getValue()=="Lucene"){
                String query = textSearch.getText().toLowerCase();
                List<String> relevantDocuments = new ArrayList<>();
                UIManager.put("OptionPane",new java.awt.Dimension(500,500));
                JOptionPane.showMessageDialog( null, "Relevant decoment"+relevantDocuments,null, JOptionPane.YES_NO_CANCEL_OPTION);

            }else if (comboBox.getValue() == "Term-document" && Tokenization.isSelected()) {
                String query = textSearch.getText().toLowerCase();
                String[] queryTokens = TermDocumentMatrix.tokenize(query);
                List<String> relevantDocuments = new ArrayList<>();

                for (int i = 0; i < documents.size(); i++) {
                    boolean relevant = TermDocumentMatrix.evaluateBooleanQuery(queryTokens, matrix, i);
                    if (relevant) {

                        relevantDocuments.add("doc" + i+"\n");


                    }
                }

                UIManager.put("OptionPane",new java.awt.Dimension(500,500));
                JOptionPane.showMessageDialog( null, "Relevant decoment"+relevantDocuments,null, JOptionPane.YES_NO_CANCEL_OPTION);
                //labelResult.setText("Relevant documents: " + relevantDocuments);
// start of printing in console
                   List<String> documens = new ArrayList<>();
                try {
                    documens.addAll(loadDocuments("C:\\Users\\eezt3\\Downloads\\demo\\Preprocessing\\tokenized_files\\tokenized_text.txt"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Create a set of terms in alphabetical order
                Set<String> terms = new TreeSet<>();
                for (String document : documens) {
                    String[] words = document.split(" ");
                    for (String word : words) {
                        terms.add(word);
                    }
                }
                // Create an alphabetical ordered term-document incidence matrix
                int[][] matrix = new int[terms.size()][documens.size()];
                int termIndex = 0;
                for (String term : terms) {
                    int docIndex = 0;
                    for (String document : documens) {
                        String[] words = document.split(" ");
                        int termCount = 0;
                        for (String word : words) {
                            if (term.equals(word)) {
                                termCount++;
                            }
                        }
                        matrix[termIndex][docIndex] = termCount;
                        docIndex++;
                    }
                    termIndex++;
                }

                // Print the alphabetical ordered term-document incidence matrix
                System.out.print("          ");
                for (int i = 0; i < documens.size(); i++) {
                    System.out.printf("Doc%02d ", i + 1);
                }
                System.out.println();
                for (int i = 0; i < matrix.length; i++) {
                    System.out.printf("%-10s", terms.toArray()[i]);
                    for (int j = 0; j < matrix[i].length; j++) {
                        System.out.printf("%5d ", matrix[i][j]);
                    }
                    System.out.println();
                }



// end of printing in console

            } else if (comboBox.getValue() == "Inverted index" && Tokenization.isSelected() && Normalization.isSelected() && StopWords.isSelected() && Stemming.isSelected()&&Lemetization.isSelected()) {
                String query = textSearch.getText().toLowerCase();
                String[] queryTokens = InvertedIndex2.tokenize(query);
                List<String> relevantDocuments = new ArrayList<>();

                for (int i = 0; i < documents.size(); i++) {
                    boolean relevant = InvertedIndex.evaluateBooleanQuery(queryTokens, matrix2, i);
                    if (relevant) {

                        relevantDocuments.add("doc" + i );

                    }
                }
                UIManager.put("OptionPane",new java.awt.Dimension(500,500));
                JOptionPane.showMessageDialog( null, "Relevant decoment"+relevantDocuments,null, JOptionPane.YES_NO_CANCEL_OPTION);
                //labelResult.setText("Relevant documents: " + relevantDocuments);
            } else if (comboBox.getValue() == "Inverted index" && Tokenization.isSelected() && Normalization.isSelected() && StopWords.isSelected()) {
                String query = textSearch.getText().toLowerCase();
                String[] queryTokens = InvertedIndex2.tokenize(query);
                List<String> relevantDocuments = new ArrayList<>();

                for (int i = 0; i < documents.size(); i++) {
                    boolean relevant = InvertedIndex2.evaluateBooleanQuery(queryTokens, matrix2, i);
                    if (relevant) {
                        relevantDocuments.add("doc" + i);
                    }
                }
                UIManager.put("OptionPane",new java.awt.Dimension(500,500));
                JOptionPane.showMessageDialog( null, "Relevant decoment"+relevantDocuments,null, JOptionPane.YES_NO_CANCEL_OPTION);
                //labelResult.setText("Relevant documents: " + relevantDocuments);
            } else if (comboBox.getValue() == "Positional index"&& Tokenization.isSelected()) {
                String query = textSearch.getText().toLowerCase();
                String[] queryTokens = PositionalGUI.tokenize(query);
                List<String> relevantDocuments = new ArrayList<>();

                for (int i = 0; i < documents.size(); i++) {
                    boolean relevant = PositionalGUI.evaluateBooleanQueryForDocument(queryTokens, matrix4, i);
                    if (relevant) {
                        relevantDocuments.add("doc" + i);
                    }
                }
                UIManager.put("OptionPane",new java.awt.Dimension(500,500));
                JOptionPane.showMessageDialog( null, "Relevant decoment"+relevantDocuments,null, JOptionPane.YES_NO_CANCEL_OPTION);
                //labelResult.setText("Relevant documents: " + relevantDocuments);

                ///////////////////////



                ///////////////////////
            } else if (comboBox.getValue() == "Bi-word index"&& Tokenization.isSelected()) {
                String query = textSearch.getText().toLowerCase();
                String[] queryTokens = TermDocumentMatrix.tokenize(query);
                List<String> relevantDocuments = new ArrayList<>();

                for (int i = 0; i < documents.size(); i++) {
                    boolean relevant = TermDocumentMatrix.evaluateBooleanQuery(queryTokens, matrix, i);
                    if (relevant) {
                        relevantDocuments.add("doc" + i);
                    }
                }
                UIManager.put("OptionPane",new java.awt.Dimension(500,500));
                JOptionPane.showMessageDialog( null, "Relevant decoment"+relevantDocuments,null, JOptionPane.YES_NO_CANCEL_OPTION);
                //labelResult.setText("Relevant documents: " + relevantDocuments);
            }
        });
    }

    @FXML
    void getComboBoxValue(ActionEvent event) {
        System.out.println(comboBox.getValue());
    }
    public void BackButton() {
        BackToHome.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = (Stage) BackToHome.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

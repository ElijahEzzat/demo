package com.example.demo;

import Preprocessing.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IndexingController implements Initializable {

    @FXML
    private ComboBox<String> combo;
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
    private Button Backing;
    @FXML
    private Button Applying;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Backing.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = (Stage) Backing.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        combo.setItems(FXCollections.observableArrayList( "Lucene","Term-document","Inverted index","Positional index","Bi-word index"));
        Applying.setOnAction(event -> {
           /* if (combo.getValue()=="Lucene"){
            if (Tokenization.isSelected()){
                try {
                    Tokenization TK = new Tokenization("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (Normalization.isSelected()) {
                try {
                    Normalization N = new Normalization("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else if (StopWords.isSelected()) {
                try {
                    StopWords N = new StopWords("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else if (Lemetization.isSelected()) {
                try {
                    Normalization N = new Normalization("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else if (Stemming.isSelected()) {
                try {
                    Stemming N = new Stemming("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }}*/
            if (combo.getValue()=="Term-document"){
                if (Tokenization.isSelected()){
                    try {
                        Tokenization TK = new Tokenization("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }}
            else if (combo.getValue()=="Inverted index"){

                if (Tokenization.isSelected()){
                    try {
                        Tokenization TK = new Tokenization("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (Normalization.isSelected()) {
                    try {
                        Normalization N = new Normalization("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else if (StopWords.isSelected()) {
                    try {
                        StopWords N = new StopWords("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else if (Lemetization.isSelected()) {
                    try {
                        Lemmatization N = new Lemmatization("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else if (Stemming.isSelected()) {
                    try {
                        Stemming N = new Stemming("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }}
            else if (combo.getValue()=="Positional index"){

                if (Tokenization.isSelected()){
                    try {
                        Tokenization TK = new Tokenization("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (Normalization.isSelected()) {
                    try {
                        Normalization N = new Normalization("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else if (StopWords.isSelected()) {
                    try {
                        StopWords N = new StopWords("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else if (Lemetization.isSelected()) {
                    try {
                        Normalization N = new Normalization("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else if (Stemming.isSelected()) {
                    try {
                        Stemming N = new Stemming("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }}
            else if (combo.getValue()=="Bi-word index"){

                if (Tokenization.isSelected()){
                    try {
                        Tokenization TK = new Tokenization("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if (Normalization.isSelected()) {
                    try {
                        Normalization N = new Normalization("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else if (StopWords.isSelected()) {
                    try {
                        StopWords N = new StopWords("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else if (Lemetization.isSelected()) {
                    try {
                        Normalization N = new Normalization("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else if (Stemming.isSelected()) {
                    try {
                        Stemming N = new Stemming("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }}
        });
    }
    @FXML
    void getComboBoxValue(ActionEvent event){
        System.out.println(combo.getValue());
    }
    public void handleBackButton() {
        Backing.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = (Stage) Backing.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

package com.example.demo;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
public class Controller implements Initializable  {
    @FXML
    private Button Indexing;
    @FXML
    private Button Searching;
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
            Indexing.setOnAction(event -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("indexing-view.fxml"));
                    Stage stage = (Stage) Indexing.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Searching.setOnAction(event -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("searching-view.fxml"));
                    Stage stage = (Stage) Searching.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }
    public void Index(){

        Searching.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("indexing-view.fxml"));
                Stage stage = (Stage) Searching.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public void Search(){

        Searching.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("searching-view.fxml"));
                Stage stage = (Stage) Searching.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
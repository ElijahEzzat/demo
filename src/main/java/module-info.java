module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires lucene.queryparser;
    requires lucene.analyzers.common;
    requires lucene.core;
    requires java.desktop;
    requires stanford.corenlp;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}
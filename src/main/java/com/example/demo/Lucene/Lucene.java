package com.example.demo.Lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Lucene {
    // Load a list of documents
    public static List<String> loadDocuments(String filename) throws IOException {
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

    public static void main(String[] args) throws IOException, ParseException {
        // Load the documents
        List<String> documents = new ArrayList<>();

        documents.addAll(loadDocuments("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\Preprocessing\\normalized\\output_1.txt"));
        documents.addAll(loadDocuments("C:\\Users\\Mohamed Mostafa\\Downloads\\demo\\Preprocessing\\normalized\\output_2.txt"));

        // Create the Lucene index
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);

        Directory index = FSDirectory.open(new File ("index"));
        IndexWriterConfig config = new IndexWriterConfig( Version.LUCENE_42, analyzer);
        try (IndexWriter writer = new IndexWriter(index, config)) {
            for (int i = 0; i < documents.size(); i++) {
                Document doc = new Document();
                doc.add(new TextField("content", documents.get(i), Field.Store.YES));
                writer.addDocument(doc);
            }
        }

        // Allow the user to enter a query and output relevant documents
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a query:");
        String queryString = scanner.nextLine();

        // using IndexReader to open index for reading
        IndexReader reader = DirectoryReader.open(index);


        IndexSearcher searcher = new IndexSearcher(reader);

        //using MultiFieldQueryParser to parse the query and search for relevant documents in index
        MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_42, new String[]{"content"}, analyzer);

        Query query = parser.parse(queryString);
        TopDocs topDocs = searcher.search(query, 10);
        ScoreDoc[] hits = topDocs.scoreDocs;

        List<String> relevantDocuments = new ArrayList<>();
        for (ScoreDoc hit : hits) {
            Document doc = searcher.doc(hit.doc);
            relevantDocuments.add(doc.get("content"));
        }
        reader.close();

        System.out.println("Relevant documents: " + relevantDocuments);

    }

}
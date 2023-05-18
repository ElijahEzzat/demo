package Preprocessing;

import org.tartarus.snowball.ext.PorterStemmer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Stemming {
    public Stemming(String filepath)throws IOException{
        // Iterate through each document in dataset folder
        File datasetFolder = new File(filepath);
        for (File file : datasetFolder.listFiles()) {
            // Read document contents into string
            String contents = new String(Files.readAllBytes(file.toPath()));

            // Tokenize the document
            StringTokenizer tokenizer = new StringTokenizer(contents);
            List<String> stemmedTokens = new ArrayList<String>();
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().toLowerCase();
                // Stem the token
                PorterStemmer stemmer = new PorterStemmer();
                stemmer.setCurrent(token);
                stemmer.stem();
                String stemmedToken = stemmer.getCurrent();
                stemmedTokens.add(stemmedToken);
            }

            // Write cleaned document to new file
            String folderPath = "./Preprocessing/Stemmimg_files/";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String filePath = folderPath + "cleaned_" + file.getName();
            FileWriter writer = new FileWriter(filePath);
            for (String token : stemmedTokens) {
                writer.write(token + " ");
            }
            writer.close();
        }
    }
    public static void main(String[] args) throws IOException {
        // Iterate through each document in dataset folder
        File datasetFolder = new File("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset");
        for (File file : datasetFolder.listFiles()) {
            // Read document contents into string
            String contents = new String(Files.readAllBytes(file.toPath()));

            // Tokenize the document
            StringTokenizer tokenizer = new StringTokenizer(contents);
            List<String> stemmedTokens = new ArrayList<String>();
            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().toLowerCase();
                // Stem the token
                PorterStemmer stemmer = new PorterStemmer();
                stemmer.setCurrent(token);
                stemmer.stem();
                String stemmedToken = stemmer.getCurrent();
                stemmedTokens.add(stemmedToken);
            }

            // Write cleaned document to new file
            String folderPath = "./cleaned_files/";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            String filePath = folderPath + "cleaned_" + file.getName();
            FileWriter writer = new FileWriter(filePath);
            for (String token : stemmedTokens) {
                writer.write(token + " ");
            }
            writer.close();
        }
    }
}
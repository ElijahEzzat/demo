package Preprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Normalization {
public Normalization(String filepath)  throws IOException{
    File[] files = new File(filepath).listFiles();

    // Normalize the text files and print the terms that normalization applied to
    for (File file : files) {
        Set<String> normalizedTerms = normalizeTextFile(file);
        if (!normalizedTerms.isEmpty()) {
            System.out.println("Normalized terms in file " + file.getName() + ": " + normalizedTerms);
        }
    }

}
    public static void main(String[] args) throws IOException {
        // Load the text files
        File[] files = new File("C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset").listFiles();

        // Normalize the text files and print the terms that normalization applied to
        for (File file : files) {
            Set<String> normalizedTerms = normalizeTextFile(file);
            if (!normalizedTerms.isEmpty()) {
                System.out.println("Normalized terms in file " + file.getName() + ": " + normalizedTerms);
            }
        }
    }

    // Normalize a text file and return the set of normalized terms
    public static Set<String> normalizeTextFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        Set<String> normalizedTerms = new HashSet<>();
        while (line != null) {
            String[] tokens = line.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+");
            for (String token : tokens) {
                if (!token.isEmpty()) {
                    if (!token.equals(line)) {
                        normalizedTerms.add(token);
                    }
                }
            }
            line = reader.readLine();
        }
        reader.close();

        // Write the normalized text to a file
        String folderPath = "./Preprocessing/normalized_files/";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = folderPath + "normalized_" + file.getName();
        FileWriter writer = new FileWriter(filePath);
        for (String token : normalizedTerms) {
            writer.write(token + " ");
        }
        writer.close();

        return normalizedTerms;
    }
}
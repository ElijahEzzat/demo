package Preprocessing;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.PropertiesUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Lemmatization {
    public Lemmatization(String filePath) throws IOException {
        // Set up the properties for the pipeline
        Properties props = PropertiesUtils.asProperties(
                "annotators", "tokenize, ssplit, pos, lemma",
                "pos.model", "edu/stanford/nlp/models/pos-tagger/english-left3words-distsim.tagger",
                "tokenize.language", "en"
        );

        // Create the pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // Set input and output directories
        String inputDirPath = filePath;
        String outputDirPath = "./Preprocessing/lemmatized_files/";

        // Create output directory if it doesn't exist
        File outputDir = new File(outputDirPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Loop through each file in input directory
        File inputDir = new File(inputDirPath);
        for (File file : inputDir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                // Read input file contents
                String inputText = new String(java.nio.file.Files.readAllBytes(file.toPath()));

                // Process the text using the pipeline
                Annotation document = new Annotation(inputText);
                pipeline.annotate(document);

                // Get the lemmas for each token in the document
                List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
                StringBuilder outputText = new StringBuilder();
                for (CoreMap sentence : sentences) {
                    for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                        String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                        outputText.append(lemma).append(" ");
                    }
                    outputText.append("\n");
                }

                // Write output to new file in output directory
                File outputFile = new File(outputDir, file.getName());
                FileWriter writer = new FileWriter(outputFile);
                writer.write(outputText.toString());
                writer.close();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // Set up the properties for the pipeline
        Properties props = PropertiesUtils.asProperties(
                "annotators", "tokenize, ssplit, pos, lemma",
                "pos.model", "edu/stanford/nlp/models/pos-tagger/english-left3words-distsim.tagger",
                "tokenize.language", "en"
        );

        // Create the pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // Set input and output directories
        String inputDirPath = "C:\\Users\\eezt3\\Downloads\\demo\\src\\main\\java\\com\\example\\demo\\dataset";
        String outputDirPath = "./Preprocessing/lemmatized_files/";

        // Create output directory if it doesn't exist
        File outputDir = new File(outputDirPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Loop through each file in input directory
        File inputDir = new File(inputDirPath);
        for (File file : inputDir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                // Read input file contents
                String inputText = new String(java.nio.file.Files.readAllBytes(file.toPath()));

                // Process the text using the pipeline
                Annotation document = new Annotation(inputText);
                pipeline.annotate(document);

                // Get the lemmas for each token in the document
                List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
                StringBuilder outputText = new StringBuilder();
                for (CoreMap sentence : sentences) {
                    for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                        String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                        outputText.append(lemma).append(" ");
                    }
                    outputText.append("\n");
                }

                // Write output to new file in output directory
                File outputFile = new File(outputDir, file.getName());
                FileWriter writer = new FileWriter(outputFile);
                writer.write(outputText.toString());
                writer.close();
            }
        }
    }
}
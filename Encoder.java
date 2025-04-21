package ie.atu.sw;

import java.io.*;
import java.util.*;

/**
 * This class encodes words into numbers using a word-to-code mapping from a CSV file.
 */
public class Encoder {
    // A map to store word -> number codes
    private Map<String, Integer> wordMap;

    /**
     * Constructor: loads word-code pairs from a CSV file.
     * @param csvFile the name of the CSV file containing mappings.
     * @throws IOException if there’s an error reading the file.
     */
    public Encoder(String csvFile) throws IOException {
        wordMap = new HashMap<>();
        loadEncodings(csvFile);
    }

    /**
     * Reads the CSV file and fills the wordMap with word -> number pairs.
     * @param csvFile the CSV file with mappings.
     * @throws IOException if file is missing or reading fails.
     */
    private void loadEncodings(String csvFile) throws IOException {
        // Try to load the CSV file from resources
        InputStream inputStream = Runner.class.getResourceAsStream("/ie/atu/sw/" + csvFile);
        if (inputStream == null) {
            throw new FileNotFoundException("Mapping file not found: " + csvFile);
        }

        // Read the file line by line
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    // Map word -> code (as integer)
                    wordMap.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                }
            }
        }
    }

    /**
     * Encodes the input file by converting each word into its corresponding number.
     * @param inputFile the file containing plain text.
     * @param outputFile the file where encoded text (numbers) will be saved.
     * @throws IOException if file reading/writing fails.
     */
    public void encode(String inputFile, String outputFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            int progress = 0;
            List<String> encodedLines = new ArrayList<>();

            // Read each line from the input file
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\\s+"); // Split by spaces
                StringBuilder encodedLine = new StringBuilder();

                // Replace each word with its code
                for (String word : words) {
                    // If the word is not in the map, use 0
                    encodedLine.append(wordMap.getOrDefault(word, 0)).append(" ");
                }

                encodedLines.add(encodedLine.toString().trim());
                printProgress(++progress, 100); // Show progress bar
            }

            // Write all encoded lines to the output file
            for (String encoded : encodedLines) {
                bw.write(encoded);
                bw.newLine();
            }

            System.out.println("\nEncoding completed. Output saved to " + outputFile);
        }
    }

    /**
     * Shows a simple progress bar in the console.
     * @param index current step number.
     * @param total total number of steps.
     */
    private void printProgress(int index, int total) {
        if (index > total) return;
        int size = 50; // Length of progress bar
        char done = '█'; // Filled block
        char todo = '░'; // Empty block

        int complete = (100 * index) / total; // Percent complete
        int completeLen = size * complete / 100; // How many blocks are done

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append((i < completeLen) ? done : todo);
        }

        System.out.print("\r" + sb + "] " + complete + "%");

        if (index == total) System.out.println("\n");
    }
}

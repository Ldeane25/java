package ie.atu.sw;

import java.io.*;
import java.util.*;

public class Decoder {
    // A map to store code (number) -> word
    private Map<Integer, String> codeMap;

    // Constructor: loads the CSV file with codes
    public Decoder(String csvFile) throws IOException {
        codeMap = new HashMap<>();
        loadEncodings(csvFile);
    }

    // Reads the CSV file and fills the codeMap
    private void loadEncodings(String csvFile) throws IOException {
        // Load file from resources
        InputStream inputStream = Runner.class.getResourceAsStream("/ie/atu/sw/" + csvFile);
        if (inputStream == null) {
            throw new FileNotFoundException("Could not find: " + csvFile);
        }

        // Read each line and split into word and code
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int code = Integer.parseInt(parts[1].trim());
                    String word = parts[0].trim();
                    codeMap.put(code, word);
                }
            }
        }
    }

    // Decodes the input file and writes the result to the output file
    public void decode(String inputFile, String outputFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            int progress = 0;
            List<String> decodedLines = new ArrayList<>();

            // Read each line of encoded numbers
            while ((line = br.readLine()) != null) {
                String[] numbers = line.split("\\s+");
                StringBuilder decodedLine = new StringBuilder();

                // Convert each number to a word using the map
                for (String num : numbers) {
                    try {
                        int code = Integer.parseInt(num);
                        // If code not found, use [???]
                        decodedLine.append(codeMap.getOrDefault(code, "[???]")).append(" ");
                    } catch (NumberFormatException e) {
                        decodedLine.append("[???] ");
                    }
                }

                // Save the decoded line
                decodedLines.add(decodedLine.toString().trim());
                printProgress(++progress, 100); // Show progress
            }

            // Write decoded lines to output file
            for (String decoded : decodedLines) {
                bw.write(decoded);
                bw.newLine();
            }

            System.out.println("\nDecoding done! Output saved to " + outputFile);
        }
    }

    // Prints a simple progress bar in the console
    private void printProgress(int index, int total) {
        if (index > total) return;
        int size = 50;
        char done = '█';
        char todo = '░';

        int percent = (100 * index) / total;
        int doneLen = size * percent / 100;

        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            bar.append(i < doneLen ? done : todo);
        }

        System.out.print("\r" + bar + "] " + percent + "%");

        if (index == total) System.out.println("\n");
    }
}

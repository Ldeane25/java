package ie.atu.sw;

import java.io.*;
import java.util.*;

public class Runner {
    // Default CSV file for word-code mappings
    private static final String DEFAULT_MAPPING = "encodings-10000.csv";
    // Default name for output file
    private static final String DEFAULT_OUTPUT = "out.txt";

    // Encoder and Decoder objects
    private Encoder encoder;
    private Decoder decoder;

    // Current settings for file paths and mode
    private String currentMapping = DEFAULT_MAPPING;
    private String currentInputFile;
    private String currentOutputFile = DEFAULT_OUTPUT;
    private boolean encodeMode = true; // true = encode, false = decode

    // Program starts here
    public static void main(String[] args) {
        new Runner().start(); // Create Runner instance and start the program
    }

    // Main loop of the program
    public void start() {
        try {
            reloadMappings(); // Load the default mappings

            Scanner scanner = new Scanner(System.in);
            while (true) {
                displayMenu(); // Show user options
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        // Let user specify a new mapping file
                        System.out.print("Enter mapping file path (or press Enter to use default): ");
                        String newMapping = scanner.nextLine();
                        if (!newMapping.trim().isEmpty()) {
                            currentMapping = newMapping;
                            reloadMappings();
                        }
                        break;
                    case "2":
                        // Let user specify input file
                        System.out.print("Enter text file to encode/decode: ");
                        currentInputFile = scanner.nextLine();
                        break;
                    case "3":
                        // Let user specify output file
                        System.out.print("Enter output file name: ");
                        currentOutputFile = scanner.nextLine();
                        break;
                    case "4":
                        // Toggle between encode and decode mode
                        encodeMode = !encodeMode;
                        System.out.println("Mode switched to: " + (encodeMode ? "Encoding" : "Decoding"));
                        break;
                    case "5":
                        // Perform encoding if input file is set
                        if (currentInputFile != null && !currentInputFile.isEmpty()) {
                            encoder.encode(currentInputFile, currentOutputFile);
                        } else {
                            System.out.println("Error: No input file specified.");
                        }
                        break;
                    case "6":
                        // Perform decoding if input file is set
                        if (currentInputFile != null && !currentInputFile.isEmpty()) {
                            decoder.decode(currentInputFile, currentOutputFile);
                        } else {
                            System.out.println("Error: No input file specified.");
                        }
                        break;
                    case "?":
                        // Quit the program
                        System.out.println("Exiting program.");
                        scanner.close();
                        return;
                    default:
                        // Handle invalid menu options
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (Exception e) {
            // Print any unexpected error
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Loads the mappings again (used on startup or when switching CSV file)
    private void reloadMappings() throws IOException {
        encoder = new Encoder(currentMapping);
        decoder = new Decoder(currentMapping);
        System.out.println("Mappings reloaded successfully!");
    }

    // Displays the menu options to the user
    private void displayMenu() {
        System.out.println("\n************************************************************");
        System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
        System.out.println("*              Encoding Words with Suffixes                *");
        System.out.println("************************************************************");
        System.out.println("(1) Specify Mapping File");
        System.out.println("(2) Specify Text File to Encode/Decode");
        System.out.println("(3) Specify Output File (default: ./out.txt)");
        System.out.println("(4) Toggle Encoding/Decoding Mode");
        System.out.println("(5) Encode Text File");
        System.out.println("(6) Decode Text File");
        System.out.println("(?) Quit");
        System.out.print("Select Option [1-?]> ");
    }
}

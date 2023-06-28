package dijkstra.dollhouse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class is used to print the descriptions of the rooms and items.
 */
public final class PrintDescriptions {
    private PrintDescriptions() {
    }
    /**
     *
     */
    public static void print(final String searchedWord, final String filePath) {
        try {
            File file = new File(filePath);
            Scanner fileScanner = new Scanner(file);
            String line;
            Boolean exit = false;
            // scan file line by line
            while (fileScanner.hasNextLine() || !exit) {
                line = fileScanner.nextLine();
                // check if line contains searchedWord
                if (line.startsWith(searchedWord)) {
                    exit = true;
                    // remove searchedWord from line
                    line = line.replace(searchedWord + " ", "");
                    while (!line.contains("#")) {
                        System.out.println(line);
                        line = fileScanner.nextLine();
                    }
                }
            }

            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato: " + filePath);
            e.printStackTrace();
        }
    }

}

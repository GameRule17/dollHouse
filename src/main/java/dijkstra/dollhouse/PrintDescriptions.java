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
    public static void print(final String parolaDaCercare, final String percorsoFile) {
        try {
            // Apre il file per la lettura
            File file = new File(percorsoFile);
            Scanner fileScanner = new Scanner(file);
            String riga;
            Boolean exit = false;
            // Scannerizza il file riga per riga
            while (fileScanner.hasNextLine() || !exit) {
                riga = fileScanner.nextLine();
                // Verifica se la riga inizia con la parola desiderata
                if (riga.startsWith(parolaDaCercare)) {
                    exit = true;
                    // Rimuovo la parola iniziale in maiuscolo
                    riga = riga.replace(parolaDaCercare + " ", "");
                    while (!riga.contains("#")) {
                        System.out.println(riga);
                        riga = fileScanner.nextLine();
                    }
                }
            }

            // Chiude lo scanner del file
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato: " + percorsoFile);
            e.printStackTrace();
        }
    }

}

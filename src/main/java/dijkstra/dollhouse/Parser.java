package dijkstra.dollhouse;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Parser {

    private static final int COMMAND_LENGTH = 2; // verb + noun
    private static final int MAX_INPUTCOMMAND_LENGTH = 8;
    //frase più lunga individuata tra quelle possibili: fai il bagno nella vasca da bagno
    private static final int MIN_INPUTCOMMAND_LENGTH = 1;

    private Parser () {
        //empty constructor
    }

    private static String[] splitInput (final String inputCommand) {

        String[] splittedInput = inputCommand.split(" ");
        if(splittedInput.length < MIN_INPUTCOMMAND_LENGTH || splittedInput.length > MAX_INPUTCOMMAND_LENGTH) {
            return null;
        }
        else {
            return splittedInput;
        }
    }

    private static String[] removeStopword(final String[] splittedInput) {
        String line;
        Set<String> stopwords = new HashSet<>();
        String[] command = new String[COMMAND_LENGTH];
        int j = 0;

        try {
            File file = new File("C:\\Users\\Daniele\\Desktop\\progettoMap\\dollHouse\\res\\parser\\stopwords.txt");
            Scanner fileScanner = new Scanner(file);

            // memorize stopwords in the file
            while (fileScanner.hasNextLine()) {
                line = fileScanner.nextLine();
                stopwords.add(line);
            }
            fileScanner.close();

            for (int i = 0; i < splittedInput.length; i++) {
                if (j<COMMAND_LENGTH && !stopwords.contains(splittedInput[i])) {
                    command[j] = splittedInput[i];
                    j++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return command;
    }

    public static StringBuilder parseInput (final String command) {
        String[] initialCommand = splitInput(command);
        StringBuilder finalCommand = new StringBuilder();
        if(initialCommand != null) {
            initialCommand = removeStopword(initialCommand);
            // visualizziamo solo i campi non vuoti nel comando finale
            for (int i = 0; i < initialCommand.length; i++) {
                if(initialCommand[i] != null) {
                    finalCommand.append(initialCommand[i]);
                    finalCommand.append(" ");
                }
            }
        }
        return finalCommand;
    }
}
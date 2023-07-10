package dijkstra.dollhouse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Parser class.
 */
public class Parser {

  private static final int COMMAND_LENGTH = 2;
  private static final int MAX_INPUTCOMMAND_LENGTH = 8;
  private static final int MIN_INPUTCOMMAND_LENGTH = 1;
  private static Set<String> stopwords = new HashSet<String>();

  /**
   * Constructor.
   *
   * @throws FileNotFoundException - if the file is not found.
   */
  private Parser() throws FileNotFoundException {

    String line;
    try {
      File file = new File("./res/parser/stopwords.txt");
      Scanner fileScanner = new Scanner(file);

      while (fileScanner.hasNextLine()) {
        line = fileScanner.nextLine();
        stopwords.add(line);
      }
      fileScanner.close();

    } catch (FileNotFoundException e) {
      throw e;
    }
    
  }

  private static String[] splitInput(final String inputCommand) {

    String[] splittedInput = inputCommand.split(" ");
    if (splittedInput.length < MIN_INPUTCOMMAND_LENGTH 
        || splittedInput.length > MAX_INPUTCOMMAND_LENGTH) {
      return null;
    } else {
      return splittedInput;
    }
  }

  private static String[] removeStopword(final String[] splittedInput) {
    
    String[] command = new String[COMMAND_LENGTH];
    int j = 0;
    
    for (int i = 0; i < splittedInput.length; i++) {
      if (j < COMMAND_LENGTH && !stopwords.contains(splittedInput[i])) {
        command[j] = splittedInput[i];
        j++;
      }
    }
    
    return command;
  }

  /**
   * Define the final command.
   *
   * @param command - the input command.
   * @return - the final command
   * @throws FileNotFoundException - if the file is not found.
   */
  public static StringBuilder parseInput(final String command) {
    String[] initialCommand = splitInput(command);
    StringBuilder finalCommand = new StringBuilder();
    if (initialCommand != null) {
      initialCommand = removeStopword(initialCommand);
      for (int i = 0; i < initialCommand.length; i++) {
        if (initialCommand[i] != null) {
          finalCommand.append(initialCommand[i]);
        } else {
          finalCommand.append("");
        }
        finalCommand.append(" ");
      }
    }
    return finalCommand;
  }
}
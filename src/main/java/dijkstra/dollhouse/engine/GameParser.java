package dijkstra.dollhouse.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Parser class.
 */
public class GameParser {

  private Set<String> stopwords = new HashSet<String>();

  /**
   * Constructor.
   *
   * @throws FileNotFoundException - if the file is not found.
   */
  public GameParser() throws FileNotFoundException {

    String line;
    Scanner fileScanner = null;
    try {
      File file = new File("./res/parser/stopwords.txt");
      fileScanner = new Scanner(file);

      while (fileScanner.hasNextLine()) {
        line = fileScanner.nextLine();
        stopwords.add(line);
      }

    } catch (FileNotFoundException e) {
      throw e;
    } finally {
      if (fileScanner != null) {
        fileScanner.close();
      }
    }
    
  }

  /**
   * .
   *
   * @param input .
   * @return .
   */
  public ParsedInput parse(final String input) {
    
    String[] tokens = input.split("\\s+");
    StringBuilder s = new StringBuilder();
    int i = 0;
    int size = tokens.length;
    String action = "";
    
    while (i < size && (stopwords.contains(tokens[i]) || tokens[i].compareTo("") == 0)) {
      // System.out.println("Skipped: <" + tokens[i] + ">");
      i++;
    }

    if (i < size) {
      // System.out.println("Added: <" + tokens[i] + ">");
      action = tokens[i];
    }

    do {
      i++;
    } while (i < size && stopwords.contains(tokens[i]));

    for (; i < size; i++) {
      s.append(tokens[i] + " ");
    }

    if (s.length() > 0) {
      s.deleteCharAt(s.length() - 1);
    }
    
    return new ParsedInput(action, s.toString());
  }
}
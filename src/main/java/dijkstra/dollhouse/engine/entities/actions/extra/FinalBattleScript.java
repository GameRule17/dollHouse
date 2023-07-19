package dijkstra.dollhouse.engine.entities.actions.extra;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Script for the final battle of the game.
 */
public class FinalBattleScript implements GameScript {
  private static final String url = "./res/dialogues/witchFight.txt";
  private String output;
  private Exception exception;

  /**
   * Public Constructor.
   */
  public FinalBattleScript() {
    StringBuilder s = new StringBuilder();
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File(url));
      while (scanner.hasNextLine()) {
        s.append(scanner.nextLine());
      }
    } catch (FileNotFoundException e) {
      exception = e;
    } finally {
      if (scanner != null) {
        scanner.close();
      }
    }
  }

  @Override
  public String execute() {
    try {
      GameHandler.saveGame();
    } catch (Exception e) {
      exception = e;
    }
    // carica le statistiche
    // pop up hai finito il gioco
    // ecco le tue statistiche
    // torna al menu iniziale
    return output;
  }

  @Override
  public boolean isOver() {
    return true;
  }

  @Override
  public Exception getException() {
    return exception;
  }
}

package dijkstra.dollhouse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import org.json.simple.parser.ParseException;

/**
 * L'ActionListener avrà un gamehandler.
 * GameHadler dovrà essere interrogato dagli script
 * per ottenere le informazioni necesarie per la loro esecuzione.
 */
public class GameHandler {
  private static final String initUrl = "./res/maps/piano_terra.json";
  private static final String savedGameUrl = "./res/savings/saved_game.dat";
  private Game game;
  // parser

  public void newGame()
                      throws ClassNotFoundException, NoSuchMethodException,
                      InstantiationException, IllegalAccessException,
                      IllegalArgumentException, InvocationTargetException,
                      IOException, ParseException {
    game = new Game(initUrl, null);
  }

  public Game getGame() {
    return game;
  }

  // public String executeCommand(final String command) {
  //   // parse the command
  //   // find the entity
  //   // find the action
  //   /*
  //   if (action instanceof GameScriptedAction) {
  //     do {
  //       action.execute(game);
  //       // sets the output
  //       // checks the exception
  //     } while (!action.isOver())
  //   }
  //   // returns the output
  //    */
  // }

  /**
   * Load a game run started yet.
   *
   * @throws IOException - In case of IO Errors
   * @throws ClassNotFoundException - In case has failed the initialization of the game
   */
  public void loadGame() throws IOException, ClassNotFoundException {
    FileInputStream file = null;
    ObjectInputStream input = null;

    try {
      file = new FileInputStream(new File(savedGameUrl));
      input = new ObjectInputStream(file);
      game = (Game) input.readObject();
    } finally {
      if (input != null) {
        input.close();
      }
      if (file != null) {
        file.close();
      }
    }
  }

  /**
   * Save this current game run.
   *
   * @throws IOException - In case of IO Errors
   */
  public void saveGame() throws IOException {
    File file = new File(savedGameUrl);
    FileOutputStream fileOutputStream = null;
    ObjectOutputStream output = null;

    try {
      if (!file.getParentFile().mkdirs()) {
        throw new IOException("La directory ./savings non è stato possibile crearla!");
      }
      if (!file.createNewFile()) {
        throw new IOException("Il file ./savings/saved_game.dat non è stato possibile crearlo!");
      }
      fileOutputStream = new FileOutputStream(file, false);
      output = new ObjectOutputStream(fileOutputStream);
      output.writeObject(game);    
    } finally {
      if (output != null) {
        output.close();
      }
      if (fileOutputStream != null) {
        fileOutputStream.close();
      }
    }
  }

  public void print() {
    game.getMap().printObject(game.getMap().getCurrentRoom());
  }

  /**
   * .
   *
   * @param args .
   */
  public static void main(String[] args) {
    GameHandler gh = new GameHandler();
    try {
      gh.newGame();
      gh.saveGame();
      gh.loadGame();
      gh.print();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // get stats
}

package dijkstra.dollhouse;

import dijkstra.dollhouse.engine.Game;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.GamePlayer;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.actions.GameScriptedAction;
import dijkstra.dollhouse.engine.levels.GameRoom;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * L'ActionListener avrà un gamehandler.
 * GameHadler dovrà essere interrogato dagli script
 * per ottenere le informazioni necesarie per la loro esecuzione.
 */
public final class GameHandler {
  private static final String initUrl = "./res/maps/piano_terra.json";
  private static final String savedGameUrl = "./res/savings/saved_game.dat";
  private static Game game = null;
  private static GameEntity currentEntity = null;
  private static GameAction currentAction = null;
  private static GameParser parser;
  // parser
  // stato attuale del comando

  public static void initParser() throws FileNotFoundException {
    parser = new GameParser();
  }

  /**
   * .
   *
   * @throws Exception .
   */
  public static void newGame() throws Exception {
    if (game == null) {
      game = new Game(initUrl, null);
    } else {
      throw new Exception("The game has already started.");
    }
  }

  public static Game getGame() {
    return game;
  }

  public static GameEntity getCurrentEntity() {
    return currentEntity;
  }

  public static GameAction getCurrentAction() {
    return currentAction;
  }

  public static String executeCommand(final String command) {
    ParsedInput parsedInput = parser.parse(command);
    GameRoom room = game.getMap().getCurrentRoom();
    String entityName = parsedInput.getEntity();
    String actionName = parsedInput.getAction();
    GamePlayer player = game.getPlayer();
    String output = "Comando inesistente!";

    // System.out.println(parsedInput);

    if (currentAction == null) {
      currentEntity = room.findEntity(entityName);
      if (currentEntity == null) {
        currentEntity = room.findBehavioralNpc(entityName);
      }

      if (currentEntity == null) {
        currentEntity = player.getGameInventory().findGameObject(entityName);
      }

      if (currentEntity == null) {
        currentAction = player.findAction(actionName);
      } else {
        currentAction = currentEntity.findAction(actionName);
      }
    }

    // System.out.println(currentEntity);

    if (currentAction instanceof GameScriptedAction) {
      ((GameScriptedAction) currentAction).execute();
      if (((GameScriptedAction) currentAction).isOver()) {
        output = currentAction.getOutput();
        currentAction = null;
        currentEntity = null;
      }
    } else {
      if (currentAction != null) {
        output = currentAction.getOutput();
      }
      currentAction = null;
      currentEntity = null;
    }

    return output + "\n";
    // return player.getName() + "> " + command + "\n" + output + "\n";
  }

  /**
   * Load a game run started yet.
   *
   * @throws Exception - ciao
   */
  public static void loadGame() throws Exception {
    FileInputStream file = null;
    ObjectInputStream input = null;

    if (game == null) {
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
    } else {
      throw new Exception("The game has already started.");
    }
  }

  /**
   * Save this current game run.
   *
   * @throws Exception .
   */
  public static void saveGame() throws Exception {
    File file = new File(savedGameUrl);
    FileOutputStream fileOutputStream = null;
    ObjectOutputStream output = null;

    if (game != null) {
      try {
        file.getParentFile().mkdirs();
        file.createNewFile();
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
    } else {
      throw new Exception("The game hasn't started yet");
    }
  }

  /**
   * .
   *
   * @param args .
   */
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    String input;
    try {
      GameHandler.newGame();
      GameHandler.saveGame();
      initParser();
      // GameHandler.loadGame();
      do {
        System.out.println(GameHandler.getGame().getMap().toString());
        System.out.print("\033[44m$ ");
        input = in.nextLine();
        // System.out.print("\033[0m");
        System.out.print(executeCommand(input));
      } while (input.compareTo("exit") != 0);
    } catch (Exception e) {
      e.printStackTrace();
    }

    in.close();
  }

  // get stats
}

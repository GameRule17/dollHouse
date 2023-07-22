package dijkstra.dollhouse;

import dijkstra.dollhouse.engine.Game;
import dijkstra.dollhouse.engine.GameParser;
import dijkstra.dollhouse.engine.ParsedInput;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.GamePlayer;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import dijkstra.dollhouse.engine.levels.GameRoom;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * L'ActionListener avrà un gamehandler.
 * GameHadler dovrà essere interrogato dagli script
 * per ottenere le informazioni necesarie per la loro esecuzione.
 */
public final class GameHandler {
  private static final String initUrl = "./res/maps/piano_seminterrato.json";
  private static final String savedGameUrl = "./res/savings/saved_game.dat";
  private static Game game = null;
  private static GameEntity currentEntity = null;
  private static GameAction currentAction = null;
  private static ParsedInput parsedInput = null;
  private static GameParser parser;

  public static void resetGame() {
    game = null;
  }

  public static void onOpen() throws FileNotFoundException {
    parser = new GameParser();
    game.getPlayer().getGameStatistics().getTimer().startTimer();
  }

  public static void onClose() throws Exception {
    game.getMap().stopAllBehavioralNpcs();
    game.getPlayer().getGameStatistics().getTimer().updateGameTime();
    saveGame();
  }

  private static void deleteDir(final File file) {
    File[] contents = file.listFiles();
    if (contents != null) {
      for (File f : contents) {
        deleteDir(f);
      }
    }
    file.delete();
  }

  /**
   * .
   *
   * @throws Exception .
   */
  public static void newGame() throws Exception {
    if (game == null) {
      game = new Game(initUrl, null);
      deleteDir(new File("./res/savings"));
    } else {
      throw new Exception("The game has already started.");
    }
  }

  public static Game getGame() {
    return game;
  }

  public static ParsedInput getParsedInput() {
    return parsedInput;
  }

  public static GameEntity getCurrentEntity() {
    return currentEntity;
  }

  public static GameAction getCurrentAction() {
    return currentAction;
  }

  /**
   * .
   *
   * @param command .
   * @return .
   * @throws Exception .
   */
  public static String executeCommand(final String command) throws Exception {
    parsedInput = parser.parse(command);
    GameRoom room = game.getMap().getCurrentRoom();
    String entityName = parsedInput.getEntity();
    String actionName = parsedInput.getAction();
    GamePlayer player = game.getPlayer();
    String output = "Questa azione non può essere effettuata!";
    Exception exception;

    player.getGameStatistics().incrementsNumberOfExecutedActions();

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

    if (currentAction instanceof GameScript) {
      output = ((GameScript) currentAction).execute();
      exception = ((GameScript) currentAction).getException();
      if (exception != null) {
        throw exception;
      }
      if (((GameScript) currentAction).isOver()) {
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

    System.out.println(player.getGameStatistics().toString());
    System.out.println(room.getAdjacentRooms());
    return "\n" + room.getName() + ":" + player.getName()
            + "~$" + command + "\n" + output + "\n";
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
      } catch (Exception e) {
        // e.printStackTrace();
        throw e;
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
}

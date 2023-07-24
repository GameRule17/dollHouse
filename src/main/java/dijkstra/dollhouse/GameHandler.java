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
import java.util.List;
import java.util.Scanner;

/**
 * Static class in order to manage GUI-Engine integration.
 */
public final class GameHandler {
  private static final String initUrl = "./res/maps/piano_seminterrato.json";
  private static final String savedGameUrl = "./res/savings/saved_game.dat";
  private static Game game = null;
  private static GameEntity currentEntity = null;
  private static GameAction currentAction = null;
  private static ParsedInput parsedInput = null;
  private static GameParser parser;
  private static boolean isEnded = false;

  public static void endGame() throws InterruptedException {
    isEnded = true;
  }

  public static void resetGame() {
    game = null;
  }

  /**
   * Initialize the GameHandler in order to play the game.
   *
   * @throws FileNotFoundException if the stopwords file is not found.
   */
  public static void onOpen() throws FileNotFoundException {
    parser = new GameParser();
    game.getPlayer().getGameStatistics().getTimer().startTimer();
    game.getMap().runAllBehavioralNpcs();
  }

  /**
   * Performs all needed operations before closing the game.
   *
   * @throws Exception exceptions during the operation.
   */
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
   * It starts a new game.
   *
   * @throws Exception exceptions during the execution.
   */
  public static void newGame() throws Exception {
    if (game == null) {
      isEnded = false;
      game = new Game(initUrl, null);
      deleteDir(new File("./res/savings"));
      File file = new File("./res/initialOutput.txt");
      Scanner scanner = new Scanner(file);
      while (scanner.hasNextLine()) {
        GuiHandler.print(scanner.nextLine() + "\n");
      }
      scanner.close();
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
   * Updates the inventory GUI adding all objects in the player inventory.
   */
  public static void updateInventoryGui() {
    List<GameEntity> inventory = game.getPlayer().getGameInventory().getObjects();
    for (GameEntity gameEntity : inventory) {
      GuiHandler.addInventory(gameEntity.getName());
    }
  }

  /**
   * This method executes the command inserted by the player.
   * It returns the output of the command to be printed.
   * If the flag isEnded equals true, this method returns the player to the menu.
   *
   * @param command - the command to executes.
   * @return String - the output of the command to print.
   * @throws Exception if exceptions occurs during the execution.
   */
  public static String executeCommand(final String command) throws Exception {
    parsedInput = parser.parse(command);
    GameRoom room = game.getMap().getCurrentRoom();
    String entityName = parsedInput.getEntity();
    String actionName = parsedInput.getAction();
    GamePlayer player = game.getPlayer();
    String output = "Questa azione non può essere effettuata!";
    Exception exception;

    if (isEnded) {
      GuiHandler.popUpMessage("Complimenti! Hai finito Dollhouse!\n"
          + "Per vedere le tue statistiche di gioco torna al menu principale"
          + " e clicca sulla voce \"Statistiche\".");
      GuiHandler.returnMenu();
      GameHandler.resetGame();
      MusicPlayer.stopMusic();
      MusicPlayer.playMusic("./res/songs/menuStart.mp3");
      return null;
    }

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
    return "\n[" + room.getName() + "] " + player.getName()
            + " > " + command + "\n" + output + "\n";
  }

  /**
   * Load a game run started yet.
   *
   * @throws Exception Any of the usual Input/Output related exceptions.
   */
  public static void loadGame() throws Exception {
    FileInputStream file = null;
    ObjectInputStream input = null;

    if (game == null) {
      isEnded = false;
      try {
        file = new FileInputStream(new File(savedGameUrl));
        input = new ObjectInputStream(file);
        game = (Game) input.readObject();
      } catch (Exception e) {
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
   * @throws Exception Any of the usual Input/Output related exceptions..
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

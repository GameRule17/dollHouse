package dijkstra.dollhouse.engine.entities.actions.predefined;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

import org.json.simple.parser.ParseException;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.actions.GameScriptedAction;
import dijkstra.dollhouse.engine.levels.GameMap;
import dijkstra.dollhouse.engine.levels.GameRoom;

/**
 * .
 */
public class ChangeMap extends GameScriptedAction {

  private Exception exception;

  public ChangeMap(final String output) {
    super(output);
    exception = null;
  }

  /**
   * Save this current game run.
   *
   * @throws Exception .
   */
  private void saveMap() throws Exception {
    final String url = "./res/savings/" + GameHandler.getGame().getMap().getName() + ".dat";
    File file = new File(url);
    FileOutputStream fileOutputStream = null;
    ObjectOutputStream output = null;

    try {
      file.getParentFile().mkdirs();
      file.createNewFile();
      fileOutputStream = new FileOutputStream(file, false);
      output = new ObjectOutputStream(fileOutputStream);
      output.writeObject(GameHandler.getGame().getMap());    
    } finally {
      if (output != null) {
        output.close();
      }
      if (fileOutputStream != null) {
        fileOutputStream.close();
      }
    }
  }

  private void loadMap(final String input)
                      throws ClassNotFoundException, NoSuchMethodException,
                      InstantiationException, IllegalAccessException,
                      IllegalArgumentException, InvocationTargetException,
                      IOException, ParseException {
    String[] tokens = input.split("/");
    String url = "./res/savings/" + tokens[tokens.length - 1].replaceAll(".json", ".dat");
    File f = new File(url);
    if (f.exists() && !f.isDirectory()) { 
      FileInputStream file = new FileInputStream(new File(url));
      ObjectInputStream inputFile = new ObjectInputStream(file);
      GameHandler.getGame().setMap((GameMap) inputFile.readObject());
    } else {
      GameHandler.getGame().setMap(input);
    }
  }

  @Override
  public String execute() {
    GameEntity entity = GameHandler.getCurrentEntity();
    String[] input = entity.getName().split("\\s+");
    String output = null;
  
    try {
      saveMap();
      GameHandler.getGame().getMap().stopAllBehavioralNpcs();
      loadMap(input[0]);
      
      GameHandler.getGame().getMap().runAllBehavioralNpcs();
      GameRoom room = GameHandler.getGame().getMap().getRoom(input[1]);
      if (room != null) {
        GameHandler.getGame().getMap().setCurrentRoom(room);
        exception = null;
        output = room.getDescription();
      } else {
        exception = new Exception("The room named " + entity.getName() + " does not exist!");
      }
    } catch (Exception e) {
      exception = e;
    }
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

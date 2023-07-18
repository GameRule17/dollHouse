package dijkstra.dollhouse.engine.entities;

import dijkstra.dollhouse.engine.JSONLoader;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.details.GameInventory;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * An istance of a player represent a player in a game.
 * A player has an inventory and its game statistics liked
 * to its game run.
 */
public class GamePlayer extends GameEntity {
  private static final String actionsUrl = "./res/actions/player_actions.json";
  private GameInventory inventory;
  // statistiche di gioco

  /**
   * Public constructor for GamePlayer.
   *
   * @param name - sets the player name.
   * @throws InvocationTargetException . 
   * @throws IllegalArgumentException .
   * @throws IllegalAccessException .
   * @throws InstantiationException .
   * @throws NoSuchMethodException .
   * @throws ClassNotFoundException .
   * @throws IOException .
   * @throws ParseException .
   */
  public GamePlayer(final String name)
                    throws ClassNotFoundException, NoSuchMethodException,
                    InstantiationException, IllegalAccessException,
                    IllegalArgumentException, InvocationTargetException,
                    IOException, ParseException {
    super(name);
    inventory = new GameInventory();
    FileReader fileReader = null;
    try {
      fileReader = new FileReader(actionsUrl, Charset.defaultCharset());
      JSONParser parser = new JSONParser();
      JSONObject json = (JSONObject) parser.parse(fileReader);
      JSONArray jsonActions = (JSONArray) json.get("actions");

      for (Object object : jsonActions) {
        actions.add(JSONLoader.getGameAction((JSONObject) object));
      }
    } catch (Exception e) {
      throw e;
    } finally {
      if (fileReader != null) {
        fileReader.close();
      }
    }
  }

  public GameInventory getGameInventory() {
    return inventory;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("Name: " + name + "\n");
    s.append("Aliases: ");
    for (String alias : aliases) {
      s.append(alias + " ");
    }
    // s.append("\nRecipe: " + recipe.toString() + "\n");
    for (GameAction action : actions) {
      s.append(action.toString());
    }
    return s.toString();
  }

  // public static void main(String[] args) {
  //   try {
  //     GamePlayer j = new GamePlayer("nico");
  //     System.out.println(j.toString());
  //   } catch (Exception e) {
  //     e.printStackTrace();
  //   }
  // }
}
package dijkstra.dollhouse.engine.entities;

import dijkstra.dollhouse.engine.JsonLoader;
import dijkstra.dollhouse.engine.entities.details.GameInventory;
import dijkstra.dollhouse.engine.entities.details.GameStatistics;
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
  private GameStatistics statistics;

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
        actions.add(JsonLoader.getGameAction((JSONObject) object));
      }

      statistics = new GameStatistics();
    } catch (Exception e) {
      throw e;
    } finally {
      if (fileReader != null) {
        fileReader.close();
      }
    }
  }

  public GameStatistics getGameStatistics() {
    return statistics;
  }

  public GameInventory getGameInventory() {
    return inventory;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("Name: " + name + "\n");
    s.append("Aliases: ");
    
    aliases.stream().forEach(alias -> s.append(alias + " "));
    s.append("\nActions:\n");
    actions.stream().forEach(action -> s.append(action.toString()));

    s.append("\n" + statistics.toString());
    return s.toString();
  }
}

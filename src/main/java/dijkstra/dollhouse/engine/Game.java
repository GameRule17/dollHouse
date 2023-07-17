package dijkstra.dollhouse.engine;

import dijkstra.dollhouse.engine.entities.GamePlayer;
import dijkstra.dollhouse.engine.levels.GameMap;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import org.json.simple.parser.ParseException;

/**
 * An istance of Game represents a game run of a player.
 * It has a map in which the player has played and the istance
 * of the player. It can be saved/load in/by a file.
 */
public class Game implements Serializable {
  private GameMap map;
  private GamePlayer player;

  public Game(final String mapUrl, final String playerName)
              throws ClassNotFoundException, NoSuchMethodException,
              InstantiationException, IllegalAccessException,
              IllegalArgumentException, InvocationTargetException,
              IOException, ParseException {
    map = new GameMap(mapUrl);
    player = new GamePlayer(playerName);
  }

  public void setMap(final String mapUrl)
                    throws ClassNotFoundException, NoSuchMethodException,
                    InstantiationException, IllegalAccessException,
                    IllegalArgumentException, InvocationTargetException,
                    IOException, ParseException {
    map = new GameMap(mapUrl);
  }

  public GameMap getMap() {
    return map;
  }

  public GamePlayer getPlayer() {
    return player;
  }

}

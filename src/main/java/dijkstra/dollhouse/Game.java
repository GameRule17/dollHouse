package dijkstra.dollhouse;

import dijkstra.dollhouse.entities.GamePlayer;
import dijkstra.dollhouse.levels.GameMap;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import org.json.simple.parser.ParseException;

/**
 * Manages all about a player run of the game.
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

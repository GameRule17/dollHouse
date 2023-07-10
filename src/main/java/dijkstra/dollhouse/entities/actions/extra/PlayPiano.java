package dijkstra.dollhouse.entities.actions.extra;

import dijkstra.dollhouse.entities.GameEntity;
import dijkstra.dollhouse.entities.actions.InteractGameAction;
import dijkstra.dollhouse.levels.GameMap;

/**
 * .
 */
public class PlayPiano extends InteractGameAction {

  public PlayPiano(String output) {
    super(output);
  }

  @Override
  public String execute(GameMap map, GameEntity target) {
    System.out.println("Hello world from PlayPiano!");
    return null;
  }
    
}

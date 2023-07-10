package dijkstra.dollhouse.entities.actions;

import dijkstra.dollhouse.entities.GameEntity;
import dijkstra.dollhouse.levels.GameMap;

/**
 * .
 */
public abstract class InteractGameAction extends GameAction {

  public InteractGameAction(String output) {
    super(output);
  }
  
  public abstract String execute(final GameMap map, final GameEntity target);

}

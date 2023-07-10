package dijkstra.dollhouse.entities.actions.predefined;

import dijkstra.dollhouse.entities.GameEntity;
import dijkstra.dollhouse.entities.actions.InteractGameAction;
import dijkstra.dollhouse.levels.GameMap;

/**
 * .
 */
public class PickItem extends InteractGameAction {

  public PickItem(String output) {
    super(output);
  }

  @Override
  public String execute(GameMap map, GameEntity target) {
    System.out.println("Hello world from PickItem!");
    return null;
  }
    
}

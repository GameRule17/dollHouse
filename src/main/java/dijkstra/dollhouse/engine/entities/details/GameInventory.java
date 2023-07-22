package dijkstra.dollhouse.engine.entities.details;

import dijkstra.dollhouse.engine.entities.GameEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An istance of GameInventory represents a player inventory.
 */
public class GameInventory implements Serializable {
  private List<GameEntity> inventory;

  public GameInventory() {
    inventory = new ArrayList<>();
  }

  public List<GameEntity> getObjects() {
    return inventory;
  }

  /**
   * Finds a GameObject with the name specified by "name" argument.
   * If this GameObject does not exist, returns null
   *
   * @param name - the name of the GameObject to find
   * @return - the GameObject to find or null instead
   */
  public GameEntity findGameObject(final String name) {
    Iterator<GameEntity> iterator = inventory.iterator();
    GameEntity object;
    while (iterator.hasNext()) {
      object = iterator.next();
      if (object.isAliasOf(name)) {
        return object;
      }
    }
    return null;
  }

  public void add(final GameEntity object) {
    inventory.add(object);
  }

  public void remove(final GameEntity object) {
    inventory.remove(object);
  }
}

package dijkstra.dollhouse.entities;

import dijkstra.dollhouse.entities.actions.GameAction;

/**
 * Class for representing game Objects.
 */
public class GameObject extends GameEntity {
  private boolean craftable;

  /**
   * Constructor.

   * @param name - sets the name of the object
   * @param craftable - sets if this object can be used to craft another object
   */
  public GameObject(final String name, final boolean craftable) {
    super(name);
    this.craftable = craftable;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("Name: " + name + "\n");
    s.append("Aliases: ");
    for (String alias : aliases) {
      s.append(alias + " ");
    }
    s.append("\nCraftable: " + craftable + "\n");
    for (GameAction action : actions) {
      s.append(action.toString());
    }
    s.append("\n");
    return s.toString();
  }
}

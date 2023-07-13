package dijkstra.dollhouse.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for representing game Objects.
 */
public class GameCraftableObject extends GameEntity {
  private List<String> recipe;

  /**
   * Constructor.

   * @param name - sets the name of the object
   */
  public GameCraftableObject(final String name) {
    super(name);
    recipe = new ArrayList<>();
  }

  public void addToRecipe(final String item) {
    recipe.add(item);
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder(super.toString());
    s.append("\nRecipe: " + recipe.toString() + "\n");
    return s.toString();
  }
}

package dijkstra.dollhouse.engine.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * All istances of GameCraftableObject represent objects in game that can be crafted
 * with other objects in the player inventory. These objects must have a recipe.
 * A recipe is a List of String aliases of all the objects needed.
 */
public class GameCraftableObject extends GameEntity {
  private List<String> recipe;

  /**
   * Public constructor for GameCraftableObject.
   * Here the recipe is initialized with an empty list.
   *
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

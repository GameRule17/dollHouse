package dijkstra.dollhouse.entities;

import dijkstra.dollhouse.entities.actions.GameAction;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class for representing Entities in game (like NPCs, Objects . . .).
 */
public abstract class GameEntity {

  protected String name;
  protected Collection<GameAction> actions;
  protected Collection<String> aliases;

  /**
   * .

   * @param name .
   */
  public GameEntity(final String name) {
    this.name = name;
    this.actions = new ArrayList<>();
    this.aliases = new ArrayList<>();
  }

  public void addAction(final GameAction action) {
    actions.add(action);
  }

  public void addAlias(final String alias) {
    aliases.add(alias);
  }

  public Collection<GameAction> getActions() {
    return actions;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
  
}

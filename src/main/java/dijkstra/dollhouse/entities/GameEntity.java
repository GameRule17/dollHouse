package dijkstra.dollhouse.entities;

import dijkstra.dollhouse.entities.actions.GameAction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Class for representing Entities in game (like NPCs, Objects . . .).
 */
public abstract class GameEntity implements Serializable {

  protected String name;
  protected Collection<GameAction> actions;
  protected Collection<String> aliases;

  /**
   * Constructor for GameEnetity.
   *
   * @param name - sets the name of this entity.
   */
  public GameEntity(final String name) {
    this.name = name;
    this.actions = new ArrayList<>();
    this.aliases = new ArrayList<>();
  }

  /**
   * Finds an Action with the name specified by "name" argument.
   * If this Action does not exist, returns null
   *
   * @param name - the name of the action to find
   * @return - the action to find or null instead
   */
  public GameAction findAction(final String name) {
    Iterator<GameAction> iterator = actions.iterator();
    GameAction action;
    while (iterator.hasNext()) {
      action = iterator.next();
      if (action.isAliasOf(name)) {
        return action;
      }
    }
    return null;
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

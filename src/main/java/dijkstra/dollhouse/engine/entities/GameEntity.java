package dijkstra.dollhouse.engine.entities;

import dijkstra.dollhouse.engine.entities.actions.GameAction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * An istance of GameEntity represents all possible entities in game
 * like npcs, objects and players.
 * All entities have a name and a collection of aliases that can be used to
 * indentify the entity.
 * All entities have a collection of actions that can be performed on them.
 */
public class GameEntity implements Serializable {

  protected String name;
  protected Collection<GameAction> actions;
  protected Collection<String> aliases;

  /**
   * Public constructor for GameEntity.
   * Here the collection of actions and aliases are
   * initialized as empty collections.
   *
   * @param name - sets the name of this entity.
   */
  public GameEntity(final String name) {
    this.name = name;
    this.actions = new ArrayList<>();
    this.aliases = new ArrayList<>();
  }

  /**
   * Get the alias at position "position".
   *
   * @param position - the position of the alias
   * @return String
   */
  public String getAlias(final int position) {
    Iterator<String> it = aliases.iterator();
    int i = 0;
    while (it.hasNext() && i < position) {
      it.next();
    }
    return it.next();
  }

  /**
   * Checks if the parameter is an alias of this GameAction object.
   *
   * @param alias - the alias to check
   * @return true if is an alias, false otherwise
   */
  public boolean isAliasOf(final String alias) {
    for (String string : aliases) {
      if (string.compareToIgnoreCase(alias) == 0) {
        return true;
      }
    }
    return false;
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

  public void removeAction(final GameAction action) {
    actions.remove(action);
  }

  public void addAlias(final String alias) {
    aliases.add(alias);
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("Name: " + name + "\n");
    s.append("Aliases: ");
    aliases.stream().forEach(alias -> s.append(alias + " "));
    s.append("\nActions:\n");
    actions.stream().forEach(action -> s.append("\n" + action.toString()));
    return s.toString();
  }
  
}

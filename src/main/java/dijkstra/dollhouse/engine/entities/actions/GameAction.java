package dijkstra.dollhouse.engine.entities.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * An istance of GameAction represents one simple action to be performed in game.
 * All GameAction istances have a Collection of aliases representing
 * all possible names for the action represented by this istance.
 * Aliases are used to identify an action.
 * The output of an action corresponds to the output returned after the execution.
 */
public class GameAction implements Serializable {
  private Collection<String> aliases;
  protected String output;

  /**
   * GameAction constructor.
   * Here the collection of aliases is initialized.
   *
   * @param output - the string representing the output of the action execution
   */
  public GameAction(final String output) {
    this.aliases = new ArrayList<>();
    this.output = output;
  }

  public void addAlias(final String alias) {
    aliases.add(alias);
  }

  /**
   * Checks if the parameter is an alias of this GameAction object.
   *
   * @param alias - the alias to check
   * @return true if "alias" is an alias of this istance, false otherwise
   */
  public boolean isAliasOf(final String alias) {
    for (String string : aliases) {
      if (string.compareToIgnoreCase(alias) == 0) {
        return true;
      }
    }
    return false;
  }

  public String getOutput() {
    return output;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("Aliases: ");
    // for (String string : aliases) {
    //   s.append(string + " ");
    // }
    aliases.stream().forEach(alias -> s.append(alias + " "));
    return s.toString();
  }
}

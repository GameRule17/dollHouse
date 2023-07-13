package dijkstra.dollhouse.entities.actions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Class for represents all simple Actions in the game.
 */
public class GameAction implements Serializable {
  private Collection<String> aliases;
  protected String output;

  /**
   * GameAction constructor.
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

   * @param alias - the alias to check
   * @return true if is an alias, false otherwise
   */
  public boolean isAliasOf(final String alias) {
    for (String string : aliases) {
      if (string.equals(alias)) {
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
    for (String string : aliases) {
      s.append(string + " ");
    }
    s.append("\nOutput: " + output);
    return s.toString();
  }
}

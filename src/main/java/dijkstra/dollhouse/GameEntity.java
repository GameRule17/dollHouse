package dijkstra.dollhouse;

/**
 * Class for representing Entities in game (like NPCs, Objects . . .).
 */
public abstract class GameEntity {
  public static final int NUMBER_OF_ACTIONS = 4;

  private String name;
  // aliases

  private String[] actionsOutput;

  /**
   * Enum for all possible actions to be performed on entities (DA RIVEDERE).
   */
  public enum Action {
    EXAMINE, INTERACT, PICK, DROP
  }

  public GameEntity(final String name, final String[] actionsOutput) {
    this.name = name;
    this.actionsOutput = actionsOutput;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setActionOutput(final Action action, final String actionOutput) {
    actionsOutput[action.ordinal()] = actionOutput;
  }

  public String getActionOutput(final Action action) {
    return actionsOutput[action.ordinal()];
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("Nome: " + name + "\n");
    for (int i = 0; i <  NUMBER_OF_ACTIONS; i++) {
      s.append(Action.values()[i] + ": " + actionsOutput[i] + "\n");
    }
    s.append("\n");
    return s.toString();
  }
}

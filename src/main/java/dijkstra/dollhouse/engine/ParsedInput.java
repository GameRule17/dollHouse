package dijkstra.dollhouse.engine;

/**
 * Class that represents the parsed input.
 * It has an attribute called action that represents the action to be performed
 * and an attribute called entity that represents the entity on which perform the action.
 */
public class ParsedInput {
  private String action;
  private String entity;

  public ParsedInput(final String action, final String entity) {
    this.action = action;
    this.entity = entity;
  }

  public String getAction() {
    return action;
  }

  public String getEntity() {
    return entity;
  }

  public String toString() {
    return "[" + action + "," + entity + "]";
  }

}

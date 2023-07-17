package dijkstra.dollhouse;

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

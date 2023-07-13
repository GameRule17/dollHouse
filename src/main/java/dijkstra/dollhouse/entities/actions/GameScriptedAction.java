package dijkstra.dollhouse.entities.actions;

/**
 * .
 */
public abstract class GameScriptedAction extends GameAction implements GameScript {

  public GameScriptedAction(String output) {
    super(output);
  }

  @Override
  public String toString() {
    return super.toString() + "\n\033[32mClass: " + this.getClass().getName() + "\033[0m";
  }

}

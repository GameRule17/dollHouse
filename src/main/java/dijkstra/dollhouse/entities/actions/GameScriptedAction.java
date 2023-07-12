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
    return super.toString() + "\nClass: " + this.getClass().getName();
  }

}

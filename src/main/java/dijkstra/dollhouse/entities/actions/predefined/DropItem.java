package dijkstra.dollhouse.entities.actions.predefined;

import dijkstra.dollhouse.Game;
import dijkstra.dollhouse.entities.actions.GameScriptedAction;

/**
 * .
 */
public class DropItem extends GameScriptedAction {

  public DropItem(String output) {
    super(output);
  }

  @Override
  public boolean execute(final Game game) {
    System.out.println("Hello world from DropItem!");
    return true;
  }

  @Override
  public boolean isOver() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isOver'");
  }

  @Override
  public Exception getException() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getException'");
  }
    
}

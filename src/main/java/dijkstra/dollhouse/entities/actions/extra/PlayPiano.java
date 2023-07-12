package dijkstra.dollhouse.entities.actions.extra;

import dijkstra.dollhouse.Game;
import dijkstra.dollhouse.entities.actions.GameScriptedAction;

/**
 * .
 */
public class PlayPiano extends GameScriptedAction {

  public PlayPiano(String output) {
    super(output);
  }

  @Override
  public boolean execute(final Game game) {
    System.out.println("Hello world from PlayPiano!");
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

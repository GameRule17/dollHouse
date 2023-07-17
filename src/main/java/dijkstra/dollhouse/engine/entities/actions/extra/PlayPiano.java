package dijkstra.dollhouse.engine.entities.actions.extra;

import dijkstra.dollhouse.engine.entities.actions.GameScriptedAction;

/**
 * .
 */
public class PlayPiano extends GameScriptedAction {

  public PlayPiano(String output) {
    super(output);
  }

  @Override
  public String execute() {
    return null;
  }

  @Override
  public boolean isOver() {
    throw new UnsupportedOperationException("Unimplemented method 'isOver'");
  }

  @Override
  public Exception getException() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getException'");
  }
    
}

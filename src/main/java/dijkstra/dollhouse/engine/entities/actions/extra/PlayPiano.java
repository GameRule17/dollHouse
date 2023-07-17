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
  public void execute() {
  }

  @Override
  public boolean isOver() {
    throw new UnsupportedOperationException("Unimplemented method 'isOver'");
  }
    
}

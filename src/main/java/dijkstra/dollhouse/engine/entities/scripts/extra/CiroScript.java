package dijkstra.dollhouse.engine.entities.scripts.extra;

import java.io.Serializable;

import dijkstra.dollhouse.engine.entities.scripts.GameScript;

/**
 * .
 */
public class CiroScript implements GameScript, Serializable {

  public CiroScript() {
    
  }

  @Override
  public String execute() {
    return "CIROOOO";
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

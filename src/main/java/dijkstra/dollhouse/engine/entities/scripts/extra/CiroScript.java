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
  public void execute() {
    System.out.println("CIROOOOO");
  }

  @Override
  public boolean isOver() {
    throw new UnsupportedOperationException("Unimplemented method 'isOver'");
  }

  @Override
  public String getOutput() {
    throw new UnsupportedOperationException("Unimplemented method 'getOutput'");
  }
    
}

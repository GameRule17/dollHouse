package dijkstra.dollhouse.engine.entities.scripts;

/**
 * All possible scripts/behaviors of the game must implement GameScript in order
 * to be executed correctly by the engine. 
 */
public interface GameScript {

  public abstract String execute();

  public abstract boolean isOver();

  // public abstract String getOutput();

  public abstract Exception getException();

}

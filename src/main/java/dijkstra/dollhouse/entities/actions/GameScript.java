package dijkstra.dollhouse.entities.actions;

import dijkstra.dollhouse.Game;

/**
 * . 
 */
public interface GameScript {

  public abstract boolean execute(final Game game);

  public abstract boolean isOver();

  public abstract String getOutput();

  public abstract Exception getException();

}

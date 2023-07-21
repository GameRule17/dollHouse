package dijkstra.dollhouse.engine.entities.actions.predefined;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import dijkstra.dollhouse.engine.levels.GameRoom;

/**
 * .
 */
public class ChangeRoom extends GameAction implements GameScript {

  private Exception exception;

  public ChangeRoom(final String output) {
    super(output);
    exception = null;
  }

  @Override
  public String execute() {
    GameEntity entity = GameHandler.getCurrentEntity();
    GameRoom room = GameHandler.getGame().getMap().getCurrentRoom()
                              .getAdjacentRoom(entity.getName());
    String output = null;
    if (room != null) {
      GameHandler.getGame().getMap().setCurrentRoom(room);
      exception = null;
      output = room.getDescription();
    } else {
      exception = new Exception("The room named " + entity.getName() + " does not exist!");
    }
    return output;
  }

  @Override
  public boolean isOver() {
    return true;
  }

  @Override
  public Exception getException() {
    return exception;
  }
}

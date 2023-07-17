package dijkstra.dollhouse.engine.entities.actions.predefined;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.actions.GameScriptedAction;
import dijkstra.dollhouse.engine.levels.GameRoom;

/**
 * .
 */
public class ChangeMap extends GameScriptedAction {

  private Exception exception;

  public ChangeMap(final String output) {
    super(output);
    exception = null;
  }

  @Override
  public String execute() {
    GameEntity entity = GameHandler.getCurrentEntity();
    String[] input = entity.getName().split("\\s+");
    String output = null;
    try {
      GameHandler.getGame().setMap(input[0]);
      GameRoom room = GameHandler.getGame().getMap().getRoom(input[1]);
      if (room != null) {
        GameHandler.getGame().getMap().setCurrentRoom(room);
        exception = null;
        output = room.getDescription();
      } else {
        exception = new Exception("The room named " + entity.getName() + " does not exist!");
      }
    } catch (Exception e) {
      exception = e;
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

package dijkstra.dollhouse.engine.entities.actions.predefined;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import dijkstra.dollhouse.engine.levels.GameRoom;

/**
 * The script that allows the player to examine the current room.
 */
public class ExamineScript extends GameAction implements GameScript {

  public ExamineScript(String output) {
    super(output);
  }

  @Override
  public String execute() {
    GameRoom room = GameHandler.getGame().getMap().getCurrentRoom();
    String output = room.getDescription() + "\nPuoi notare le seguenti entita':";
    String name;
    for (GameEntity entity : room.getEntities()) {
      name = entity.getName();
      if (name.charAt(0) != '.') {
        output += "\n- " + name;
      } else {
        output += "\n- " + entity.getAlias(0);
      }
    }
    for (GameEntity entity : room.getGameBehavioralNpc()) {
      output += "\n- " + entity.getName();
    }

    return output;
  }

  @Override
  public boolean isOver() {
    return true;
  }

  @Override
  public Exception getException() {
    return null;
  }
  
}

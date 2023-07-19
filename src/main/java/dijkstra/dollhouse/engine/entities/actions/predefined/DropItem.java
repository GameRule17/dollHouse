package dijkstra.dollhouse.engine.entities.actions.predefined;

import dijkstra.dollhouse.GUIHandler;
import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.actions.GameScriptedAction;
import dijkstra.dollhouse.engine.entities.details.GameInventory;
import dijkstra.dollhouse.engine.levels.GameRoom;

/**
 * GameScriptedAction that remove the entity from the inventory of the player
 * and add it in the current room.
 */
public class DropItem extends GameScriptedAction {

  private boolean isExecutable;

  public DropItem(final String output) {
    super(output);
    isExecutable = false;
  }

  public void setExecutable(final boolean executable) {
    isExecutable = executable;
  }

  @Override
  public String execute() {
    GameEntity entity = GameHandler.getCurrentEntity();
    GameRoom room = GameHandler.getGame().getMap().getCurrentRoom();
    GameInventory inventory = GameHandler.getGame().getPlayer().getGameInventory();
    GameAction pick;
    String output;

    if (isExecutable) {
      inventory.remove(entity);
      room.addEntity(entity);
      pick = entity.findAction("prendi");
      if (pick instanceof PickItem) {
        ((PickItem) pick).setExecutable(true);
      }
      isExecutable = false;
      output = this.output;
      GUIHandler.removeInventory(entity.getName());
    } else {
      output = "L'entita' non è nel tuo inventario!";
    }

    return output;
  }

  @Override
  public String getOutput() {
    return this.output;
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

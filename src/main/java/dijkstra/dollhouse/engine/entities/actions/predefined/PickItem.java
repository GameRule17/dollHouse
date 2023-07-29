package dijkstra.dollhouse.engine.entities.actions.predefined;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.GuiHandler;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.details.GameInventory;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import dijkstra.dollhouse.engine.levels.GameRoom;

/**
 * GameScriptedAction that remove the entity from the current room
 * and add it in the player inventory.
 */
public class PickItem extends GameAction implements GameScript {

  private boolean isExecutable;

  public PickItem(final String output) {
    super(output);
    isExecutable = true;
  }

  public void setExecutable(final boolean executable) {
    isExecutable = executable;
  }

  @Override
  public String execute() {
    GameEntity entity = GameHandler.getCurrentEntity();
    GameRoom room = GameHandler.getGame().getMap().getCurrentRoom();
    GameInventory inventory = GameHandler.getGame().getPlayer().getGameInventory();
    String output;
    GameAction drop;

    if (isExecutable) {
      room.removeEntity(entity);
      inventory.add(entity);
      drop = entity.findAction("lascia");
      if (drop instanceof DropItem) {
        ((DropItem) drop).setExecutable(true);
      }
      isExecutable = false;
      output = this.output;
      GuiHandler.addInventory(entity.getName());
    } else {
      output = "Questa entita' e' gia' presente nell'inventario!";
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

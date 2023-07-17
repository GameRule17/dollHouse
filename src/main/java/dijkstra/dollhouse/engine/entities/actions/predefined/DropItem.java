package dijkstra.dollhouse.engine.entities.actions.predefined;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.actions.GameScriptedAction;
import dijkstra.dollhouse.engine.entities.details.GameInventory;
import dijkstra.dollhouse.engine.levels.GameRoom;

/**
 * GameScriptedAction that remove the entity from the inventory of the player
 * and add it in the current room.
 */
public class DropItem extends GameScriptedAction {

  

  public DropItem(final String output) {
    super(output);
  }

  @Override
  public void execute() {
    GameEntity entity = GameHandler.getCurrentEntity();
    GameRoom room = GameHandler.getGame().getMap().getCurrentRoom();
    GameInventory inventory = GameHandler.getGame().getPlayer().getGameInventory();
    PickItem pick = new PickItem("L'oggetto è stato messo nell'inventario!");
    pick.addAlias("prendi");

    inventory.remove(entity);
    room.addEntity(entity);

    entity.removeAction(GameHandler.getCurrentAction());
    entity.addAction(pick);
  }

  @Override
  public String getOutput() {
    return this.output;
  }

  @Override
  public boolean isOver() {
    return true;
  }
}

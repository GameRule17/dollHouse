package dijkstra.dollhouse.engine.entities.actions.predefined;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.actions.GameScriptedAction;
import dijkstra.dollhouse.engine.entities.details.GameInventory;
import dijkstra.dollhouse.engine.levels.GameRoom;

/**
 * GameScriptedAction that remove the entity from the current room
 * and add it in the player inventory.
 */
public class PickItem extends GameScriptedAction {
  public PickItem(final String output) {
    super(output);
  }

  @Override
  public void execute() {
    GameEntity entity = GameHandler.getCurrentEntity();
    GameRoom room = GameHandler.getGame().getMap().getCurrentRoom();
    GameInventory inventory = GameHandler.getGame().getPlayer().getGameInventory();
    DropItem drop = new DropItem("L'oggetto è stato lasciato in questa stanza!");
    drop.addAlias("lascia");

    room.removeEntity(entity);
    inventory.add(entity);

    entity.removeAction(GameHandler.getCurrentAction());
    entity.addAction(drop);
  }

  @Override
  public boolean isOver() {
    return true;
  }
}

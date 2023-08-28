package dijkstra.dollhouse.engine.entities.actions.extra;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.JsonLoader;
import dijkstra.dollhouse.engine.entities.GameEntity;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import dijkstra.dollhouse.engine.levels.GameRoom;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Script for the mask in the game.
 */
public class MaskScript extends GameAction implements GameScript {

  private Exception exception;

  public MaskScript(final String output) {
    super(output);
    exception = null;
  }

  private GameEntity getTrapdoor() {
    FileReader fileReader = null;
    GameEntity trapdoor = null;
    try {
      fileReader = new FileReader("./res/objects/trapdoor.json", Charset.defaultCharset());
      JSONParser parser = new JSONParser();
      JSONObject jsonTrapdoor = (JSONObject) parser.parse(fileReader);
      trapdoor = JsonLoader.getGameEntity(jsonTrapdoor);
    } catch (IOException | ParseException | ClassNotFoundException | NoSuchMethodException
            | InstantiationException | IllegalAccessException | IllegalArgumentException
            | InvocationTargetException e) {
      exception = e;
    }
    return trapdoor;
  }

  @Override
  public String execute() {
    GameRoom room = GameHandler.getGame().getMap().getCurrentRoom();
    GameEntity mask;
    room.removeEntity(room.findEntity("Botola"));
    room.addEntity(getTrapdoor());
    mask = GameHandler.getGame().getPlayer()
              .getGameInventory().findGameObject("Maschera");
    if (mask != null) {
      mask.removeAction(this);
    } else {
      return "Cerchi di indossare la maschera ma ti accorgi di non averla mai presa!";
    }
    return this.output;
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

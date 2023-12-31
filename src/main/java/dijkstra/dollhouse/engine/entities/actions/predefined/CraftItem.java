package dijkstra.dollhouse.engine.entities.actions.predefined;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.GuiHandler;
import dijkstra.dollhouse.engine.JsonLoader;
import dijkstra.dollhouse.engine.entities.GameCraftableObject;
import dijkstra.dollhouse.engine.entities.actions.GameAction;
import dijkstra.dollhouse.engine.entities.details.GameInventory;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The script that allows the player to craft items.
 */
public class CraftItem extends GameAction implements GameScript {

  private static final String url = "./res/actions/craftable_objects.json";
  private static List<GameCraftableObject> craftableItems;
  private static Exception exception;

  static {
    craftableItems = new ArrayList<>();

    try {
      FileReader fileReader = new FileReader(url, Charset.defaultCharset());
      JSONParser parser = new JSONParser();
      JSONObject jsonRecipes = (JSONObject) parser.parse(fileReader);
      JSONArray jsonObjects = (JSONArray) jsonRecipes.get("craftable_objects");

      for (Object object : jsonObjects) {
        craftableItems.add(JsonLoader.getGameCraftableObject((JSONObject) object));
      }

      if (fileReader != null) {
        fileReader.close();
      }
    } catch (ClassNotFoundException | NoSuchMethodException
            | InstantiationException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException
            | IOException | ParseException e) {
      exception = e;
    }
  }

  public CraftItem(final String output) {
    super(output);
  }

  @Override
  public String execute() {
    String entity = GameHandler.getParsedInput().getEntity();
    GameInventory inventory = GameHandler.getGame().getPlayer().getGameInventory();
    GameCraftableObject object;

    try {
      object = craftableItems.stream()
                             .filter(o -> o.isAliasOf(entity))
                             .findAny()
                             .get();
      if (object.getRecipe()
                .stream()
                .anyMatch(needed -> inventory.findGameObject(needed) == null)) {
        return "Mancano gli oggetti necessari per costruire questo oggetto!";
      }
      inventory.add(object);
      GuiHandler.addInventory(object.getName());
      object.getRecipe().stream()
                        .map(itemName -> inventory.findGameObject(itemName))
                        .forEach(item -> {
                          inventory.remove(item);
                          GuiHandler.removeInventory(item.getName());
                        });
      return this.output + "\nL'oggetto e' stato aggiunto nel tuo inventario!";
    } catch (Exception e) {
      return "L'oggetto da costruire e' inesistente!";
    }
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

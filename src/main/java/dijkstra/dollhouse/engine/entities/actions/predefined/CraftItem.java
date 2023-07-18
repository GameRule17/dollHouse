package dijkstra.dollhouse.engine.entities.actions.predefined;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.JSONLoader;
import dijkstra.dollhouse.engine.entities.GameCraftableObject;
import dijkstra.dollhouse.engine.entities.actions.GameScriptedAction;
import dijkstra.dollhouse.engine.entities.details.GameInventory;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * .
 */
public class CraftItem extends GameScriptedAction {

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
        craftableItems.add(JSONLoader.getGameCraftableObject((JSONObject) object));
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
    Iterator<GameCraftableObject> it = craftableItems.iterator();
    GameCraftableObject object;

    while (it.hasNext()) {
      object = it.next();
      if (object.isAliasOf(entity)) {
        for (String needed : object.getRecipe()) {
          if (inventory.findGameObject(needed) == null) {
            return "Mancano gli oggetti necessari per costruire questo oggetto!";
          } else {
            inventory.add(object);
            for (String item : object.getRecipe()) {
              inventory.remove(inventory.findGameObject(item));
            }
            return "L'oggetto e' stato aggiunto nel tuo inventario!";
          }
        }
      }
    }

    return "L'oggetto da costruire e' inesistente!";

  }

  @Override
  public boolean isOver() {
    return true;
  }

  @Override
  public Exception getException() {
    return exception;
  }
  
  // public static void main(String[] args) {
  //   try {
  //     CraftItem j = new CraftItem("craftato");
  //     for (GameCraftableObject object : craftableItems) {
  //     System.out.println(object.toString());
  //   }
  //   } catch (Exception e) {
  //     e.printStackTrace();
  //   }
  // }
}
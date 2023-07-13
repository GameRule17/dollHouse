package dijkstra.dollhouse.entities.actions.predefined;

import dijkstra.dollhouse.Game;
import dijkstra.dollhouse.JSONLoader;
import dijkstra.dollhouse.entities.GameCraftableObject;
import dijkstra.dollhouse.entities.actions.GameScriptedAction;
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
 * .
 */
public class CraftItem extends GameScriptedAction {

  private static final String url = "./res/actions/craftable_objects.json";
  private static List<GameCraftableObject> craftableItems;

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
      e.printStackTrace();
    }
  }

  public CraftItem(String output)
                  throws IOException, ParseException,
                  ClassNotFoundException, NoSuchMethodException,
                  InstantiationException, IllegalAccessException,
                  IllegalArgumentException, InvocationTargetException {
    super(output);
  }

  @Override
  public boolean execute(Game game) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'execute'");
  }

  @Override
  public boolean isOver() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'isOver'");
  }

  @Override
  public Exception getException() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getException'");
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

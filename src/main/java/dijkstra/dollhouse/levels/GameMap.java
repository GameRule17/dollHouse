package dijkstra.dollhouse.levels;

import dijkstra.dollhouse.entities.GameEntity;
import dijkstra.dollhouse.entities.GameObject;
import dijkstra.dollhouse.entities.actions.GameAction;
import dijkstra.dollhouse.entities.actions.InteractGameAction;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.Collection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Class for representing the Map of the current game level.
 */
public class GameMap {

  private GameRoom currentRoom;

  private void setAdjacentRooms(final JSONObject jsonRoom, final GameRoom room,
                                final GameRoom[] rooms) {
    Long index = ((Long) jsonRoom.get("east"));
    if (index != null) {
      room.setEast(rooms[index.intValue()]);
    }
    index = ((Long) jsonRoom.get("west"));
    if (index != null) {
      room.setWest(rooms[index.intValue()]);
    }
    index = ((Long) jsonRoom.get("north"));
    if (index != null) {
      room.setNorth(rooms[index.intValue()]);
    }
    index = ((Long) jsonRoom.get("south"));
    if (index != null) {
      room.setSouth(rooms[index.intValue()]);
    }
  }

  private GameAction getScriptedAction(final String className,
                                      final String output)
                                      throws ClassNotFoundException, NoSuchMethodException,
                                      InstantiationException, IllegalAccessException,
                                      IllegalArgumentException, InvocationTargetException {
    if (className == null) {
      return new GameAction(output);
    }
    Class<?> classScript = Class.forName(className);
    Constructor<?> constructor = classScript.getDeclaredConstructor(String.class);
    Object script = constructor.newInstance(output);
    if (script instanceof GameAction) {
      return (GameAction) script;
    }
    throw new ClassNotFoundException("The script is not an istance of GameScript!\n");

  }

  private GameAction getGameAction(JSONObject jsonObject)
                                  throws ClassNotFoundException, NoSuchMethodException,
                                  InstantiationException, IllegalAccessException,
                                  IllegalArgumentException, InvocationTargetException {
    JSONArray jsonAliases = (JSONArray) ((JSONObject) jsonObject).get("aliases");
    String output  = (String) ((JSONObject) jsonObject).get("output");
    GameAction action = getScriptedAction((String) ((JSONObject) jsonObject).get("script"), output);
    
    for (Object alias : jsonAliases) {
      action.addAlias(alias.toString());
    }

    return action;
  }

  private GameObject getGameObject(JSONObject jsonObject)
                                  throws ClassNotFoundException,
                                  NoSuchMethodException, InstantiationException,
                                  IllegalAccessException, IllegalArgumentException,
                                  InvocationTargetException {
    String name = (String) ((JSONObject) jsonObject).get("name");
    boolean craftable = (boolean) ((JSONObject) jsonObject).get("craftable");
    GameObject gameObject = new GameObject(name, craftable);
    JSONArray jsonArray = (JSONArray) ((JSONObject) jsonObject).get("aliases");

    for (Object alias : jsonArray) {
      gameObject.addAlias(alias.toString());
    }

    jsonArray = (JSONArray) ((JSONObject) jsonObject).get("actions");
    for (Object action : jsonArray) {
      gameObject.addAction(getGameAction((JSONObject) action));
    }

    return gameObject;
  }

  private void setRoomObjects(final JSONArray jsonObjects, final GameRoom room)
                              throws ClassNotFoundException, NoSuchMethodException,
                              InstantiationException, IllegalAccessException,
                              IllegalArgumentException, InvocationTargetException {
    for (Object jsonObj : jsonObjects) {
      room.addEntity(getGameObject((JSONObject) jsonObj));
    }
  }

  private GameRoom[] getInitializedGameRooms(final int numberOfGameRooms) {
    GameRoom[] rooms = new GameRoom[numberOfGameRooms];
    for (int i = 0; i < numberOfGameRooms; i++) {
      rooms[i] = new GameRoom();
    }
    return rooms;
  }

  /**
   * Public constructor for the game map.

   * @param url - the path where is located the json file
   * @throws IOException due to the file
   * @throws org.json.simple.parser.ParseException parser exception
   * @throws InvocationTargetException .
   * @throws IllegalArgumentException .
   * @throws IllegalAccessException .
   * @throws InstantiationException .
   * @throws NoSuchMethodException .
   * @throws ClassNotFoundException .
   */
  public GameMap(final String url)
                throws IOException,
                org.json.simple.parser.ParseException,
                ClassNotFoundException,
                NoSuchMethodException, InstantiationException,
                IllegalAccessException, IllegalArgumentException,
                InvocationTargetException {
    FileReader fileReader = null;
    try {
      fileReader = new FileReader(url, Charset.defaultCharset());
      JSONParser parser = new JSONParser();
      JSONObject jsonMap = (JSONObject) parser.parse(fileReader);
      JSONArray jsonRooms = (JSONArray) jsonMap.get("gamerooms");
      JSONObject jsonRoom;
      int numberOfGameRooms = jsonRooms.size();
      GameRoom[] rooms = getInitializedGameRooms(numberOfGameRooms);

      for (int i = 0; i < numberOfGameRooms; i++) {
        jsonRoom = (JSONObject) jsonRooms.get(i);
        rooms[i].setName((String) jsonRoom.get("name"));
        rooms[i].setDescription((String) jsonRoom.get("description"));
        setAdjacentRooms(jsonRoom, rooms[i], rooms);
        setRoomObjects((JSONArray) jsonRoom.get("objects"), rooms[i]);
        // set npcs
      }

      currentRoom = rooms[0];
    } catch (IOException | org.json.simple.parser.ParseException e) {
      throw e;
    } finally {
      if (fileReader != null) {
        fileReader.close();
      }
    }
  }

  public GameRoom getCurrentRoom() {
    return currentRoom;
  }

  /**
   * .

   * @param room - dv
   */
  public void printObject(GameRoom room) {
    Collection<GameEntity> entities = room.getEntities();
    System.out.println("Puoi vedere " + entities.size() + " oggetti, ossia: ");
    for (GameEntity entity : entities) {
      System.out.println(entity.toString());
      for (GameAction action : entity.getActions()) {
        // System.out.print(action.toString());
        if (action instanceof InteractGameAction) {
          System.out.println("\033[32m");
          ((InteractGameAction) action).execute(this, entity);
          System.out.println("\033[0m");
        }
      }
    }
  }

  /**
   * .

   * @param args .
   */
  public static void main(String[] args) {
    try {
      GameMap map = new GameMap("./res/maps/piano_terra.json");
      map.printObject(map.getCurrentRoom());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

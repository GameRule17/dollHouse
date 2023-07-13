package dijkstra.dollhouse.levels;

import dijkstra.dollhouse.JSONLoader;
import dijkstra.dollhouse.entities.GameEntity;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.Collection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Class for representing the Map of the current game level.
 */
public class GameMap implements Serializable {

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

  private void setRoomObjects(final JSONArray jsonObjects, final GameRoom room)
                              throws ClassNotFoundException, NoSuchMethodException,
                              InstantiationException, IllegalAccessException,
                              IllegalArgumentException, InvocationTargetException {
    for (Object jsonObj : jsonObjects) {
      room.addEntity(JSONLoader.getGameEntity((JSONObject) jsonObj));
    }
  }

  private GameRoom[] getInitializedGameRooms(final int numberOfGameRooms) {
    GameRoom[] rooms = new GameRoom[numberOfGameRooms];
    for (int i = 0; i < numberOfGameRooms; i++) {
      rooms[i] = new GameRoom();
    }
    return rooms;
  }

  private void setNpcs(final JSONArray jsonNpcs, final GameRoom room)
                      throws ClassNotFoundException, NoSuchMethodException,
                      InstantiationException, IllegalAccessException,
                      IllegalArgumentException, InvocationTargetException {
    for (Object npc : jsonNpcs) {
      room.addEntity(JSONLoader.getGameNpc((JSONObject) npc));
    }
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
        setNpcs((JSONArray) jsonRoom.get("npcs"), rooms[i]);
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
    System.out.println("Puoi vedere " + entities.size() + " entità, ossia: ");
    for (GameEntity entity : entities) {
      System.out.println(entity.toString());
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

package dijkstra.dollhouse.engine.levels;

import dijkstra.dollhouse.engine.JSONLoader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Stack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * An istance of a GameMap represents the current map that a player
 * is playing. It has a current room which is the place where
 * the player is currently in.
 */
public class GameMap implements Serializable {

  private GameRoom currentRoom;

  private void setAdjacentRooms(final JSONObject jsonRoom, final GameRoom room,
                                final GameRoom[] rooms) {
    JSONArray adjacentRooms = ((JSONArray) jsonRoom.get("adjacentRooms"));
    int index;
    if (adjacentRooms == null) {
      return;
    }
    for (Object adjacentRoom : adjacentRooms) {
      index = Integer.valueOf(adjacentRoom.toString());
      room.addAdjacentRoom(rooms[index]);
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
      if (((JSONObject) npc).get("script") == null) {
        room.addEntity(JSONLoader.getGameNpc((JSONObject) npc));
      } else {
        room.addBehavioralNpc(JSONLoader.getGameBehavioralNpc((JSONObject) npc));
      }
    }
  }

  /**
   * Public constructor for the game map.
   * It takes the JSON file describing the map and tries to
   * parse, extract and load all data needed by the map.
   *
   * @param url - the path where is located the JSON file describing the map.
   * @throws IOException .
   * @throws org.json.simple.parser.ParseException .
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
   * This method invoke the run method of all GameBehavioralNpcs
   * present in the map.
   */
  public void runAllBehavioralNpcs() {
    Stack<GameRoom> toBeProcessed = new Stack<>();
    Collection<GameRoom> processed = new HashSet<>();
    GameRoom gameRoom;
    toBeProcessed.add(currentRoom);
    while (!toBeProcessed.isEmpty()) {
      gameRoom = toBeProcessed.pop();
      processed.add(gameRoom);
      gameRoom.runBehavioralNpcs();
      for (GameRoom adjacent : currentRoom.getAdjacentRooms()) {
        if (!processed.contains(adjacent)) {
          toBeProcessed.add(gameRoom);
        }
      }
    }
  }

  /**
   * This method invoke the stop method of all GameBehavioralNpcs
   * present in the map.
   */
  public void stopAllBehavioralNpcs() {
    Stack<GameRoom> toBeProcessed = new Stack<>();
    Collection<GameRoom> processed = new HashSet<>();
    GameRoom gameRoom;
    toBeProcessed.add(currentRoom);
    while (!toBeProcessed.isEmpty()) {
      gameRoom = toBeProcessed.pop();
      gameRoom.stopBehavioralNpcs();
      for (GameRoom adjacent : currentRoom.getAdjacentRooms()) {
        if (!processed.contains(adjacent)) {
          toBeProcessed.add(gameRoom);
        }
      }
      processed.add(gameRoom);
    }
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    Stack<GameRoom> toBeProcessed = new Stack<>();
    Collection<GameRoom> processed = new HashSet<>();
    GameRoom gameRoom;
    toBeProcessed.add(currentRoom);
    while (!toBeProcessed.isEmpty()) {
      gameRoom = toBeProcessed.pop();
      s.append(gameRoom.toString());
      for (GameRoom adjacent : currentRoom.getAdjacentRooms()) {
        if (!processed.contains(adjacent)) {
          toBeProcessed.add(gameRoom);
        }
      }
      processed.add(gameRoom);
    }
    return s.toString();
  }
}

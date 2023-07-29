package dijkstra.dollhouse.engine.levels;

import dijkstra.dollhouse.engine.JsonLoader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
  private String name;

  private void setAdjacentRooms(final JSONObject jsonRoom, final GameRoom room,
                                final GameRoom[] rooms) {
    JSONArray adjacentRooms = ((JSONArray) jsonRoom.get("adjacentRooms"));
    int index;
    if (adjacentRooms == null) {
      return;
    }
    int size = rooms.length;
    for (Object adjacentRoom : adjacentRooms) {
      for (index = 0; index < size; index++) {
        if (rooms[index].getName().compareToIgnoreCase(adjacentRoom.toString()) == 0) {
          room.addAdjacentRoom(rooms[index]);
        }
      }
    }
  }

  private void setRoomObjects(final JSONArray jsonObjects, final GameRoom room)
                              throws ClassNotFoundException, NoSuchMethodException,
                              InstantiationException, IllegalAccessException,
                              IllegalArgumentException, InvocationTargetException {
    for (Object jsonObj : jsonObjects) {
      room.addEntity(JsonLoader.getGameEntity((JSONObject) jsonObj));
    }
  }

  private GameRoom[] getInitializedGameRooms(final int numberOfGameRooms,
                                            final JSONArray jsonRooms) {
    GameRoom[] rooms = new GameRoom[numberOfGameRooms];
    JSONObject jsonRoom;
    for (int i = 0; i < numberOfGameRooms; i++) {
      rooms[i] = new GameRoom();
      jsonRoom = (JSONObject) jsonRooms.get(i);
      rooms[i].setName((String) jsonRoom.get("name"));
      rooms[i].setDescription((String) jsonRoom.get("description"));
    }
    return rooms;
  }

  private void setNpcs(final JSONArray jsonNpcs, final GameRoom room)
                      throws ClassNotFoundException, NoSuchMethodException,
                      InstantiationException, IllegalAccessException,
                      IllegalArgumentException, InvocationTargetException {
    if (jsonNpcs != null) {
      for (Object npc : jsonNpcs) {
        if (((JSONObject) npc).get("behavior") == null) {
          room.addEntity(JsonLoader.getGameNpc((JSONObject) npc));
        } else {
          room.addBehavioralNpc(JsonLoader.getGameBehavioralNpc((JSONObject) npc));
        }
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
      GameRoom[] rooms = getInitializedGameRooms(numberOfGameRooms, jsonRooms);

      name = jsonMap.get("name").toString();

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

  public String getName() {
    return name;
  }

  public GameRoom getCurrentRoom() {
    return currentRoom;
  }

  public void setCurrentRoom(final GameRoom room) {
    currentRoom = room;
  }

  /**
   * Returns the room that have an Entity called "name".
   *
   * @param name - the name of the entity.
   * @return GameRoom
   */
  public GameRoom getBehavioralNpcRoom(final String name) {
    Stack<GameRoom> toBeProcessed = new Stack<>();
    Collection<GameRoom> processed = new HashSet<>();
    GameRoom gameRoom;
    toBeProcessed.add(currentRoom);
    while (!toBeProcessed.isEmpty()) {
      gameRoom = toBeProcessed.pop();
      processed.add(gameRoom);
      if (gameRoom.findBehavioralNpc(name) != null) {
        return gameRoom;
      }
      for (GameRoom adjacent : gameRoom.getAdjacentRooms()) {
        if (!processed.contains(adjacent)) {
          toBeProcessed.add(adjacent);
        }
      }
    }
    return null;
  }

  /**
   * Returns the room with name "name".
   *
   * @param name - the name of room to find.
   * @return GameRoom
   */
  public GameRoom getRoom(final String name) {
    Stack<GameRoom> toBeProcessed = new Stack<>();
    Collection<GameRoom> processed = new HashSet<>();
    GameRoom gameRoom;
    toBeProcessed.add(currentRoom);
    while (!toBeProcessed.isEmpty()) {
      gameRoom = toBeProcessed.pop();
      processed.add(gameRoom);
      if (gameRoom.getName().compareToIgnoreCase(name) == 0) {
        return gameRoom;
      }
      for (GameRoom adjacent : gameRoom.getAdjacentRooms()) {
        if (!processed.contains(adjacent)) {
          toBeProcessed.add(adjacent);
        }
      }
    }
    return null;
  }

  /**
   * This method invoke the run method of all GameBehavioralNpcs
   * present in the map.
   */
  public void runAllBehavioralNpcs() {
    Stack<GameRoom> toBeProcessed = new Stack<>();
    Collection<GameRoom> processed = new HashSet<>();
    GameRoom gameRoom;
    Collection<GameRoom> adjacentRooms;
    Iterator<GameRoom> it;
    toBeProcessed.add(currentRoom);
    while (!toBeProcessed.isEmpty()) {
      gameRoom = toBeProcessed.pop();
      processed.add(gameRoom);
      gameRoom.runBehavioralNpcs();
      adjacentRooms = gameRoom.getAdjacentRooms();
      it = adjacentRooms.iterator();
      while (it.hasNext()) {
        gameRoom = it.next();
        if (!processed.contains(gameRoom)) {
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
    Collection<GameRoom> adjacentRooms;
    Iterator<GameRoom> it;
    toBeProcessed.add(currentRoom);
    while (!toBeProcessed.isEmpty()) {
      gameRoom = toBeProcessed.pop();
      processed.add(gameRoom);
      gameRoom.stopBehavioralNpcs();
      adjacentRooms = gameRoom.getAdjacentRooms();
      it = adjacentRooms.iterator();
      while (it.hasNext()) {
        gameRoom = it.next();
        if (!processed.contains(gameRoom)) {
          toBeProcessed.add(gameRoom);
        }
      }
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
      for (GameRoom adjacent : gameRoom.getAdjacentRooms()) {
        if (!processed.contains(adjacent)) {
          toBeProcessed.add(adjacent);
        }
      }
      processed.add(gameRoom);
    }
    return s.toString();
  }

  // public static void main(String[] args) {
  //   try {
  //     GameMap map = new GameMap("./res/maps/piano_terra.json");
  //     System.out.println(map.toString());
  //   } catch (Exception e) {
  //     e.printStackTrace();
  //   }
  // }
}

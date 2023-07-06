package dijkstra.dollhouse;

import java.io.FileReader;
// import java.util.Map;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Class for representing the Map of the current Level Game.
 */
public class GameMap {

  private String name;
  private GameRoom currentRoom;
  // Adiacents Map set

  // public void printObject (GameRoom room) {
  //   Map<String, GameEntity> entities = room.getEntities();
  //   System.out.println("Puoi vedere " + entities.size() + " oggetti, ossia: ");
  //   for (String key : entities.keySet()) {
  //       System.out.println(entities.get(key).toString());
  //   }
  // }

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

  private void setRoomObjects(final JSONArray jsonObjects, final GameRoom room) {
    String name;
    boolean pickupable;
    boolean craftable;
    GameObject roomObj;
    JSONArray jsonActions;
    String[] actions;
    for (Object jsonObj : jsonObjects) {
      actions = new String[GameEntity.NUMBER_OF_ACTIONS];
      name = (String) ((JSONObject) jsonObj).get("name");
      pickupable = (boolean) ((JSONObject) jsonObj).get("pickupable");
      craftable = (boolean) ((JSONObject) jsonObj).get("craftable");
      jsonActions = (JSONArray) ((JSONObject) jsonObj).get("actions");
      for (int j = 0; j < GameEntity.NUMBER_OF_ACTIONS; j++) {
        actions[j] = (String) jsonActions.get(j);
      }
      roomObj = new GameObject(name, actions, pickupable, craftable);
      room.addEntity(roomObj);
    }
  }

  /**
   * Public constructor for the game map.

   * @param url - the path where is located the json file
   * @throws IOException due to the file
   * @throws org.json.simple.parser.ParseException parser exception
   */
  public GameMap(final String url) throws IOException,
                                          org.json.simple.parser.ParseException {
    try {
      JSONParser parser = new JSONParser();
      Object obj = parser.parse(new FileReader(url));
      JSONObject map = (JSONObject) obj;

      this.name = (String) map.get("name");

      int numberOfGameRooms = ((Long) map.get("number_of_gamerooms")).intValue();
      GameRoom[] rooms = new GameRoom[(int) numberOfGameRooms];
      for (int i = 0; i < numberOfGameRooms; i++) {
        rooms[i] = new GameRoom();
      }

      JSONArray jsonRooms = (JSONArray) map.get("gamerooms");
      JSONObject room;
      for (int i = 0; i < numberOfGameRooms; i++) {
        room = (JSONObject) jsonRooms.get(i);
        setAdjacentRooms(room, rooms[i], rooms);
        rooms[i].setName((String) room.get("name"));
        rooms[i].setDescription((String) room.get("description"));
        setRoomObjects((JSONArray) room.get("objects"), rooms[i]);
        // set npcs
      }
      currentRoom = rooms[0];
    } catch (IOException | org.json.simple.parser.ParseException e) {
      throw e;
    }
  }

  public GameRoom getCurrentRoom() {
    return currentRoom;
  }

  // public static void main(String[] args) {
  //   try {
  //     GameMap map = new GameMap("./res/maps/piano_seminterrato.json");
  //     map.printObject(map.getCurrentRoom());
  //     map.printObject(map.getCurrentRoom().getNorth());
  //   } catch (Exception e) {
  //     e.printStackTrace();
  //   }
  // }
}

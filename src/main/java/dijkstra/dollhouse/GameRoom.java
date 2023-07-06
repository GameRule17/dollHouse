package dijkstra.dollhouse;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a room of one map.
 */
public class GameRoom {
  private GameRoom north;
  private GameRoom south;
  private GameRoom east;
  private GameRoom west;
  private String name;
  private String description;
  private Map<String, GameEntity> entities;
  // Actions Set

  /**
   * Default constructor.
   */
  public GameRoom() {
    north = null;
    south = null;
    east = null;
    west = null;
    name = null;
    description = null;
    entities = new HashMap<>();
  }

  /**
   * Public GameRoom constructor.
   * Sets the room at north, south, east and west positions.

   * @param north - north room
   * @param south - south room
   * @param east - east room
   * @param west - west room
   */
  public GameRoom(final GameRoom north, final GameRoom south,
                  final GameRoom east, final GameRoom west) {
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
  }

  public Map<String, GameEntity> getEntities() {
    return entities;
  }

  public void addEntity(final GameEntity entity) {
    entities.put(entity.getName(), entity);
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public GameRoom getNorth() {
    return north;
  }

  public GameRoom getSouth() {
    return south;
  }

  public GameRoom getEast() {
    return east;
  }

  public GameRoom getWest() {
    return west;
  }

  public void setNorth(final GameRoom north) {
    this.north = north;
  }

  public void setSouth(final GameRoom south) {
    this.south = south;
  }

  public void setEast(final GameRoom east) {
    this.east = east;
  }

  public void setWest(final GameRoom west) {
    this.west = west;
  }

}

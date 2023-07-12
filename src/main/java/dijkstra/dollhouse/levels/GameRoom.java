package dijkstra.dollhouse.levels;

import dijkstra.dollhouse.entities.GameEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A GameRoom represents a room of a GameMap.
 * GameRoom is responsible for managing all information about it,
 * about all entities in the room and about all four possible links
 * with other rooms.
 */
public class GameRoom implements Serializable {
  /*
   * nel caso del json potrebbe convenire salvare le stanze adiacenti
   * in un vettore e identificarle mediante id così che nel json sia possibile
   * referenziarle per lo script ChangeRoom
   */
  private GameRoom north;
  private GameRoom south;
  private GameRoom east;
  private GameRoom west;
  private String name;
  private String description;
  private Collection<GameEntity> entities;

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
    entities = new ArrayList<>();
  }

  /**
   * Finds an Entity with the name specified by "name" argument.
   * If this Entity does not exist, returns null
   *
   * @param name - the name of the entity to find
   * @return - the entity to find or null instead
   */
  public GameEntity findEntity(final String name) {
    Iterator<GameEntity> iterator = entities.iterator();
    GameEntity entity;
    while (iterator.hasNext()) {
      entity = iterator.next();
      if (entity.getName().compareToIgnoreCase(name) == 0) {
        return entity;
      }
    }
    return null;
  }

  public Collection<GameEntity> getEntities() {
    return entities;
  }

  public void addEntity(final GameEntity entity) {
    entities.add(entity);
  }

  public void removeEntity(final GameEntity entity) {
    entities.remove(entity);
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

package dijkstra.dollhouse.engine.levels;

import dijkstra.dollhouse.engine.entities.GameBehavioralNpc;
import dijkstra.dollhouse.engine.entities.GameEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * An istance of GameRoom represents a room in a map.
 * A Room has a collection of GameEntity and GameBehavioralNpc istances which
 * the player can interact with. It has a name that identifies the room in a GameMap and
 * a description. It has also a list of adjacent rooms the player can go to.
 * All these adjacent rooms are identified by its name and position in the list.
 */
public class GameRoom implements Serializable {
  private ArrayList<GameRoom> adjacentRooms;
  private String name;
  private String description;
  private Collection<GameEntity> entities;
  private Collection<GameBehavioralNpc> npcs;

  /**
   * Public constructor for GameRoom.
   * Here the list of all adjacent rooms and
   * the collection of entities and npcs are initialized as
   * an empty collection.
   */
  public GameRoom() {
    adjacentRooms = new ArrayList<>();
    description = null;
    entities = new ArrayList<>();
    npcs = new ArrayList<>();
  }

  public void addAdjacentRoom(final GameRoom gameRoom) {
    adjacentRooms.add(gameRoom);
  }

  public ArrayList<GameRoom> getAdjacentRooms() {
    return adjacentRooms;
  }

  /**
   * Finds an Entity with the name specified by "name" argument.
   * If this Entity does not exist, returns null.
   *
   * @param name - the name of the entity to find.
   * @return - the entity to find or null instead.
   */
  public GameEntity findEntity(final String name) {
    Iterator<GameEntity> iterator = entities.iterator();
    GameEntity entity;
    while (iterator.hasNext()) {
      entity = iterator.next();
      if (entity.isAliasOf(name)) {
        return entity;
      }
    }
    return null;
  }

  /**
   * Finds a GameBehavioralNpc with the name specified by "name" argument.
   * If this GameBehavioralNpc does not exist, returns null.
   *
   * @param name - the name of the GameBehavioralNpc to find.
   * @return - the GameBehavioralNpc to find or null instead.
   */
  public GameEntity findBehavioralNpc(final String name) {
    Iterator<GameBehavioralNpc> iterator = npcs.iterator();
    GameBehavioralNpc npc;
    while (iterator.hasNext()) {
      npc = iterator.next();
      if (npc.isAliasOf(name)) {
        return npc;
      }
    }
    return null;
  }

  /**
   * Invokes the run method of all GameBehavioralNpc istances
   * in this room.
   */
  public void runBehavioralNpcs() {
    for (GameBehavioralNpc npc : npcs) {
      npc.start();
    }
  }

  /**
   * Invokes the stop method of all GameBehavioralNpc istances
   * in this room.
   */
  public void stopBehavioralNpcs() {
    for (GameBehavioralNpc npc : npcs) {
      npc.stop();
    }
  }

  public void addEntity(final GameEntity entity) {
    entities.add(entity);
  }

  public void removeEntity(final GameEntity entity) {
    entities.remove(entity);
  }

  public void addBehavioralNpc(final GameBehavioralNpc npc) {
    npcs.add(npc);
  }

  public void removeBehavioralNpc(final GameBehavioralNpc npc) {
    npcs.remove(npc);
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

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder("Puoi vedere " + entities.size() + " entità, ossia: \n\n");
    for (GameEntity entity : entities) {
      s.append(entity.toString() + "\n");
    }
    for (GameBehavioralNpc npc : npcs) {
      s.append(npc.toString() + "\n");
    }
    return s.toString();
  }

}

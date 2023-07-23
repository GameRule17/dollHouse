package dijkstra.dollhouse.engine.entities;

import dijkstra.dollhouse.GUIHandler;
import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import dijkstra.dollhouse.engine.levels.GameRoom;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * All istances of GameBehavioralNpc represent npcs that have a script been executed
 * by a thread. This npcs are npcs that interact with the world regardless of user input.
 */
public class GameBehavioralNpc extends GameNpc implements Runnable {

  protected transient Thread thread;
  private GameScript script;
  protected List<String> rooms;
  private Random random;

  /**
   * .
   *
   * @param name .
   */
  public GameBehavioralNpc(String name) {
    super(name);
    rooms = new ArrayList<>();
    random = new Random();
  }

  public void addRoom(final String room) {
    rooms.add(room);
  }

  public void setGameScript(final GameScript script) {
    this.script = script;
  }

  public Thread getThread() {
    return thread;
  }

  /**
   * Start the thread associated with this istance.
   */
  public void start() {
    thread = new Thread(this, name);
    thread.start();
    System.out.println("Runnato: " + name);
  }

  /**
   * Stop the thread associated with this istance.
   */
  public void stop() {
    if (thread != null) {
      thread.interrupt();
      System.out.println("Stoppato: " + name);
    }
  }

  private String executeRandomBehavior() {
    int index = random.nextInt(0, 2);
    String output = "";

    if (index == 0) {
      index = random.nextInt(0, rooms.size());
      output = name + " entra in " + rooms.get(index);
      GameRoom newRoom = GameHandler.getGame().getMap().getRoom(rooms.get(index));
      GameRoom oldRoom = GameHandler.getGame().getMap().getBehavioralNpcRoom(name);
      oldRoom.removeBehavioralNpc(this);
      newRoom.addBehavioralNpc(this);
    } else {
      output = script.execute();
    }

    return output;
  }

  @Override
  public void run() {
    boolean isRunning = true;
    String output;

    
    while (isRunning) {
      try {
        Thread.sleep((long) 10e3);
        do {
          output = executeRandomBehavior();
          if (GameHandler.getGame().getMap().getCurrentRoom().findBehavioralNpc(name) != null) {
            GUIHandler.print("\n" + this.getName() + ": " + output + "\n");
          }
          if (script.getException() != null) {
            script.getException().printStackTrace();
          }
        } while (!script.isOver());
      } catch (InterruptedException e) {
        isRunning = false;
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder(super.toString());
    s.append("\nScript: " + script.getClass().getName());
    return s.toString();
  }
  
}

package dijkstra.dollhouse.engine.entities;

import dijkstra.dollhouse.engine.entities.scripts.GameScript;
import java.lang.Thread;

/**
 * All istances of GameBehavioralNpc represent npcs that have a script been executed
 * by a thread. This npcs are npcs that interact with the world regardless of user input.
 */
public class GameBehavioralNpc extends GameNpc implements Runnable {

  protected transient Thread thread;
  private GameScript script;

  public GameBehavioralNpc(String name) {
    super(name);
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
  }

  /**
   * Stop the thread associated with this istance.
   */
  public void stop() {
    thread.interrupt();
  }

  @Override
  public void run() {
    boolean isRunning = true;
    while (isRunning) {
      try {
        Thread.sleep((long) 5e3);
        System.out.print(this.getName() + ": ");
        do {
          script.execute();
          // print the output
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

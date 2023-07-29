package dijkstra.dollhouse;

import dijkstra.dollhouse.gui.GameWindow;

/**
 * Static class in order to launch the program.
 */
public class GameLauncher {

  /**
   * Starts the program.
   *
   * @param args .
   */
  public static void main(String[] args) {
    GameWindow.getInstance().setVisible(true);
  }

}

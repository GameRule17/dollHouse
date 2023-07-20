package dijkstra.dollhouse;

import dijkstra.dollhouse.gui.GamePanel;

public class GUIHandler {

  public static void addInventory(final String name) {
    GamePanel.addInventory(name);
  }

  public static void removeInventory(final String name) {
    GamePanel.removeInventory(name);
  }

  public static void print(final String msg) {
    GamePanel.printMessage(msg);
  }
}

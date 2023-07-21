package dijkstra.dollhouse;

import javax.swing.JOptionPane;

import dijkstra.dollhouse.gui.GamePanel;
import dijkstra.dollhouse.gui.GameWindow;
import dijkstra.dollhouse.gui.MenuPanel;

/**
 * .
 */
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

  public static void popUpMessage(final String msg) {
    JOptionPane.showMessageDialog(GameWindow.getInstance(), msg, "Fine gioco", JOptionPane.PLAIN_MESSAGE);
  }

  public static void returnMenu() {
    GameWindow.getInstance().updatePanel(new MenuPanel());
  }
}

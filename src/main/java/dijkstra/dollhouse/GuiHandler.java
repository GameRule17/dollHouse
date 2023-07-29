package dijkstra.dollhouse;

import dijkstra.dollhouse.gui.GamePanel;
import dijkstra.dollhouse.gui.GameWindow;
import dijkstra.dollhouse.gui.MenuPanel;

import java.io.UnsupportedEncodingException;

import javax.swing.JOptionPane;

/**
 * Static class in order to manage Engine-GUI integration.
 */
public class GuiHandler {

  public static void addInventory(final String name) {
    GamePanel.addInventory(name);
  }

  public static void removeInventory(final String name) {
    GamePanel.removeInventory(name);
  }

  public static void print(final String msg) {
    try {
      GamePanel.printMessage(new String(msg.getBytes(), "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void popUpMessage(final String msg) {
    JOptionPane.showMessageDialog(GameWindow.getInstance(), msg,
                                  "Fine gioco", JOptionPane.PLAIN_MESSAGE);
  }

  public static void returnMenu() {
    GameWindow.getInstance().updatePanel(new MenuPanel());
  }
}

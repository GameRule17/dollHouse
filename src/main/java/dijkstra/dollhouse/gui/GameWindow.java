package dijkstra.dollhouse.gui;

import dijkstra.dollhouse.DataBaseLoader;
import dijkstra.dollhouse.GameHandler;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * .
 */
public class GameWindow extends JFrame implements WindowListener {

  private static final GameWindow instance = new GameWindow();

  /**
   * Creates new form GUI.
   */
  private GameWindow() {
    try {
      initComponents();
      setContentPane(new MenuPanel());
      DataBaseLoader.initializeDbConnection();
      DataBaseLoader.createTable();
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(this, e,
                                    "Connessione al database fallita!", JOptionPane.ERROR_MESSAGE);
      GameWindow.getInstance().updatePanel(new MenuPanel());
    }
  }

  public static GameWindow getInstance() {
    return instance;
  }

  private void initComponents() {

    addWindowListener(this);
    setMinimumSize(new Dimension(1000, 600));

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Doll House Game");
    setBackground(new java.awt.Color(69, 69, 69));
    setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    setLocationByPlatform(true);

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGap(0, 806, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGap(0, 575, Short.MAX_VALUE)
    );

    getAccessibleContext().setAccessibleName("Doll House Game - Menu Principale");

    pack();
  }

  /**
   * Updates the current panel.
   *
   * @param panel - the panel to set.
   */
  public void updatePanel(final JPanel panel) {
    GameWindow.getInstance().setContentPane(panel);
    GameWindow.getInstance().revalidate();
    GameWindow.getInstance().repaint();
  }

  @Override
  public void windowOpened(WindowEvent e) {}

  @Override
  public void windowClosing(WindowEvent e) {
    try {
      if (GameHandler.getGame() != null) {
        GameHandler.onClose();
      }
    } catch (Exception e1) {
      JOptionPane.showMessageDialog(this, e1.getMessage(),
                              "Salvataggio fallito!", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void windowClosed(WindowEvent e) {}

  @Override
  public void windowIconified(WindowEvent e) {}

  @Override
  public void windowDeiconified(WindowEvent e) {}

  @Override
  public void windowActivated(WindowEvent e) {}

  @Override
  public void windowDeactivated(WindowEvent e) {}

  public static void main(String[] args) {
    GameWindow.getInstance().setVisible(true);
  }
}
package dijkstra.dollhouse.gui;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.MusicPlayer;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
  MusicPlayer musicPlayer;

  /**
   * Creates new form GUI.
   */
  private GameWindow() {
    String filePath = "./res/songs/menuStart.mp3";
    initComponents();
    musicPlayer = new MusicPlayer();
    musicPlayer.playMusic(filePath);
    musicPlayer.setVolume(0.03);
    setContentPane(new MenuPanel());
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

  public static void main(String[] args) {
    GameWindow.getInstance().setVisible(true);
  }

  @Override
  public void windowOpened(WindowEvent e) {}

  @Override
  public void windowClosing(WindowEvent e) {
    try {
      GameHandler.getGame().getMap().stopAllBehavioralNpcs();
      GameHandler.saveGame();
    } catch (Exception e1) {
      if (GameHandler.getGame() != null) {
        JOptionPane.showMessageDialog(this, e1.getMessage(),
                                "Salvataggio fallito!", JOptionPane.ERROR_MESSAGE);
      }
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
}
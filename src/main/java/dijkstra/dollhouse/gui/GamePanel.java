package dijkstra.dollhouse.gui;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.MusicPlayer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.text.DefaultCaret;

/**
 * Class for the game panel.
 */
public class GamePanel extends JPanel implements KeyListener, ActionListener {

  private JTextField inputCommandField;
  private JScrollPane scrollPane;
  private static List listObjectsInventory;
  private JButton openGlobalChatButton;
  private static JTextArea outputCommandArea;
  private JButton sendCommandButton;
  private static MusicPlayer musicPlayer;
  private static boolean isGuiUpdated = false;

  static {
    outputCommandArea = new JTextArea();
    listObjectsInventory = new List();
    musicPlayer = new MusicPlayer("./res/songs/game.mp3");
  }

  public GamePanel() {
    initComponents();
  }

  private void initComponents() {
    inputCommandField = new JTextField();
    sendCommandButton = new JButton();
    openGlobalChatButton = new JButton();
    scrollPane = new JScrollPane();

    // autoscroll
    DefaultCaret caret = (DefaultCaret) outputCommandArea.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    sendCommandButton.setActionCommand("execute");
    openGlobalChatButton.setActionCommand("chat");

    this.setBackground(new Color(45, 47, 48));
    this.setPreferredSize(new Dimension(730, 515));

    inputCommandField.setText("Inserisci comando");
    inputCommandField.setToolTipText("");
    inputCommandField.addKeyListener(this);

    listObjectsInventory.setBackground(new Color(88, 91, 93));
    listObjectsInventory.addActionListener(this);

    sendCommandButton.setBackground(new Color(0, 153, 153));
    sendCommandButton.setText("INVIA");
    sendCommandButton.setEnabled(true);
    sendCommandButton.addActionListener(this);

    openGlobalChatButton.setBackground(new Color(0, 153, 153));
    openGlobalChatButton.setEnabled(true);
    openGlobalChatButton.setLabel("🌐");
    openGlobalChatButton.addActionListener(this);

    outputCommandArea.setBackground(new Color(88, 91, 93));
    outputCommandArea.setColumns(20);
    outputCommandArea.setFont(new Font("Tahoma", 0, 14)); // NOI18N
    outputCommandArea.setLineWrap(true);
    outputCommandArea.setRows(5);
    outputCommandArea.setEnabled(false);
    outputCommandArea.setSelectionColor(new Color(255, 255, 255));
    scrollPane.setViewportView(outputCommandArea);

    GroupLayout gamePanelLayout = new GroupLayout(this);
    this.setLayout(gamePanelLayout);
    gamePanelLayout.setHorizontalGroup(
        gamePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gamePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addComponent(inputCommandField, GroupLayout.DEFAULT_SIZE,
                            64, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendCommandButton, GroupLayout.PREFERRED_SIZE,
                            111, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openGlobalChatButton, GroupLayout.PREFERRED_SIZE,
                            61, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listObjectsInventory, GroupLayout.PREFERRED_SIZE,
                    138, GroupLayout.PREFERRED_SIZE)
                .addContainerGap()));
    gamePanelLayout.setVerticalGroup(
        gamePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gamePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(listObjectsInventory, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
                            227, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(gamePanelLayout
                          .createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addGroup(gamePanelLayout
                              .createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(inputCommandField, GroupLayout.PREFERRED_SIZE,
                                    33, GroupLayout.PREFERRED_SIZE)
                                .addComponent(openGlobalChatButton, GroupLayout.PREFERRED_SIZE,
                                    35, GroupLayout.PREFERRED_SIZE))
                            .addComponent(sendCommandButton, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(10, 10, 10)));
  }

  public static boolean isGuiUpdated() {
    return isGuiUpdated;
  }

  public static void setIsGuiUpdated(final boolean flag) {
    isGuiUpdated = flag;
  }

  public static void cleanOutputArea() {
    outputCommandArea.setText("");
    listObjectsInventory.removeAll();
  }

  public static void addInventory(final String name) {
    listObjectsInventory.add(name);
  }

  public static void removeInventory(final String name) {
    listObjectsInventory.remove(name);
  }

  public static void printMessage(final String msg) {
    outputCommandArea.append(msg);
  }

  private void executeCommand() {
    try {
      outputCommandArea.append(GameHandler.executeCommand(inputCommandField.getText()));
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, e.getMessage(),
          "Esecuzione fallita!", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void keyPressed(KeyEvent arg0) {
    if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
      executeCommand();
      inputCommandField.setText("");
    }
  }

  @Override
  public void keyReleased(KeyEvent arg0) {

  }

  @Override
  public void keyTyped(KeyEvent arg0) {
    if (inputCommandField.getText().equals("Inserisci comando")) {
      inputCommandField.setText("");
    }
  }

  

  @Override
  public void actionPerformed(ActionEvent arg0) {
    String action = arg0.getActionCommand();
    switch (action) {
      case "execute":
        executeCommand();
        break;
      case "chat":
        try {
          GlobalChatPanel panel = new GlobalChatPanel();
          GameWindow.getInstance().updatePanel(panel);
          GameHandler.getGame().getMap().stopAllBehavioralNpcs();
        } catch (IOException e) {
          JOptionPane.showMessageDialog(this, "Non è stata avviata la connessione con il server!",
              "Errore di connessione", JOptionPane.ERROR_MESSAGE);
        }
        break;
      default:
        break;
    }
  }
}
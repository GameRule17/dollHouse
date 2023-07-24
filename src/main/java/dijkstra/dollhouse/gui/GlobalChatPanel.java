package dijkstra.dollhouse.gui;

import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.gui.socket.ChatClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 * Class for GlobalChatPanel.
 */
public class GlobalChatPanel extends JPanel implements KeyListener, ActionListener {

  private JLabel globalChatTitleLabel;
  private JTextField inputGlobalChatField;
  private JScrollPane scrollPane;
  private JButton returnGame;
  private JButton sendGlobalChatButton;
  public static JTextArea outputGlobalChatArea;

  private static String username = GameHandler.getGame().getPlayer().getName();
  private static ChatClient client;

  static {
    outputGlobalChatArea = new JTextArea();
    outputGlobalChatArea.setEditable(false);
    outputGlobalChatArea.setBackground(new java.awt.Color(88, 91, 93));
    outputGlobalChatArea.setColumns(20);
    outputGlobalChatArea.setRows(5);
    outputGlobalChatArea.setPreferredSize(null);
  }

  public GlobalChatPanel() throws IOException {
    initComponents();
    joinToChat();
  }

  private void initComponents() {

    scrollPane = new JScrollPane();
    globalChatTitleLabel = new JLabel();
    inputGlobalChatField = new JTextField();
    sendGlobalChatButton = new JButton();
    returnGame = new JButton();

    sendGlobalChatButton.setActionCommand("send");
    returnGame.setActionCommand("return");

    this.setBackground(new java.awt.Color(45, 47, 48));
    this.setPreferredSize(new java.awt.Dimension(730, 515));

    scrollPane.setViewportView(outputGlobalChatArea);

    globalChatTitleLabel.setBackground(new java.awt.Color(255, 255, 255));
    globalChatTitleLabel.setFont(new java.awt.Font("ROG Fonts", 0, 24)); // NOI18N
    globalChatTitleLabel.setForeground(new java.awt.Color(255, 255, 255));
    globalChatTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    globalChatTitleLabel.setText("CHAT GLOBALE");

    inputGlobalChatField.setText("Invia un messaggio");
    inputGlobalChatField.setToolTipText("");
    inputGlobalChatField.addKeyListener(this);

    sendGlobalChatButton.setBackground(new java.awt.Color(0, 153, 153));
    sendGlobalChatButton.setText("INVIA");
    sendGlobalChatButton.setEnabled(true);
    sendGlobalChatButton.addActionListener(this);

    returnGame.setText("Schermata di gioco");
    returnGame.setEnabled(true);
    returnGame.addActionListener(this);

    GroupLayout globalChatPanelLayout = new GroupLayout(this);
    this.setLayout(globalChatPanelLayout);
    globalChatPanelLayout.setHorizontalGroup(
        globalChatPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(globalChatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(globalChatPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING,
                              globalChatPanelLayout.createSequentialGroup()
                        .addComponent(returnGame)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(scrollPane)
                    .addGroup(globalChatPanelLayout.createSequentialGroup()
                        .addComponent(inputGlobalChatField, GroupLayout.DEFAULT_SIZE,
                                      259, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendGlobalChatButton, GroupLayout.PREFERRED_SIZE,
                            111, GroupLayout.PREFERRED_SIZE))
                    .addComponent(globalChatTitleLabel, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap()));
    globalChatPanelLayout.setVerticalGroup(
        globalChatPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(globalChatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(returnGame, GroupLayout.PREFERRED_SIZE,
                              23, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(globalChatTitleLabel, GroupLayout.PREFERRED_SIZE,
                    47, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
                    153, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(globalChatPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(inputGlobalChatField, GroupLayout.PREFERRED_SIZE,
                        35, GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendGlobalChatButton, GroupLayout.PREFERRED_SIZE,
                        35, GroupLayout.PREFERRED_SIZE))
                .addContainerGap()));
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (inputGlobalChatField.getText().equals("Invia un messaggio")) {
      inputGlobalChatField.setText("");
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      sendMessage();
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    switch (action) {
      case "send":
        sendMessage();
        break;
      case "return":
        GameWindow.getInstance().updatePanel(new GamePanel());
        GameHandler.getGame().getMap().runAllBehavioralNpcs();
        break;
      default:
        break;
    }
  }

  private void sendMessage() {
    String message = inputGlobalChatField.getText();
    if (message != null && message.trim().length() > 0) {
      try {
        client.sendMessage(username + ": " + message);
        inputGlobalChatField.setText("");
      } catch (IOException ex) {
        try {
          client.close();
          JOptionPane.showMessageDialog(this, "Il server non è più in esecuzione!",
                                        "Errore", JOptionPane.ERROR_MESSAGE);
          client = null;
          GameWindow.getInstance().updatePanel(new GamePanel());
        } catch (IOException e) {
          JOptionPane.showMessageDialog(this, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
      }
    }
  }



  private void joinToChat() throws IOException {
    if (client == null) {
      client = new ChatClient();
      try {
        client.start();
      } catch (IOException exc) {
        client = null;
        throw exc;
      }
    }
  }
}
package dijkstra.dollhouse.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
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
  private JTextArea outputGlobalChatArea;
  private JButton returnGame;
  private JButton sendGlobalChatButton;

  public GlobalChatPanel() {
    initComponents();
  }

  private void initComponents() {

    scrollPane = new JScrollPane();
    outputGlobalChatArea = new JTextArea();
    globalChatTitleLabel = new JLabel();
    inputGlobalChatField = new JTextField();
    sendGlobalChatButton = new JButton();
    returnGame = new JButton();

    sendGlobalChatButton.setActionCommand("send");
    returnGame.setActionCommand("return");

    this.setBackground(new java.awt.Color(45, 47, 48));
    this.setPreferredSize(new java.awt.Dimension(730, 515));

    outputGlobalChatArea.setEditable(false);
    outputGlobalChatArea.setBackground(new java.awt.Color(88, 91, 93));
    outputGlobalChatArea.setColumns(20);
    outputGlobalChatArea.setRows(5);
    outputGlobalChatArea.setPreferredSize(null);
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
            .addGroup(GroupLayout.Alignment.TRAILING, globalChatPanelLayout.createSequentialGroup()
              .addComponent(returnGame)
              .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(scrollPane)
            .addGroup(globalChatPanelLayout.createSequentialGroup()
              .addComponent(inputGlobalChatField, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
              .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(sendGlobalChatButton, GroupLayout.PREFERRED_SIZE,
                      111, GroupLayout.PREFERRED_SIZE))
            .addComponent(globalChatTitleLabel, GroupLayout.DEFAULT_SIZE,
                          GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addContainerGap())
    );
    globalChatPanelLayout.setVerticalGroup(
        globalChatPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(globalChatPanelLayout.createSequentialGroup()
          .addContainerGap()
          .addComponent(returnGame, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
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
          .addContainerGap())
    );
  }

  public void sendMessage() {

  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {}

  @Override
  public void keyReleased(KeyEvent e) {}

  @Override
  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    switch (action) {
      case "send":
        // implementare sendMessage()
        sendMessage();
        break;
      case "return":
        GameWindow.getInstance().updatePanel(new GamePanel());
        break;
      default:
        break;
    }
  }
}
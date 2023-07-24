package dijkstra.dollhouse.gui;

import dijkstra.dollhouse.DataBaseLoader;
import dijkstra.dollhouse.GameHandler;
import dijkstra.dollhouse.MusicPlayer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 * Class for pick a name form.
 */
public class PickNamePanel extends JPanel implements ActionListener, KeyListener {

  private JTextField insertNameField;
  private JButton returnMenu;
  private JButton startGameButton;
  private JLabel startNewGameLabel;
  private JLabel wrongNicknameLabel;

  public PickNamePanel() {
    initComponents();
  }

  private void initComponents() {
    startNewGameLabel = new JLabel();
    startGameButton = new JButton();
    insertNameField = new JTextField();
    wrongNicknameLabel = new JLabel();
    returnMenu = new JButton();

    returnMenu.setActionCommand("return");
    startGameButton.setActionCommand("start");

    this.setBackground(new Color(45, 47, 48));
    this.setPreferredSize(new Dimension(730, 515));

    startNewGameLabel.setFont(new Font("ROG Fonts", 0, 36)); // NOI18N
    startNewGameLabel.setForeground(new Color(102, 51, 255));
    startNewGameLabel.setHorizontalAlignment(SwingConstants.CENTER);
    startNewGameLabel.setText("INIZIA UNA NUOVA PARTITA");

    startGameButton.setText("INIZIA");
    startGameButton.addActionListener(this);

    insertNameField.setText("Inserisci il tuo nickname");
    insertNameField.addKeyListener(this);

    wrongNicknameLabel.setBackground(new Color(255, 255, 255));
    wrongNicknameLabel.setForeground(new Color(255, 51, 51));

    returnMenu.setText("Indietro");
    returnMenu.setEnabled(true);
    returnMenu.addActionListener(this);

    GroupLayout namePanelLayout = new GroupLayout(this);
    this.setLayout(namePanelLayout);
    namePanelLayout.setHorizontalGroup(
        namePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(namePanelLayout.createSequentialGroup()
          .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(returnMenu)
          .addContainerGap())
        .addComponent(startNewGameLabel, GroupLayout.DEFAULT_SIZE,
                      GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(GroupLayout.Alignment.TRAILING, namePanelLayout.createSequentialGroup()
          .addGap(0, 0, Short.MAX_VALUE)
          .addGroup(namePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addComponent(startGameButton, GroupLayout.PREFERRED_SIZE,
                          195, GroupLayout.PREFERRED_SIZE)
            .addComponent(wrongNicknameLabel, GroupLayout.PREFERRED_SIZE,
                          195, GroupLayout.PREFERRED_SIZE)
            .addComponent(insertNameField, GroupLayout.PREFERRED_SIZE,
                          195, GroupLayout.PREFERRED_SIZE))
          .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    namePanelLayout.setVerticalGroup(
        namePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(namePanelLayout.createSequentialGroup()
        .addComponent(startNewGameLabel, GroupLayout.PREFERRED_SIZE,
                      85, GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(insertNameField, GroupLayout.PREFERRED_SIZE,
                      48, GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(wrongNicknameLabel, GroupLayout.PREFERRED_SIZE,
                      34, GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, Short.MAX_VALUE)
        .addComponent(startGameButton, GroupLayout.PREFERRED_SIZE,
                      40, GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(returnMenu)
        .addGap(26, 26, 26))
    );
  }

  private void startGame() {
    String username = insertNameField.getText();
    wrongNicknameLabel.setText("");
    try {
      if (username != null
          && username.length() > 0
          && !username.equals("Inserisci il tuo nickname")
          && !username.contains(" ")
          && !DataBaseLoader.usernameAlreadyUsed(username)) {
        try {
          MusicPlayer.stopMusic();
          MusicPlayer.playMusic("./res/songs/game.mp3");
          GameHandler.newGame();
          GameHandler.getGame().getPlayer().setName(username);
          GameHandler.onOpen();
          GameWindow.getInstance().updatePanel(new GamePanel());
        } catch (Exception e) {
          JOptionPane.showMessageDialog(this, e.getMessage(), "ERRORE",
                                        JOptionPane.ERROR_MESSAGE);
        }
      } else {
        wrongNicknameLabel.setText("Username non valido o già usato!");
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(this, e.getMessage(), "Non è possibile accedere ai dati!",
                                        JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    switch (action) {
      case "start":
        startGame();
        break;
      case "return":
        GameWindow.getInstance().updatePanel(new MenuPanel());
        break;
      default:
        break;
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (insertNameField.getText().equals("Inserisci il tuo nickname")) {
      insertNameField.setText("");
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
      startGame();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}
}
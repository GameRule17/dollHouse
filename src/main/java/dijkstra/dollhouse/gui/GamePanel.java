package dijkstra.dollhouse.gui;

import dijkstra.dollhouse.GameHandler;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
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
  private static JList<String> listObjectsInventory;
  private JButton openGlobalChatButton;
  private static JTextArea outputCommandArea;
  private JButton sendCommandButton;
  private static DefaultListModel<String> model;
  private static boolean isGuiUpdated = false;

  static {
    outputCommandArea = new JTextArea();
    model = new DefaultListModel<>();
    listObjectsInventory = new JList<String>(model);
  }

  public GamePanel() {
    initComponents();
  }

  private void setBackgroundToImageStyle() {
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setBorder(null);
    scrollPane.setViewportBorder(null);
    scrollPane.setBorder(null);
    // scrollPane.setBackground(new Color(0, 0, 0, 0));
    listObjectsInventory.setOpaque(false);
    listObjectsInventory.setCellRenderer(new DefaultListCellRenderer() {
      @Override
        public Component getListCellRendererComponent(JList<?> list1, Object obj,
                                                  int index, boolean isSelected, boolean isFocus) {
            super.getListCellRendererComponent(list1, obj, index, isSelected, isFocus);
            setForeground(Color.WHITE);
            setOpaque(isSelected);
            return this;
        }
    });
    outputCommandArea.setOpaque(false);
    // outputCommandArea.setForeground(new java.awt.Color(255, 255, 255, 255));
    // scrollPane.setForeground(new java.awt.Color(255, 255, 255, 255));
  }

  private void setMinimalStyle() {
    this.setBackground(new Color(45, 47, 48));
    listObjectsInventory.setBackground(new Color(88, 91, 93));
    outputCommandArea.setBackground(new Color(88, 91, 93));
    outputCommandArea.setSelectionColor(new Color(255, 255, 255));
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

    this.setPreferredSize(new Dimension(730, 515));

    inputCommandField.setText("Inserisci comando");
    inputCommandField.addKeyListener(this);

    sendCommandButton.setBackground(new Color(0, 153, 153));
    sendCommandButton.setText("INVIA");
    sendCommandButton.setEnabled(true);
    sendCommandButton.addActionListener(this);

    openGlobalChatButton.setBackground(new Color(0, 153, 153));
    openGlobalChatButton.setEnabled(true);
    openGlobalChatButton.setText("🌐");
    openGlobalChatButton.addActionListener(this);

    
    outputCommandArea.setColumns(20);
    outputCommandArea.setFont(new Font("Tahoma", 0, 14)); // NOI18N
    outputCommandArea.setLineWrap(true);
    outputCommandArea.setRows(5);
    outputCommandArea.setEnabled(false);
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
    // setBackgroundToImageStyle();
    setMinimalStyle();
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
    model.addElement(name);
  }

  public static void removeInventory(final String name) {
    model.removeElement(name);
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

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    ImageIcon img = new ImageIcon("./res/images/background.jpg");
    g.drawImage(img.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
  }
}
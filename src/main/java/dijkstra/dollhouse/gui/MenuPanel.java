package dijkstra.dollhouse.gui;

import dijkstra.dollhouse.GameHandler;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * Class for Menu Panel.
 */
public class MenuPanel extends JPanel implements ActionListener {

  private JButton creditsButton;
  private JButton loadGameButton;
  private JButton newGameButton;
  private JButton openStatisticsButton;
  private JLabel titleMenu;

  public MenuPanel() {
    initComponents();
  }

  private void initComponents() {
    titleMenu = new JLabel();
    openStatisticsButton = new JButton();
    loadGameButton = new JButton();
    creditsButton = new JButton();
    newGameButton = new JButton();

    openStatisticsButton.setActionCommand("statistics");
    loadGameButton.setActionCommand("load game");
    creditsButton.setActionCommand("credits");
    newGameButton.setActionCommand("new game");

    this.setBackground(new Color(45, 47, 48));

    titleMenu.setFont(new Font("ROG Fonts", 0, 36)); // NOI18N
    titleMenu.setForeground(new Color(102, 51, 255));
    titleMenu.setHorizontalAlignment(SwingConstants.CENTER);
    titleMenu.setText("DOLL HOUSE GAME");

    openStatisticsButton.setFont(new Font("Segoe UI", 0, 24)); // NOI18N
    openStatisticsButton.setText("STATISTICHE");
    openStatisticsButton.addActionListener(this);

    loadGameButton.setFont(new Font("Segoe UI", 0, 24)); // NOI18N
    loadGameButton.setText("CARICA PARTITA");
    loadGameButton.addActionListener(this);

    creditsButton.setFont(new Font("Segoe UI", 0, 24)); // NOI18N
    creditsButton.setText("CREDITS");
    creditsButton.addActionListener(this);

    newGameButton.setFont(new Font("Segoe UI", 0, 24)); // NOI18N
    newGameButton.setText("NUOVA PARTITA");
    newGameButton.addActionListener(this);

    GroupLayout menuPanelLayout = new GroupLayout(this);
    this.setLayout(menuPanelLayout);
    menuPanelLayout.setHorizontalGroup(
        menuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(titleMenu, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
        .addGroup(menuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addGroup(menuPanelLayout.createSequentialGroup()
            .addContainerGap(236, Short.MAX_VALUE)
            .addGroup(menuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
              .addComponent(openStatisticsButton, GroupLayout.PREFERRED_SIZE,
                            270, GroupLayout.PREFERRED_SIZE)
              .addComponent(creditsButton, GroupLayout.PREFERRED_SIZE,
                            270, GroupLayout.PREFERRED_SIZE)
              .addComponent(loadGameButton, GroupLayout.PREFERRED_SIZE,
                            270, GroupLayout.PREFERRED_SIZE)
              .addComponent(newGameButton, GroupLayout.PREFERRED_SIZE,
                            270, GroupLayout.PREFERRED_SIZE))
            .addContainerGap(236, Short.MAX_VALUE)))
    );
    menuPanelLayout.setVerticalGroup(
        menuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(menuPanelLayout.createSequentialGroup()
          .addGap(16, 16, 16)
          .addComponent(titleMenu)
          .addContainerGap(225, Short.MAX_VALUE))
        .addGroup(menuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addGroup(menuPanelLayout.createSequentialGroup()
            .addContainerGap(176, Short.MAX_VALUE)
            .addComponent(newGameButton, GroupLayout.PREFERRED_SIZE,
                          41, GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(loadGameButton, GroupLayout.PREFERRED_SIZE,
                          41, GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(openStatisticsButton, GroupLayout.PREFERRED_SIZE,
                          41, GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(creditsButton, GroupLayout.PREFERRED_SIZE,
                          41, GroupLayout.PREFERRED_SIZE)
            .addContainerGap(133, Short.MAX_VALUE)))
    );
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    String action = event.getActionCommand();
    switch (action) {
      case "new game":
        GameWindow.getInstance().updatePanel(new PickNamePanel());
        break;
      case "load game":
        try {
          GameHandler.initParser();
          GameHandler.loadGame();
          GameHandler.init();
          GameWindow.getInstance().updatePanel(new GamePanel());
        } catch (Exception e) {
          JOptionPane.showMessageDialog(this, e.getMessage(),
                                        "Caricamento fallito!", JOptionPane.ERROR_MESSAGE);
          // e.printStackTrace();
        }
        break;
      case "statistics":
        GameWindow.getInstance().updatePanel(new StatisticsPanel());
        break;
      case "credits":
        GameWindow.getInstance().updatePanel(new CreditsPanel());
        break;
      default:
        break;
    }
  }
}
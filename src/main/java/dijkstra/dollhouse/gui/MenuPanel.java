package dijkstra.dollhouse.gui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dijkstra.dollhouse.GameHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener {

    private javax.swing.JButton creditsButton;
    private javax.swing.JButton loadGameButton;
    private javax.swing.JButton newGameButton;
    private javax.swing.JButton openStatisticsButton;
    private javax.swing.JLabel titleMenu;

    public MenuPanel() {
        initComponents();
    }

    private void initComponents() {
        titleMenu = new javax.swing.JLabel();
        openStatisticsButton = new javax.swing.JButton();
        loadGameButton = new javax.swing.JButton();
        creditsButton = new javax.swing.JButton();
        newGameButton = new javax.swing.JButton();

        openStatisticsButton.setActionCommand("statistics");
        loadGameButton.setActionCommand("load game");
        creditsButton.setActionCommand("credits");
        newGameButton.setActionCommand("new game");

        this.setBackground(new java.awt.Color(45, 47, 48));

        titleMenu.setFont(new java.awt.Font("ROG Fonts", 0, 36)); // NOI18N
        titleMenu.setForeground(new java.awt.Color(102, 51, 255));
        titleMenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleMenu.setText("DOLL HOUSE GAME");

        openStatisticsButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        openStatisticsButton.setText("STATISTICHE");
        openStatisticsButton.addActionListener(this);

        loadGameButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        loadGameButton.setText("CARICA PARTITA");

        creditsButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        creditsButton.setText("CREDITS");
        creditsButton.addActionListener(this);

        newGameButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        newGameButton.setText("NUOVA PARTITA");
        newGameButton.addActionListener(this);

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(this);
        this.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
            .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(menuPanelLayout.createSequentialGroup()
                    .addContainerGap(236, Short.MAX_VALUE)
                    .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(openStatisticsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(creditsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(loadGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(newGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(236, Short.MAX_VALUE)))
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(titleMenu)
                .addContainerGap(225, Short.MAX_VALUE))
            .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(menuPanelLayout.createSequentialGroup()
                    .addContainerGap(176, Short.MAX_VALUE)
                    .addComponent(newGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(loadGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(openStatisticsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(creditsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(133, Short.MAX_VALUE)))
        );
    }                  

    @Override
    public void actionPerformed(ActionEvent event) {
        String action = event.getActionCommand();
        switch (action) {
            case "new game":
                GameWindow.getInstance().updatePanel(new PickANamePanel());
            break;
            case "load game":
                try {
                    GameHandler.initParser();
                    GameHandler.loadGame();
                    GameHandler.init();
                    GameWindow.getInstance().updatePanel(new GamePanel());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                    // e.printStackTrace();
                }
            break;
            case "statistics":
                GameWindow.getInstance().updatePanel(new StatisticsPanel());
            break;
            case "credits":
                GameWindow.getInstance().updatePanel(new CreditsPanel());
            break;
        }
    }
}

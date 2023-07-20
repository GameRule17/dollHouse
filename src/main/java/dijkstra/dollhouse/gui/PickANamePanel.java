package dijkstra.dollhouse.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dijkstra.dollhouse.GameHandler;

public class PickANamePanel extends JPanel implements ActionListener, KeyListener {

    private javax.swing.JTextField insertNameField;
    private javax.swing.JButton returnMenuFromPickAName;
    private javax.swing.JButton startGameButton;
    private javax.swing.JLabel startNewGameLabel;
    private javax.swing.JLabel wrongNicknameLabel; 

    public PickANamePanel() {
        initComponents();
    }

    private void initComponents() {
        startNewGameLabel = new javax.swing.JLabel();
        startGameButton = new javax.swing.JButton();
        insertNameField = new javax.swing.JTextField();
        wrongNicknameLabel = new javax.swing.JLabel();
        returnMenuFromPickAName = new javax.swing.JButton();

        returnMenuFromPickAName.setActionCommand("return");
        startGameButton.setActionCommand("start");

        this.setBackground(new java.awt.Color(45, 47, 48));
        this.setPreferredSize(new java.awt.Dimension(730, 515));

        startNewGameLabel.setFont(new java.awt.Font("ROG Fonts", 0, 36)); // NOI18N
        startNewGameLabel.setForeground(new java.awt.Color(102, 51, 255));
        startNewGameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startNewGameLabel.setText("INIZIA UNA NUOVA PARTITA");

        startGameButton.setText("INIZIA");
        startGameButton.addActionListener(this);

        insertNameField.setText("Inserisci il tuo nickname");
        insertNameField.addKeyListener(this);

        wrongNicknameLabel.setBackground(new java.awt.Color(255, 255, 255));
        wrongNicknameLabel.setForeground(new java.awt.Color(255, 51, 51));

        returnMenuFromPickAName.setText("Indietro");
        returnMenuFromPickAName.setEnabled(true);
        returnMenuFromPickAName.addActionListener(this);

        javax.swing.GroupLayout pickANamePanelLayout = new javax.swing.GroupLayout(this);
        this.setLayout(pickANamePanelLayout);
        pickANamePanelLayout.setHorizontalGroup(
            pickANamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pickANamePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(returnMenuFromPickAName)
                .addContainerGap())
            .addComponent(startNewGameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pickANamePanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pickANamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(startGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(wrongNicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(insertNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pickANamePanelLayout.setVerticalGroup(
            pickANamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pickANamePanelLayout.createSequentialGroup()
                .addComponent(startNewGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(insertNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(wrongNicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(startGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(returnMenuFromPickAName)
                .addGap(26, 26, 26))
        );
    }

    private void startGame() {
        String username = insertNameField.getText();
        wrongNicknameLabel.setText("");
        if (username != null && username.length() > 0 && !username.equals("Inserisci il tuo nickname")) {
            GameWindow.getInstance().updatePanel(new GamePanel());
            try {
                GameHandler.initParser();
                GameHandler.newGame();
                GameHandler.getGame().getPlayer().setName(username);
                GameHandler.init();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
                // e.printStackTrace();
            }
        } else {
            wrongNicknameLabel.setText("Username non valido!");
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
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(insertNameField.getText().equals("Inserisci comando")) {
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
    public void keyReleased(KeyEvent e) {
    }            
}

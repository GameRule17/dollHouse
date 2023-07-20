package dijkstra.dollhouse.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class GlobalChatPanel extends JPanel implements KeyListener, ActionListener {

    private javax.swing.JLabel globalChatTitleLabel;
    private javax.swing.JTextField inputGlobalChatField;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea outputGlobalChatArea;
    private javax.swing.JButton returnGame;
    private javax.swing.JButton sendGlobalChatButton;    

    public GlobalChatPanel() {
        initComponents();
    }
                      
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        outputGlobalChatArea = new javax.swing.JTextArea();
        globalChatTitleLabel = new javax.swing.JLabel();
        inputGlobalChatField = new javax.swing.JTextField();
        sendGlobalChatButton = new javax.swing.JButton();
        returnGame = new javax.swing.JButton();

        sendGlobalChatButton.setActionCommand("send");
        returnGame.setActionCommand("return");

        this.setBackground(new java.awt.Color(45, 47, 48));
        this.setPreferredSize(new java.awt.Dimension(730, 515));

        outputGlobalChatArea.setEditable(false);
        outputGlobalChatArea.setBackground(new java.awt.Color(88, 91, 93));
        outputGlobalChatArea.setColumns(20);
        outputGlobalChatArea.setRows(5);
        outputGlobalChatArea.setPreferredSize(null);
        jScrollPane2.setViewportView(outputGlobalChatArea);

        globalChatTitleLabel.setBackground(new java.awt.Color(255, 255, 255));
        globalChatTitleLabel.setFont(new java.awt.Font("ROG Fonts", 0, 24)); // NOI18N
        globalChatTitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        globalChatTitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
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

        javax.swing.GroupLayout globalChatPanelLayout = new javax.swing.GroupLayout(this);
        this.setLayout(globalChatPanelLayout);
        globalChatPanelLayout.setHorizontalGroup(
            globalChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(globalChatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(globalChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, globalChatPanelLayout.createSequentialGroup()
                        .addComponent(returnGame)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addGroup(globalChatPanelLayout.createSequentialGroup()
                        .addComponent(inputGlobalChatField, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendGlobalChatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(globalChatTitleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        globalChatPanelLayout.setVerticalGroup(
            globalChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(globalChatPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(returnGame, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(globalChatTitleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(globalChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputGlobalChatField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sendGlobalChatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }

    public void sendMessage() {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

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
        }
    }            
}

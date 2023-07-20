package dijkstra.dollhouse.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.text.DefaultCaret;

import dijkstra.dollhouse.GameHandler;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private javax.swing.JTextField inputCommandField;
    private javax.swing.JScrollPane jScrollPane1;
    private static java.awt.List listObjectsInventory;
    private javax.swing.JButton openGlobalChatButton;
    private static javax.swing.JTextArea outputCommandArea;
    private javax.swing.JButton sendCommandButton;

    static {
        outputCommandArea = new javax.swing.JTextArea();
        listObjectsInventory = new java.awt.List();
    }

    /**
     * Creates new form FRAME_LAVORO
     */
    public GamePanel() {
        initComponents();
    }

    private void initComponents() {
        inputCommandField = new javax.swing.JTextField();
        sendCommandButton = new javax.swing.JButton();
        openGlobalChatButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();

        // autoscroll
        DefaultCaret caret = (DefaultCaret) outputCommandArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        sendCommandButton.setActionCommand("execute");
        openGlobalChatButton.setActionCommand("chat");

        this.setBackground(new java.awt.Color(45, 47, 48));
        this.setPreferredSize(new java.awt.Dimension(730, 515));

        inputCommandField.setText("Inserisci comando");
        inputCommandField.setToolTipText("");
        inputCommandField.addKeyListener(this);

        listObjectsInventory.setBackground(new java.awt.Color(88, 91, 93));
        listObjectsInventory.addActionListener(this);

        sendCommandButton.setBackground(new java.awt.Color(0, 153, 153));
        sendCommandButton.setText("INVIA");
        sendCommandButton.setEnabled(true);
        sendCommandButton.addActionListener(this);

        openGlobalChatButton.setBackground(new java.awt.Color(0, 153, 153));
        openGlobalChatButton.setEnabled(true);
        openGlobalChatButton.setLabel("🌐");
        openGlobalChatButton.addActionListener(this);

        outputCommandArea.setBackground(new java.awt.Color(88, 91, 93));
        outputCommandArea.setColumns(20);
        outputCommandArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        outputCommandArea.setLineWrap(true);
        outputCommandArea.setRows(5);
        outputCommandArea.setEnabled(false);
        outputCommandArea.setSelectionColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(outputCommandArea);

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(this);
        this.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addComponent(inputCommandField, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendCommandButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(openGlobalChatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listObjectsInventory, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(listObjectsInventory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(inputCommandField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(openGlobalChatButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(sendCommandButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(10, 10, 10))
        );
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
        outputCommandArea.append(GameHandler.executeCommand(inputCommandField.getText()));
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
        if(inputCommandField.getText().equals("Inserisci comando")) {
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
                GameHandler.getGame().getMap().stopAllBehavioralNpcs();
                GameWindow.getInstance().updatePanel(new GlobalChatPanel());
            break;
        }
    }                 
}


package dijkstra.dollhouse.gui;

import javax.swing.*;

import dijkstra.dollhouse.MusicPlayer;

public class GameWindow extends JFrame {

    private static final GameWindow instance = new GameWindow();
    MusicPlayer musicPlayer;
    
    /**
     * Creates new form GUI
     */
    private GameWindow() {
        String filePath = "./res/songs/menuStart.mp3";
        initComponents();
        musicPlayer = new MusicPlayer();
        musicPlayer.playMusic(filePath);
        musicPlayer.setVolume(0.01);
        setContentPane(new MenuPanel());
    }

    public static GameWindow getInstance() {
        return instance;
    }

    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Doll House Game");
        setBackground(new java.awt.Color(69, 69, 69));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 806, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 575, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("Doll House Game - Menu Principale");

        pack();
    }

    public void updatePanel(final JPanel panel) {
        GameWindow.getInstance().setContentPane(panel);
        GameWindow.getInstance().revalidate();
        GameWindow.getInstance().repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        GameWindow.getInstance().setVisible(true);
    }
}


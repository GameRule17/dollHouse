package dijkstra.dollhouse.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import dijkstra.dollhouse.DataBaseLoader;
import dijkstra.dollhouse.TablePrefixSuffix;

public class StatisticsPanel extends JPanel implements ActionListener {

    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton pointsButton;
    private javax.swing.JButton returnMenuFromStatistics;
    private javax.swing.JLabel statisticsGlobalTitle;
    private javax.swing.JLabel statisticsLabel;
    private javax.swing.JButton timeButton;
    private javax.swing.JLabel userData;

    public StatisticsPanel() {
        DataBaseLoader.initializeDBConnection();
        DataBaseLoader.createTable();
        initComponents();
    }
                       
    private void initComponents() {

        statisticsGlobalTitle = new javax.swing.JLabel();
        timeButton = new javax.swing.JButton();
        pointsButton = new javax.swing.JButton();
        returnMenuFromStatistics = new javax.swing.JButton();
        statisticsLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        userData = new javax.swing.JLabel();
        userData.setText(TablePrefixSuffix.tablePrefix + DataBaseLoader.printUserData("") + TablePrefixSuffix.tableSuffix);

        //Make scrollPane background transparent
        jScrollPane3.getViewport().setOpaque(false);
        jScrollPane3.setOpaque(false);

        timeButton.setActionCommand("time");
        pointsButton.setActionCommand("points");
        returnMenuFromStatistics.setActionCommand("return");

        this.setBackground(new java.awt.Color(45, 47, 48));
        this.setPreferredSize(new java.awt.Dimension(730, 515));

        statisticsGlobalTitle.setFont(new java.awt.Font("ROG Fonts", 0, 36)); // NOI18N
        statisticsGlobalTitle.setForeground(new java.awt.Color(102, 51, 255));
        statisticsGlobalTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statisticsGlobalTitle.setText("STATISTICHE GLOBALI");

        timeButton.setText("TEMPO DI GIOCO");
        timeButton.setEnabled(true);
        timeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        timeButton.addActionListener(this);

        pointsButton.setText("PUNTI GUADAGNATI");
        pointsButton.setEnabled(true);
        pointsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pointsButton.addActionListener(this);

        returnMenuFromStatistics.setText("Indietro");
        returnMenuFromStatistics.setEnabled(true);
        returnMenuFromStatistics.addActionListener(this);

        statisticsLabel.setForeground(new java.awt.Color(255, 255, 255));
        statisticsLabel.setText("Ordina la classifica in base a: ");

        jScrollPane3.setBackground(new java.awt.Color(45, 47, 48));
        jScrollPane3.setBorder(null);
        jScrollPane3.setForeground(new java.awt.Color(45, 47, 48));
        jScrollPane3.setOpaque(false);

        userData.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        userData.setForeground(new java.awt.Color(255, 255, 255));
        userData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jScrollPane3.setViewportView(userData);

        javax.swing.GroupLayout statisticsPanelLayout = new javax.swing.GroupLayout(this);
        this.setLayout(statisticsPanelLayout);
        statisticsPanelLayout.setHorizontalGroup(
            statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statisticsGlobalTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statisticsPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(returnMenuFromStatistics))
            .addGroup(statisticsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(statisticsPanelLayout.createSequentialGroup()
                        .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(statisticsPanelLayout.createSequentialGroup()
                                .addComponent(timeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(pointsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(statisticsLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        statisticsPanelLayout.setVerticalGroup(
            statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statisticsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statisticsGlobalTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statisticsLabel)
                .addGap(18, 18, 18)
                .addGroup(statisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pointsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(returnMenuFromStatistics)
                .addContainerGap())
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "points":
                userData.setText(TablePrefixSuffix.tablePrefix
                                + DataBaseLoader.printUserData("points")
                                + TablePrefixSuffix.tableSuffix);
            break;
            case "time":
                userData.setText(TablePrefixSuffix.tablePrefix
                                + DataBaseLoader.printUserData("time")
                                + TablePrefixSuffix.tableSuffix);
            break;
            case "return":
                GameWindow.getInstance().updatePanel(new MenuPanel());
            break;
        }
    }

}

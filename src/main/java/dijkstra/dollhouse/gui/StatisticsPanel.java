package dijkstra.dollhouse.gui;

import dijkstra.dollhouse.DataBaseLoader;
import dijkstra.dollhouse.TablePrefixSuffix;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;

/**
 * Class for statistics panel.
 */
public class StatisticsPanel extends JPanel implements ActionListener {

  private JScrollPane scrollPane;
  private JButton pointsButton;
  private JButton returnMenuFromStatistics;
  private JLabel statisticsGlobalTitle;
  private JLabel statisticsLabel;
  private JButton timeButton;
  private JLabel userData;

  /**
   * Public Constructor.
   */
  public StatisticsPanel() {
    try {
      DataBaseLoader.initializeDbConnection();
      DataBaseLoader.createTable();
      initComponents();
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(this, e,
                                    "Connessione al database fallita!", JOptionPane.ERROR_MESSAGE);
      GameWindow.getInstance().updatePanel(new MenuPanel());
    }
  }

  private void initComponents() {

    statisticsGlobalTitle = new JLabel();
    timeButton = new JButton();
    pointsButton = new JButton();
    returnMenuFromStatistics = new JButton();
    statisticsLabel = new JLabel();
    scrollPane = new JScrollPane();
    userData = new JLabel();
    try {
      userData.setText(TablePrefixSuffix.tablePrefix + DataBaseLoader.printUserData("")
                      + TablePrefixSuffix.tableSuffix);
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(this, e,
                                    "Non è possibile accedere ai dati!", JOptionPane.ERROR_MESSAGE);
      GameWindow.getInstance().updatePanel(new MenuPanel());
    }

    //Make scrollPane background transparent
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setOpaque(false);

    timeButton.setActionCommand("time");
    pointsButton.setActionCommand("points");
    returnMenuFromStatistics.setActionCommand("return");

    this.setBackground(new Color(45, 47, 48));
    this.setPreferredSize(new Dimension(730, 515));

    statisticsGlobalTitle.setFont(new Font("ROG Fonts", 0, 36)); // NOI18N
    statisticsGlobalTitle.setForeground(new Color(102, 51, 255));
    statisticsGlobalTitle.setHorizontalAlignment(SwingConstants.CENTER);
    statisticsGlobalTitle.setText("STATISTICHE GLOBALI");

    timeButton.setText("TEMPO DI GIOCO");
    timeButton.setEnabled(true);
    timeButton.setHorizontalTextPosition(SwingConstants.CENTER);
    timeButton.addActionListener(this);

    pointsButton.setText("PUNTI GUADAGNATI");
    pointsButton.setEnabled(true);
    pointsButton.setHorizontalTextPosition(SwingConstants.CENTER);
    pointsButton.addActionListener(this);

    returnMenuFromStatistics.setText("Indietro");
    returnMenuFromStatistics.setEnabled(true);
    returnMenuFromStatistics.addActionListener(this);

    statisticsLabel.setForeground(new Color(255, 255, 255));
    statisticsLabel.setText("Ordina la classifica in base a: ");

    scrollPane.setBackground(new Color(45, 47, 48));
    scrollPane.setBorder(null);
    scrollPane.setForeground(new Color(45, 47, 48));
    scrollPane.setOpaque(false);

    userData.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
    userData.setForeground(new Color(255, 255, 255));
    userData.setHorizontalAlignment(SwingConstants.CENTER);
    scrollPane.setViewportView(userData);

    GroupLayout statisticsPanelLayout = new GroupLayout(this);
    this.setLayout(statisticsPanelLayout);
    statisticsPanelLayout.setHorizontalGroup(
        statisticsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addComponent(statisticsGlobalTitle, GroupLayout.DEFAULT_SIZE,
                      GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(GroupLayout.Alignment.TRAILING, statisticsPanelLayout.createSequentialGroup()
          .addGap(0, 0, Short.MAX_VALUE)
          .addComponent(returnMenuFromStatistics))
        .addGroup(statisticsPanelLayout.createSequentialGroup()
          .addContainerGap()
          .addGroup(statisticsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(statisticsPanelLayout.createSequentialGroup()
              .addGroup(statisticsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(statisticsPanelLayout.createSequentialGroup()
                  .addComponent(timeButton, GroupLayout.PREFERRED_SIZE,
                                160, GroupLayout.PREFERRED_SIZE)
                  .addGap(12, 12, 12)
                  .addComponent(pointsButton, GroupLayout.PREFERRED_SIZE,
                                160, GroupLayout.PREFERRED_SIZE))
                .addComponent(statisticsLabel))
              .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(scrollPane, GroupLayout.Alignment.TRAILING))
          .addContainerGap())
    );
    statisticsPanelLayout.setVerticalGroup(
        statisticsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(GroupLayout.Alignment.TRAILING, statisticsPanelLayout.createSequentialGroup()
          .addContainerGap()
          .addComponent(statisticsGlobalTitle, GroupLayout.PREFERRED_SIZE,
                        85, GroupLayout.PREFERRED_SIZE)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
          .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
          .addComponent(statisticsLabel)
          .addGap(18, 18, 18)
          .addGroup(statisticsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(pointsButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
            .addComponent(timeButton, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
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
        try {
          userData.setText(TablePrefixSuffix.tablePrefix
                          + DataBaseLoader.printUserData("points")
                          + TablePrefixSuffix.tableSuffix);
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(this, e,
                                    "Non è possibile accedere ai dati!", JOptionPane.ERROR_MESSAGE);
          GameWindow.getInstance().updatePanel(new MenuPanel());
        }
        break;
      case "time":
        try {
          userData.setText(TablePrefixSuffix.tablePrefix
                          + DataBaseLoader.printUserData("time")
                          + TablePrefixSuffix.tableSuffix);
        } catch (SQLException e1) {
          JOptionPane.showMessageDialog(this, e,
                                    "Non è possibile accedere ai dati!", JOptionPane.ERROR_MESSAGE);
          GameWindow.getInstance().updatePanel(new MenuPanel());
        }
        break;
      case "return":
        GameWindow.getInstance().updatePanel(new MenuPanel());
        break;
      default:
        break;
    }
  }

}
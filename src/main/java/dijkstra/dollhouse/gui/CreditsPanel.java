package dijkstra.dollhouse.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

/**
 * Class for the credits Panel.
 */
public class CreditsPanel extends JPanel {

  private JLabel gameCredits;
  private JButton returnMenuFromCredits;   

  public CreditsPanel() {
    initComponents();
  }

  private void initComponents() {
    returnMenuFromCredits = new JButton();
    gameCredits = new JLabel();

    this.setBackground(new java.awt.Color(45, 47, 48));
    this.setForeground(new Color(187, 187, 187));
    this.setPreferredSize(new java.awt.Dimension(730, 515));

    returnMenuFromCredits.setText("Indietro");
    returnMenuFromCredits.setEnabled(true);
    returnMenuFromCredits.addActionListener((ActionEvent event) ->
                                            GameWindow.getInstance().updatePanel(new MenuPanel()));

    gameCredits.setBackground(new java.awt.Color(255, 255, 255));
    gameCredits.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
    gameCredits.setForeground(new java.awt.Color(255, 255, 255));
    gameCredits.setText("<html>\n<h1 align=\"center\">Doll House </h1><br>"
                        + "\nBenvenuto nell'avventura tetra e misteriosa di \"DollHouse\"."
                        + " In questa avventura testuale, ti ritrovi legato in una stanza"
                        + " buia,\ncircondato da gabbie contenenti sinistre bambole."
                        + " <br>Davanti alla porta, un'inquietante figura ti rivela la verità:"
                        + " sei nella casa della strega Aradia, una temuta strega che ha"
                        + " trasformato più di 200 persone in bambole. "
                        + "L'unico modo per fuggire è uccidere la strega, che si"
                        + "trova al primo piano della casa, in questo modo riuscirai anche ad"
                        + "annullare l'incantesimo facendo tornare alla normalità tutte le"
                        + "sue vittime.<br>\n<br>\nLa casa si estende oltre lo scantinato"
                        + "e il piano terra, includendo un altro piano ricco di misteri."
                        + "Man mano che esplori, incontri bambole che ancora conservano la"
                        + "capacità di parlare, sfruttando i loro suggerimenti e combinando"
                        + "gli oggetti che troverai nella casa, potrai uccidere la strega. "
                        + "<br><br>\n\nPreparati ad affrontare l'orrore, risolvi enigmi"
                        + "e sopravvivi nella casa della strega Aradia!<br><br>\n\n"
                        + "L'avventura testuale 'Doll House' è stata creata per l'esame"
                        + "di Metodi Avanzati di Programmazione (MAP), da un team di 3 persone:"
                        + "Nicolò Resta, Daniele Tonti, Natale Mastrogiacomo.\n\n</html>");

    GroupLayout creditsPanelLayout = new GroupLayout(this);
    this.setLayout(creditsPanelLayout);
    creditsPanelLayout.setHorizontalGroup(
        creditsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
          .addGroup(creditsPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addGroup(creditsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(creditsPanelLayout.createSequentialGroup()
                    .addGap(0, 533, Short.MAX_VALUE)
                    .addComponent(returnMenuFromCredits))
                .addComponent(gameCredits, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addContainerGap())
    );
    creditsPanelLayout.setVerticalGroup(
        creditsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(creditsPanelLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(gameCredits, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(returnMenuFromCredits))
    );
  }    
}

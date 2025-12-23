package blackjack.gui;

import javax.swing.*;
import blackjack.controller.BlackjackController;
import blackjack.model.Table;
import blackjack.model.player.Player;

import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.ActionEvent;

/**
 * Bouton permettant de démarrer une nouvelle partie de blackjack.
 * Lorsque cliqué, ouvre une fenêtre de dialogue pour que le joueur saisisse sa mise,
 * puis démarre la partie une fois la mise validée.
 */
public class StartButton extends JButton implements ActionListener{
    /**
     * Contrôleur gérant la logique du jeu.
     */
    private BlackjackController controller;
    
    /**
     * Construit un nouveau bouton de démarrage.
     * 
     * @param controller le contrôleur du jeu à notifier lors du clic
     */
    public StartButton(BlackjackController controller) {
        super("Start Game");
        this.controller = controller;
        this.addActionListener(this);
    }

    /**
     * Gère l'action de clic sur le bouton.
     * Si aucune partie n'est en cours, ouvre une fenêtre pour saisir la mise
     * en respectant les limites de mise (betMin, betMax) et le pot du joueur.
     * Une fois la mise validée, démarre la partie.
     * 
     * @param e l'événement de clic sur le bouton
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!controller.getGameStarted()){
            JFrame f = new JFrame("Mise");
            f.setSize(400, 150);

            JPanel panel = new JPanel();
            JLabel label = new JLabel("Combien voulez-vous miser ? \n");
            JTextField input = new JTextField(10);
            JButton ok = new JButton("OK");

            int betMin = this.controller.getTable().getBetMin();
            int betMaxTable = this.controller.getTable().getBetMax();

            int pot = controller.getTable().getPlayers().get(1).getPot();

            final int betMax = Math.min(betMaxTable, pot);

            JLabel betMinLabel = new JLabel("Mise minimale : " + betMin);
            JLabel betMaxLabel = new JLabel("Mise maximale : " + betMax);
            
            panel.add(label);
            panel.add(betMinLabel);
            panel.add(betMaxLabel);
            panel.add(input);
            panel.add(ok);

            f.add(panel);
            f.setVisible(true);

            ok.addActionListener(ev -> { this.controller.checkBet(f, input, betMax); });
        }
    }    
}
package blackjack.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import blackjack.controller.*;
import javax.swing.JButton;

/**
 * Bouton permettant au joueur de tirer une carte supplémentaire (Hit).
 * Cette action ajoute une carte à la main du joueur et peut le faire dépasser 21.
 */
public class HitButton extends JButton implements ActionListener{
    /**
     * Contrôleur gérant la logique du jeu.
     */
    private BlackjackController controller;
    
    /**
     * Construit un nouveau bouton "Piocher".
     * 
     * @param controller le contrôleur du jeu à notifier lors du clic
     */
    public HitButton(BlackjackController controller){
        super("piocher");
        this.controller = controller;
        this.addActionListener(this);
    }
    
    /**
     * Gère l'action de clic sur le bouton.
     * Demande au contrôleur de faire tirer une carte au joueur actuel.
     * 
     * @param evt l'événement de clic sur le bouton
     */
    @Override
    public void actionPerformed(ActionEvent evt){
        this.controller.playerHit();
    }
}
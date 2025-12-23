package blackjack.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import blackjack.controller.*;

/**
 * Bouton permettant au joueur de doubler sa mise (Double Down).
 * Cette action double la mise du joueur, lui donne une carte supplémentaire unique,
 * puis termine automatiquement son tour. Le doublement n'est possible qu'au début
 * du tour, avant toute autre action.
 */
public class DoubleDumpPanel extends JButton implements ActionListener{
   
    /**
     * Contrôleur gérant la logique du jeu.
     */
   	private BlackjackController controller;

    /**
     * Construit un nouveau bouton "Double Dump".
     * 
     * @param controller le contrôleur du jeu à notifier lors du clic
     */
  	public DoubleDumpPanel(BlackjackController controller){
		super("Double Dump");
		this.controller = controller;
		this.addActionListener(this);
	}
	
    /**
     * Gère l'action de clic sur le bouton.
     * Demande au contrôleur d'exécuter l'action de doublement de mise
     * pour le joueur actuel (si autorisé).
     * 
     * @param evt l'événement de clic sur le bouton
     */
	@Override
	public void actionPerformed(ActionEvent evt){
		this.controller.setDoubleDump();
	}
}
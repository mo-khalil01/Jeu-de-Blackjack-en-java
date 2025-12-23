package blackjack.gui;

import blackjack.model.*;
import blackjack.model.player.*;
import blackjack.controller.*;
import java.util.*;

import javax.swing.JOptionPane;

/**
 * Point d'entrée principal de l'application de blackjack en mode graphique.
 * Cette classe initialise les joueurs de base (croupier et joueur humain),
 * crée la table de jeu et lance le contrôleur avec l'interface graphique.
 */
public class Main {
    /**
     * Méthode principale qui lance l'application.
     * Crée une table avec un croupier et un joueur humain,
     * puis démarre le contrôleur qui gère le jeu et affiche l'interface graphique.
     * 
     * @param args arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        Dealer dealer = new Dealer("Dealer", 1000);
        players.add(dealer);
        Human player1 = new Human("You", 2000);
        players.add(player1);

        Table table = new Table(players, 10, 50);
        BlackjackController controller = new BlackjackController(table); 
    }
}
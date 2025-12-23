package blackjack.gui;

import java.util.*;

import blackjack.model.player.*;
import blackjack.model.*;

/**
 * Point d'entrée pour lancer une partie de blackjack en mode terminal (console).
 * Cette version textuelle du jeu permet de jouer sans interface graphique,
 * avec affichage en ligne de commande. Utile pour les tests et le débogage.
 */
public class LauncherTerminal {
    /**
     * Méthode principale qui lance l'application en mode terminal.
     * Crée une table avec un croupier, un joueur humain et un bot,
     * puis lance 5 manches consécutives en mode console.
     * 
     * @param args arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        
        List<Player> players = new ArrayList<Player>();
        Dealer dealer = new Dealer("Dealer", 10000);
        Human humanPlayer = new Human("Human", 500);
        Bot ia = new Bot("IA");

        players.add(dealer);
        players.add(humanPlayer);
        players.add(ia);
        
        Table table = new Table(players, 5, 10);
        for(int i=0; i<5; i++){
            System.out.println("===== Round "+ i +" =====\n");
            runRound(table);
        }
    }
    
    /**
     * Lance une manche complète de blackjack en mode console.
     * Gère toutes les phases du jeu :
     * - Collecte des mises de tous les joueurs
     * - Distribution initiale des cartes
     * - Vérification d'un blackjack naturel
     * - Tours des joueurs (avec possibilité de doubler ou de tirer)
     * - Tour du croupier
     * - Détermination des gagnants
     * - Distribution des gains et pertes
     * - Réinitialisation pour la manche suivante
     * 
     * @param table la table de jeu sur laquelle lancer la manche
     */
	public static void runRound(Table table){
        List<Player> players = table.getPlayers();
        table.placeAllBet();
        table.initRound();

        if(!table.blackjack()){
            System.out.println(table.vueSituation(false));

            for(int i = 1; i < players.size(); i++){
                Player player = players.get(i);
                if(player.doubleDump()){
                    table.doubleDumpSituation(player);
                    System.out.println(table.vueSituation(false));
                }else{
                    table.classicSituation(player);
                }
            }
            Dealer dealer = (Dealer) players.get(0);
            table.classicSituation(dealer);
        }else{
            System.out.println(" === Victoire au blackjack ! ===");
        }
        System.out.println("---- fin de la manche ----");
        System.out.println(table.vueSituation(true));

        table.findWinners();
        if(table.getWinners().isEmpty()){
            System.out.println("Le dealer gagne face à tout le monde !");
        }
        else{
            System.out.println("Les vainqueurs sont : ");
            for(Player player : table.getWinners()){
                System.out.println(player+" ");
            }
        }
        table.dealCoins();
        table.resetRound();
    }
}
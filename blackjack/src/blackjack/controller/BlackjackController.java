package blackjack.controller;

import blackjack.model.*;
import blackjack.model.player.*;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JTextField;

import blackjack.gui.GraphicView;;

/**
 * Contrôleur principal gérant le déroulement d'une partie de blackjack.
 * Cette classe implémente le pattern MVC (Modèle-Vue-Contrôleur) en tant que contrôleur.
 * Elle coordonne les interactions entre le modèle (Table, Player) et la vue (GraphicView),
 * gère la logique du jeu (tours de jeu, actions des joueurs, fin de partie) et
 * orchestre le déroulement complet d'une manche de blackjack.
 */
public class BlackjackController {
    /**
     * La table de jeu contenant tous les joueurs et l'état de la partie.
     */
    private Table table;

    /**
     * Indique si une partie est actuellement en cours.
     */
    private boolean gameStarted = false;

    /**
     * Indique si le doublement de mise est encore possible pour le joueur actuel.
     * Le doublement n'est autorisé qu'au premier tour du joueur, avant toute action.
     */
    private boolean doubleDump = true;

    /**
     * Indique si la partie est terminée (tous les tours joués, gagnants déterminés).
     */
    private boolean isFinished = false;

    /**
     * Indique si un bot participe à la partie.
     */
    private boolean hasBotPlayer = false;

    /**
     * Index du joueur dont c'est actuellement le tour (1 = premier joueur humain).
     */
    private int playerIndex;

    /**
     * Référence vers la vue graphique de la partie.
     */
    private GraphicView view;

    /**
     * Construit un nouveau contrôleur de blackjack.
     * Initialise la table de jeu, propose à l'utilisateur d'ajouter un bot,
     * et crée la vue graphique associée.
     * 
     * @param table la table de jeu à contrôler
     */
    public BlackjackController(Table table) {
        this.table = table;
        this.playerIndex = 1;

        int response = JOptionPane.showConfirmDialog(
            null,
            "Voulez-vous jouer avec un bot ?",
            "Configuration de la partie",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        // Si l'utilisateur clique sur "Oui"
        if (response == JOptionPane.YES_OPTION) {
            Bot bot1 = new Bot("Bot_1", 2000);
            this.table.getPlayers().add(bot1);
            this.setHasBotPlayer(true);
        }
        this.view = new GraphicView(table, this);
    }

    /**
     * Retourne la table de jeu gérée par ce contrôleur.
     * 
     * @return la table de jeu
     */
    public Table getTable(){
        return this.table;
    }

    /**
     * Indique si une partie est actuellement en cours.
     * 
     * @return true si la partie a démarré, false sinon
     */
    public boolean getGameStarted(){
        return this.gameStarted;
    }

    /**
     * Indique si la partie est terminée.
     * 
     * @return true si la partie est terminée, false sinon
     */
    public boolean getIsFinished(){
        return this.isFinished;
    }

    /**
     * Indique si un bot participe à la partie.
     * 
     * @return true si un bot est présent, false sinon
     */
    public boolean getHasBotPlayer(){
        return this.hasBotPlayer;
    }

    /**
     * Modifie l'état de présence d'un bot dans la partie.
     * 
     * @param hasBot true si un bot est présent, false sinon
     */
    public void setHasBotPlayer(boolean hasBot){
        this.hasBotPlayer = hasBot;
    }

    /**
     * Permet au joueur actuel de doubler sa mise.
     * Le doublement double la mise du joueur, lui donne une carte supplémentaire unique,
     * puis passe automatiquement au joueur suivant. Cette action n'est possible qu'une fois
     * par tour et uniquement avant toute autre action.
     */
    public void setDoubleDump(){
        if (this.doubleDump && this.gameStarted) {
            this.doubleDump = false;
            this.table.doubleDumpSituation(this.table.getPlayers().get(this.playerIndex));
            this.nextPlayerTurn();
        }
    }

    /**
     * Démarre une nouvelle partie de blackjack.
     * Initialise une nouvelle manche, distribue 2 cartes à chaque joueur,
     * et vérifie s'il y a un blackjack naturel (21 dès le début).
     * Si c'est le cas, termine immédiatement la partie.
     */
    public void startGame() {
        if(!this.gameStarted){
            
            this.isFinished = false;
            this.gameStarted = true;

            // this.table.resetRound();
            this.table.initRound();
            this.playerIndex = 1;

            if(this.table.blackjack()){
                System.out.println("BLACKJACK NATUREL");
                this.endGame();
            }
        }
    }

    /**
     * Permet à un joueur de tirer une carte supplémentaire (Hit).
     * Désactive la possibilité de doubler après cette action.
     * Si le joueur dépasse 21 points, passe automatiquement au joueur suivant.
     * 
     * @param player le joueur qui tire une carte
     */
    public void hit(Player player){
        if(this.gameStarted){
            this.doubleDump = false;
            this.table.giveCard(player);
            if (player.getHandValue() > 21) {
                this.nextPlayerTurn();
            }
        }
    }

    /**
     * Permet à un joueur de rester (Stand) sans tirer de carte supplémentaire.
     * Désactive la possibilité de doubler et passe au joueur suivant.
     * 
     * @param player le joueur qui reste
     */
    public void stand(Player player) {
        this.doubleDump = false;
        if(this.gameStarted){
            this.nextPlayerTurn();
        }
    }

    /**
     * Passe au tour du joueur suivant dans l'ordre.
     * Si tous les joueurs ont joué, lance le tour du croupier.
     * Si le joueur suivant est un bot, exécute automatiquement son tour.
     */
    private void nextPlayerTurn() {
        this.playerIndex += 1;
        if(this.playerIndex >= this.table.getPlayers().size()) {
            this.dealerTurn();
        } 
        else{
            Player nextPlayer = this.table.getPlayers().get(this.playerIndex);
            
            if(nextPlayer instanceof Bot) {
                this.botTurn((Bot) nextPlayer);
            }
        }
    }

    /**
     * Gère automatiquement le tour d'un bot.
     * Le bot tire des cartes selon sa stratégie (définie dans la classe Bot)
     * jusqu'à ce qu'il décide de s'arrêter ou dépasse 21.
     * Passe ensuite au joueur suivant.
     * 
     * @param bot le bot dont c'est le tour
     */
    private void botTurn(Bot bot){
        while(bot.continueChoice() && bot.getHandValue() <= 21){
            this.table.giveCard(bot);
        }
        this.nextPlayerTurn();
    }

    /**
     * Gère le tour du croupier (dealer).
     * Selon les règles du blackjack, le croupier tire des cartes
     * jusqu'à atteindre au moins 17 points.
     * Une fois le tour terminé, déclenche la fin de partie.
     */
    private void dealerTurn(){
        Dealer dealer = (Dealer) this.table.getPlayers().get(0);

        while(dealer.continueChoice()){
            this.table.giveCard(dealer);
        }
        this.endGame();
    }

    /**
     * Termine la partie et effectue toutes les opérations de clôture.
     * Détermine les gagnants, révèle la main complète du croupier,
     * distribue les gains et pertes, affiche les résultats,
     * puis réinitialise la table pour une nouvelle manche.
     */
    private void endGame() {
        this.isFinished = true;
        this.table.findWinners();
        Dealer dealer = (Dealer) this.table.getPlayers().get(0);
        dealer.setVisibleHandValue(dealer.getHandValue());
        List<Player> winners = this.table.getWinners();
        if(winners.isEmpty()){
            System.out.println("Le dealer gagne cette manche ! avec "+this.table.getPlayers().get(0).getHandValue());
        } 
        else{
            System.out.print("Gagnant(s) : ");
            for (Player winner : winners) {
                System.out.print(winner.getName() + " ");
            }
        }
        
        this.table.dealCoins();
        
        System.out.println("\n=== POTS DES JOUEURS ===");
        for(int i = 1; i < this.table.getPlayers().size(); i++){
            Player p = this.table.getPlayers().get(i);
            System.out.println(p.getName() + " : " + p.getPot() + " jetons");
        }
        
        this.gameStarted = false;
        this.doubleDump = true;
        this.view.popWinner();
        this.table.resetRound();

        // this.isFinished = false;
    }

    /**
     * Gère l'action "Hit" (tirer une carte) pour le joueur dont c'est le tour.
     * Vérifie que la partie est en cours et que c'est bien le tour d'un joueur
     * avant d'exécuter l'action.
     */
    public void playerHit(){
        if(this.gameStarted && this.playerIndex < this.table.getPlayers().size()){
            this.hit(this.table.getPlayers().get(this.playerIndex));
        }
    }

    /**
     * Gère l'action "Stand" (rester) pour le joueur dont c'est le tour.
     * Vérifie que la partie est en cours et que c'est bien le tour d'un joueur
     * avant d'exécuter l'action.
     */
    public void playerStand(){
        if(this.gameStarted && this.playerIndex < this.table.getPlayers().size()){
            this.stand(this.table.getPlayers().get(this.playerIndex)); 
        }
    }

    /**
     * Vérifie et valide la mise saisie par le joueur avant de démarrer la partie.
     * Contrôle que la mise est un nombre valide dans les limites autorisées (betMin, betMax).
     * Si un bot est présent, génère également sa mise automatiquement.
     * Une fois validée, enregistre les mises et démarre la partie.
     * 
     * @param f la fenêtre de saisie de mise à fermer après validation
     * @param input le champ de texte contenant la mise saisie
     * @param betMax la mise maximale autorisée pour ce joueur
     */
    public void checkBet(JFrame f, JTextField input, int betMax){
        String miseStr = input.getText();
        int betMin = this.table.getBetMin();
        
        try {
            int mise = Integer.parseInt(miseStr);
                    
            if (mise < betMin || mise > betMax) {
                JOptionPane.showMessageDialog(f, "Veuillez entrer une mise entre " + betMin + " et " + betMax + ".");
                return;
            }
            if(this.table.getPlayers().size() > 2){
                int botBet = this.table.getPlayers().get(2).placeBet(betMin, this.table.getBetMax());
                Map<Player, Integer> bet = new HashMap<>();
                bet.put(this.table.getPlayers().get(1), mise);
                bet.put(this.table.getPlayers().get(2), botBet);
                this.table.setBets(bet);
                this.startGame();

            } else {
                Map<Player, Integer> bet = this.table.getBets();
                bet.put(this.table.getPlayers().get(1), mise);
                this.table.setBets(bet);
                this.startGame();
            }   
            f.dispose(); // ferme la fenêtre une fois validé
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(f, "Veuillez entrer un nombre valide.");
        }
    }
}
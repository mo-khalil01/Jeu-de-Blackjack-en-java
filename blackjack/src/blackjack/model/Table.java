package blackjack.model;
import java.util.*;

//import javax.smartcardio.Card;

import card.game.*;
import blackjack.model.player.*;
import blackjack.util.Observateur;
import blackjack.util.ModeleObservable;
import card.util.FactoryCard;

/**
 * Représente une table de blackjack gérant une partie complète.
 * Cette classe orchestre le déroulement du jeu, les mises, la distribution des cartes
 * et la détermination des gagnants. Elle implémente le pattern Observable pour notifier
 * les vues des changements d'état.
 */
public class Table extends ModeleObservable {

    /**
     * Liste des joueurs à la table (croupier + joueurs humains/bots).
     */
	private List<Player> players;
    
    /**
     * Le paquet de cartes utilisé pour la partie.
     */
	private Deck cardGame;
    
    /**
     * Mise minimale autorisée à la table.
     */
	private int betMin;
    
    /**
     * Mise maximale autorisée à la table.
     */
	private int betMax;
    
    /**
     * Liste des gagnants de la manche en cours.
     */
    private List<Player> winners = new ArrayList<>();
    
    /**
     * Map associant chaque joueur à sa mise pour la manche en cours.
     */
    private Map<Player, Integer> bets = new HashMap<>();

    /**
     * Construit une nouvelle table de blackjack avec les paramètres spécifiés.
     * 
     * @param players liste des joueurs à la table (le croupier doit être en première position)
     * @param betMin mise minimale autorisée à la table
     * @param betMax mise maximale autorisée à la table
     */
	public Table(List<Player> players, int betMin, int betMax) {
		this.players= players;
		
		//this.cardGame = Factory;
		this.betMin = betMin;
		this.betMax = betMax;
	} 

    /**
     * Ajoute un observateur à la table pour recevoir les notifications de changement d'état.
     * 
     * @param o l'observateur à ajouter
     */
    public void addObservateur(Observateur o) {
        super.ajouterObservateur(o);        
    }

    /**
     * Retourne la liste des joueurs présents à la table.
     * 
     * @return la liste des joueurs
     */
	public List<Player> getPlayers() {
		return this.players;
	}
	
    /**
     * Retourne le paquet de cartes utilisé pour la partie.
     * 
     * @return le paquet de cartes
     */
	public Deck getCardGame() {
		return this.cardGame;
	}
	
    /**
     * Retourne la mise minimale autorisée à la table.
     * 
     * @return la mise minimale
     */
	public int getBetMin() {
		return this.betMin;
	}

    /**
     * Modifie la mise minimale de la table.
     * 
     * @param bmin la nouvelle mise minimale
     */
	public void setBetMin(int bmin) {
		this.betMin = bmin;
	}

    /**
     * Retourne la mise maximale autorisée à la table.
     * 
     * @return la mise maximale
     */
	public int getBetMax() {
		return this.betMax;
	}

    /**
     * Modifie la mise maximale de la table.
     * 
     * @param bmax la nouvelle mise maximale
     */
	public void setBetMax(int bmax) {
		this.betMax = bmax;
	}
    
    /**
     * Retourne la liste des gagnants de la manche en cours.
     * 
     * @return la liste des gagnants
     */
    public List<Player> getWinners() {
        return winners;
    }

    /**
     * Modifie la liste des gagnants de la manche.
     * 
     * @param winers la nouvelle liste des gagnants
     */
    public void setWinners(List<Player> winers) {
        this.winners = winers;
    }

    /**
     * Retourne la map associant chaque joueur à sa mise.
     * 
     * @return la map des mises
     */
    public Map<Player, Integer> getBets() {
        return bets;
    }

    /**
     * Modifie la map des mises et notifie les observateurs.
     * 
     * @param bets la nouvelle map des mises
     */
    public void setBets(Map<Player, Integer> bets) {
        this.bets = bets;
        notifierObservateurs();
    }
    
	/**
     * Distribue une carte du paquet au joueur spécifié et notifie les observateurs.
     * 
     * @param player le joueur qui reçoit la carte
     */
	public void giveCard(Player player) {
		Card cardToGive = this.cardGame.draw();
		player.addNewCard(cardToGive);
        notifierObservateurs();
	}

    /**
     * Initialise une nouvelle manche de jeu.
     * Crée un nouveau paquet de 52 cartes, le mélange, le coupe,
     * puis distribue 2 cartes à chaque joueur. Notifie les observateurs.
     */
    public void initRound(){
        this.cardGame = FactoryCard.create52Deck();
        this.cardGame.shuffle();
        this.cardGame.cut();
        for(int i = 0; i < 2; i++) {
            for(Player p : this.players){
                this.giveCard(p);
            }
		}
        notifierObservateurs();
    }

    /**
     * Réinitialise la table pour une nouvelle manche.
     * Vide les mains de tous les joueurs, remet leurs valeurs à zéro,
     * et efface les listes des gagnants et des mises. Notifie les observateurs.
     */
    public void resetRound(){
        for(Player p : this.players){
            p.clearHand();
            p.setHandValue(0);
        }
        this.winners.clear();
        this.bets.clear();
        notifierObservateurs();
    }
 
    /**
     * Gère la situation de doublement de mise pour un joueur.
     * Double la mise du joueur et lui distribue une carte supplémentaire unique.
     * Notifie les observateurs.
     * 
     * @param player le joueur qui double sa mise
     */
    public void doubleDumpSituation(Player player){
        this.bets.replace(player, this.bets.get(player)*2);
        this.giveCard(player);
        notifierObservateurs();
    }
 
    /**
     * Gère la situation classique d'un joueur pendant son tour.
     * Le joueur tire des cartes successivement tant qu'il n'a pas dépassé 21
     * et qu'il souhaite continuer. Affiche la situation après chaque carte tirée.
     * 
     * @param player le joueur dont c'est le tour
     */
    public void classicSituation(Player player){
        while(player.getHandValue() <= 21 && player.continueChoice()) {
            this.giveCard(player);
            System.out.println(this.vueSituation(false));
            // notifierObservateurs();
        }
    }

    /**
     * Génère une représentation textuelle de l'état actuel de la table.
     * 
     * @param all si true, affiche tous les joueurs y compris le croupier avec toutes ses cartes.
     *            si false, masque une carte du croupier et affiche uniquement les autres joueurs
     * @return une chaîne décrivant la main et les valeurs de chaque joueur
     */
    public String vueSituation(boolean all){
        String situation = " ";
        int x = 0;
        if(!all){
            x = 1;
            Dealer dealer = (Dealer) this.players.get(0);
            situation += "main de "+dealer.getName() +"("+dealer.getVisibleHandValue()+") -> "+dealer.getVisibleHand()+"\n";
        }
        for(int i = x; i<this.players.size(); i++){
            Player p = this.players.get(i);
            situation += "main de "+p.getName() +"(val:"+p.getHandValue()+" / pot:" +p.getPot()+ " ) -> "+p.getHand()+"\n";
        }
        return situation ;
    }

    /**
     * Collecte les mises de tous les joueurs (sauf le croupier).
     * Demande à chaque joueur de placer sa mise dans les limites betMin et betMax,
     * puis enregistre ces mises dans la map des paris.
     */
    public void placeAllBet(){
        for(int i=1; i<this.players.size(); i++){
            int bet = this.players.get(i).placeBet(this.betMin, this.betMax);
            this.bets.put(this.players.get(i), bet);
        }
    }

    /**
     * Distribue les gains et retire les mises perdues.
     * Pour chaque joueur ayant parié : ajoute sa mise à son pot s'il a gagné,
     * retire sa mise de son pot s'il a perdu. Notifie les observateurs.
     */
    public void dealCoins(){
        for(Player player: this.bets.keySet()){
            if(this.winners.contains(player)){
                player.winCoins(bets.get(player));
            }else{
                player.looseCoins(bets.get(player));
            }
        }
        notifierObservateurs();
    }

    /**
     * Vérifie si au moins un joueur a obtenu un blackjack (21 points).
     * 
     * @return true si au moins un joueur a 21 points, false sinon
     */
    public boolean blackjack(){
        for(Player player : this.players){
            if(player.getHandValue() == 21){
                return true; 
            }
        }
        return false;
    }

    /**
     * Détermine les gagnants de la manche selon les règles du blackjack.
     * Compare la main de chaque joueur (sauf le croupier) avec celle du croupier.
     * Un joueur gagne si :
     * - Il a un blackjack et bat le croupier (cas spécial)
     * - Il n'a pas dépassé 21 ET (le croupier a dépassé 21 OU le joueur a une meilleure main)
     * 
     * Les gagnants sont ajoutés à la liste winners.
     */
    public void findWinners() {
        int dealerHandValue = this.players.get(0).getHandValue();

        boolean blackjack = this.blackjack();
        // Parcours de tous les joueurs humains
        for (int i = 1; i < this.players.size(); i++) {
            int playerValue = this.players.get(i).getHandValue();

            
            boolean playerNotBusted = playerValue <= 21;
            boolean dealerBusted = dealerHandValue > 21;
            boolean playerBeatsDealer = playerValue > dealerHandValue;

            if(blackjack){
                // Si un joueur a un blackjack, il gagne automatiquement
                if(playerValue == 21 && playerBeatsDealer){
                    this.winners.add(this.players.get(i));
                }
            }

            // Condition unique : le joueur gagne si le dealer a busté ou s'il le bat
            else if (playerNotBusted && (dealerBusted || playerBeatsDealer)) {
                this.winners.add(this.players.get(i));
            }
        }
        // notifierObservateurs();
    }
}
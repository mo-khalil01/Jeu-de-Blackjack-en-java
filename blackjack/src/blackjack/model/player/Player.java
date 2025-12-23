package blackjack.model.player;

import card.game.*;
import card.util.FactoryCard;
import java.util.Scanner;
import java.util.Random;

/**
 * Classe abstraite représentant un joueur de blackjack.
 * Cette classe définit les attributs et comportements communs à tous les types de joueurs
 * (humain, bot, croupier). Les sous-classes doivent implémenter les stratégies spécifiques
 * de décision (continuer ou non, doubler la mise).
 */
public abstract class Player{
    /**
     * Nom du joueur.
     */
    private String name;
    
    /**
     * Montant d'argent disponible pour le joueur (son capital).
     */
    private int pot;
    
    /**
     * Main du joueur, représentée par un paquet de cartes.
     */
    public Deck hand = FactoryCard.create0Deck();
    
    /**
     * Valeur totale de la main du joueur.
     */
    private int handValue = 0; 

    /**
     * Construit un nouveau joueur avec un nom et un capital initial.
     * 
     * @param name le nom du joueur
     * @param pot le capital initial du joueur
     */
    public Player(String name, int pot){
        this.name = name;
        this.pot = pot;
    }

    /**
     * Construit un nouveau joueur avec un nom et un capital par défaut de 100.
     * 
     * @param name le nom du joueur
     */
    public Player(String name){
        this(name, 100);
    }

    /**
     * Retourne le nom du joueur.
     * 
     * @return le nom du joueur
     */
    public String getName(){
        return this.name;
    }
    
    /**
     * Retourne le capital actuel du joueur.
     * 
     * @return le montant du pot
     */
    public int getPot(){
        return this.pot;
    }

    /**
     * Retourne la main du joueur sous forme de paquet de cartes.
     * 
     * @return le paquet représentant la main
     */
    public Deck getHand(){
        return this.hand;
    }

    /**
     * Retourne la valeur totale de la main du joueur.
     * 
     * @return la valeur de la main
     */
    public int getHandValue(){
        return this.handValue;
    }

    /**
     * Modifie la valeur de la main du joueur.
     * 
     * @param value la nouvelle valeur de la main
     */
    public void setHandValue(int value){
        this.handValue = value;
    }

    /**
     * Vide la main du joueur en retirant toutes ses cartes.
     */
    public void clearHand(){
        this.hand.getDeck().clear();
    }

    /**
     * Ajoute une nouvelle carte à la main du joueur et met à jour sa valeur.
     * Gère automatiquement la valeur des As : si c'est un As et que la valeur
     * totale reste inférieure ou égale à 11, ajoute 10 points supplémentaires
     * (pour compter l'As comme 11 au lieu de 1).
     * 
     * @param card la carte à ajouter à la main
     */
    public void addNewCard(Card card){
        this.handValue += card.getValue();
        this.hand.addCard(card);
        if(card.getName()=="As" && this.handValue<=11){
            this.handValue += 10;
        }
    }

    /**
     * Méthode abstraite indiquant si le joueur souhaite continuer à tirer des cartes.
     * Chaque type de joueur (humain, bot, croupier) implémente sa propre stratégie.
     * 
     * @return true si le joueur souhaite continuer, false sinon
     */
    public abstract boolean continueChoice();

    /**
     * Demande au joueur de placer une mise dans les limites autorisées.
     * Implémentation par défaut : génère une mise aléatoire entre betMin et betMax,
     * limitée au capital disponible du joueur.
     * 
     * @param betMin la mise minimale autorisée
     * @param betMax la mise maximale autorisée
     * @return le montant de la mise placée
     */
    public int placeBet(int betMin, int betMax){
        Random rand = new Random();
        int betValue = Math.min(this.pot, rand.nextInt(betMax - betMin + 1) + betMin);
        return betValue;
    }

    /**
     * Détermine si le joueur souhaite doubler sa mise.
     * Implémentation par défaut : décision aléatoire (50/50).
     * Peut être redéfinie par les sous-classes pour implémenter des stratégies spécifiques.
     * 
     * @return true si le joueur double, false sinon
     */
    public boolean doubleDump(){
        Random rand = new Random();
        return rand.nextBoolean();
    }

    /**
     * Ajoute des gains au capital du joueur.
     * 
     * @param coins le montant à ajouter au pot
     */
    public void winCoins(int coins){
        this.pot += coins;
    }
    
    /**
     * Retire une mise perdue du capital du joueur.
     * 
     * @param coins le montant à retirer du pot
     */
    public void looseCoins(int coins){
        this.pot -= coins;
    }

    /**
     * Retourne une représentation textuelle du joueur.
     * 
     * @return le nom du joueur
     */
    @Override
    public String toString(){
        return this.name;
    }
}
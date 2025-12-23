package blackjack.model.player;
import card.game.Card;
import card.game.Deck;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.Random;

/**
 * Représente un joueur bot (intelligence artificielle) au blackjack.
 * Le bot applique une stratégie simple : il continue à tirer des cartes
 * tant que sa main vaut moins de 20 points, et double sa mise s'il a 11 points.
 */
public class Bot extends Player{
    
    /**
     * Construit un nouveau bot avec un nom et un capital initial.
     * 
     * @param name le nom du bot
     * @param pot le capital initial du bot
     */
    public Bot(String name, int pot){
        super(name, pot);
    }

    /**
     * Construit un nouveau bot avec un nom et un capital par défaut de 100.
     * 
     * @param name le nom du bot
     */
    public Bot(String name){
        super(name);
        
    }

    /**
     * Détermine si le bot souhaite continuer à tirer des cartes.
     * Stratégie : le bot continue tant que sa main vaut moins de 20 points.
     * 
     * @return true si la valeur de la main est inférieure à 20, false sinon
     */
    @Override
    public boolean continueChoice(){
        System.out.println("le bot continu ? ");
        if(super.getHandValue() < 20){
            System.out.println("OUI");
            return true;
        }
        System.out.println("NON");
        return false;
    }  

    /**
     * Détermine si le bot souhaite doubler sa mise.
     * Stratégie : le bot double uniquement s'il a exactement 11 points,
     * situation favorable pour tirer une carte à 10 points.
     * 
     * @return true si la valeur de la main est exactement 11, false sinon
     */
    @Override
    public boolean doubleDump(){
        if(this.getHandValue() == 11){
            return true;
        }
        return false;
    }

}
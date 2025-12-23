package blackjack.model.player;
import card.game.Card;
import card.game.Deck;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Représente le croupier (dealer) dans une partie de blackjack.
 * Le croupier a des règles spécifiques : il cache une de ses cartes au début,
 * et doit tirer des cartes jusqu'à atteindre au moins 17 points.
 */
public class Dealer extends Player{
    
    /**
     * Valeur visible de la main du croupier (excluant la carte cachée).
     */
    private int visibleHandValue = 0;
    
    /**
     * Construit un nouveau croupier avec un nom et un capital initial.
     * 
     * @param name le nom du croupier
     * @param pot le capital initial du croupier
     */
    public Dealer(String name, int pot){
        super(name, pot);
    }

    /**
     * Construit un nouveau croupier avec un nom et un capital par défaut de 100.
     * 
     * @param name le nom du croupier
     */
    public Dealer(String name){
        super(name);
        
    }

    /**
     * Retourne la main visible du croupier (toutes les cartes sauf la première).
     * Au blackjack, le croupier cache sa première carte distribuée.
     * 
     * @return une chaîne représentant les cartes visibles du croupier
     */
    public String getVisibleHand(){
        LinkedList<Card> cardInHand = new LinkedList<>();
        cardInHand.addAll(super.getHand().getDeck());
        cardInHand.pop();
        return cardInHand.toString();
    }

    /**
     * Retourne la valeur visible de la main du croupier (excluant la carte cachée).
     * 
     * @return la valeur des cartes visibles
     */
    public int getVisibleHandValue(){
        return this.visibleHandValue;
    }
    
    /**
     * Modifie la valeur visible de la main du croupier.
     * 
     * @param i la nouvelle valeur visible
     */
    public void setVisibleHandValue(int i){
        this.visibleHandValue = i;
    }

    /**
     * Modifie la valeur totale de la main du croupier.
     * Réinitialise également la valeur visible à 0.
     * 
     * @param x la nouvelle valeur de la main
     */
    @Override
    public void setHandValue(int x){
        this.visibleHandValue = 0;
        super.setHandValue(x);
    }

    /**
     * Détermine si le croupier doit continuer à tirer des cartes.
     * Règle du blackjack : le croupier tire jusqu'à atteindre au moins 17 points.
     * 
     * @return true si la valeur de la main est inférieure à 17, false sinon
     */
    @Override
    public boolean continueChoice(){
        if(super.getHandValue() < 17){
            return true;
        }
        return false;
    }  

    /**
     * Ajoute une nouvelle carte à la main du croupier.
     * Met à jour la valeur visible uniquement pour les cartes après la première
     * (la première carte reste cachée). Gère la valeur spéciale des As.
     * 
     * @param card la carte à ajouter
     */
    @Override
    public void addNewCard(Card card){
        super.addNewCard(card);
        if(super.getHand().getDeck().size() > 1){
            this.visibleHandValue += card.getValue();
            if(card.getName()=="As"){
            this.visibleHandValue += 10;
            }
        }

        
    }
}
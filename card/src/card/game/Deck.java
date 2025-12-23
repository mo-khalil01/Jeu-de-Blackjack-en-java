package card.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Représente un paquet de cartes avec des opérations de manipulation.
 * Cette classe permet de gérer un ensemble de cartes et d'effectuer
 * des opérations courantes comme mélanger, piocher ou couper le paquet.
 */
public class Deck{
   private LinkedList<Card> deck;

   /**
    * Construit un nouveau paquet avec la liste de cartes fournie.
    * 
    * @param deck la liste des cartes constituant le paquet
    */
   public Deck(LinkedList<Card> deck){
      this.deck = deck;
   }

   /**
    * Retourne la liste des cartes du paquet.
    * 
    * @return la LinkedList contenant toutes les cartes du paquet
    */
   public LinkedList<Card> getDeck(){
    return this.deck;
   }
   
   /**
    * Mélange aléatoirement les cartes du paquet.
    * Utilise Collections.shuffle() pour un mélange uniforme.
    */
   public void shuffle(){
      Collections.shuffle(this.deck);
   }

   /**
    * Retourne la carte située à l'index spécifié sans la retirer du paquet.
    * 
    * @param index la position de la carte dans le paquet (commence à 0)
    * @return la carte à la position demandée
    */
   public Card getCardToIndex(int index){
       return this.deck.get(index); 
   }

   /**
    * Pioche et retire la première carte du paquet.
    * 
    * @return la carte retirée du sommet du paquet
    */
   public Card draw(){
      Card c = this.deck.pop();
      return c;

   }

   /**
    * Retourne une représentation textuelle du paquet.
    * 
    * @return la liste des cartes sous forme de chaîne
    */
   @Override
   public String toString(){
    return this.deck.toString();
   }

   /**
    * Supprime la carte située à l'index spécifié du paquet.
    * 
    * @param i l'index de la carte à supprimer
    */
   public void deleteCard(int i){
       this.deck.remove(i);
   }

   /**
    * Ajoute une carte au sommet du paquet.
    * 
    * @param c la carte à ajouter
    */
   public void addCard(Card c){
      this.deck.add(c);
   }

   /**
    * Effectue une coupe aléatoire du paquet.
    * Divise le paquet en deux parties à un point aléatoire et inverse leur ordre.
    * La coupe est effectuée uniquement si le paquet contient au moins 4 cartes.
    * Le point de coupe est choisi entre la position 2 et (taille - 2).
    */
   public void cut(){
      Random rand = new Random();
      // seuil de coupe min 4 Cards
      if(this.deck.size() < 4){
        return ;
      }
      int cutIndex = rand.nextInt(this.deck.size()-4)+2;
      LinkedList<Card> part1 = new LinkedList<>(this.deck.subList(0, cutIndex));
      LinkedList<Card> part2 = new LinkedList<>(this.deck.subList(cutIndex,this.deck.size()));
      this.deck.clear();
      this.deck.addAll(part2);
      this.deck.addAll(part1);
      

   }

    
}
package card.util;

import java.util.LinkedList;
import java.util.List;

import card.game.*;

/**
 * Fabrique pour créer des paquets de cartes et des cartes individuelles.
 * Cette classe utilitaire fournit des méthodes statiques pour générer
 * des jeux de cartes standards (32 ou 52 cartes) avec les valeurs appropriées.
 */
public class FactoryCard {
    // les couleurs                                                         
    private static final List<String> COLORS = List.of("Pique", "Coeur", "Carreau", "Trefle");

    // Les noms des cartes selon le type de jeu
    private static final List<String> NAMES_52 = List.of("2", "3", "4", "5", "6", "7", "8", "9", "10", "Valet", "Reine", "Roi", "As");
    private static final List<String> NAMES_32 = List.of("7", "8", "9", "10", "Valet", "Reine", "Roi", "As");

    // Les figures (têtes)
    private static final List<String> HEADS = List.of("Valet", "Reine", "Roi");

    /**
     * Constructeur privé pour empêcher l'instanciation.
     * Cette classe ne contient que des méthodes statiques.
     */
    private FactoryCard() {}

    /**
     * Crée un paquet standard de 32 cartes (jeu français sans les 2 à 6).
     * Les cartes sont créées pour chaque combinaison de couleur et de nom.
     * 
     * @return un Deck contenant 32 cartes (4 couleurs × 8 valeurs)
     */
    public static Deck create32Deck() {
        LinkedList<Card> cards = new LinkedList<>();
        for (String name : NAMES_32) {
            for (String color : COLORS) {
                cards.add(createCard(color, name));
            }
        }
        return new Deck(cards);
    }

    /**
     * Crée un paquet standard de 52 cartes (jeu français complet).
     * Les cartes sont créées pour chaque combinaison de couleur et de nom.
     * 
     * @return un Deck contenant 52 cartes (4 couleurs × 13 valeurs)
     */
    public static Deck create52Deck() {
        LinkedList<Card> cards = new LinkedList<>();
        for (String name : NAMES_52) {
            for (String color : COLORS) {
                cards.add(createCard(color, name));
            }
        }
        return new Deck(cards);
    }

    /**
     * Crée un paquet vide sans aucune carte.
     * 
     * @return un Deck vide
     */
     public static Deck create0Deck() {
        LinkedList<Card> cards = new LinkedList<>();
        return new Deck(cards);
    }

    /**
     * Crée une carte individuelle avec la couleur et le nom spécifiés.
     * La valeur de la carte est automatiquement attribuée selon les règles suivantes :
     * - Les figures (Valet, Reine, Roi) valent 10
     * - L'As vaut 1
     * - Les autres cartes valent leur valeur numérique
     * 
     * @param color la couleur de la carte (Pique, Coeur, Carreau, Trefle)
     * @param name le nom de la carte (2-10, Valet, Reine, Roi, As)
     * @return une nouvelle carte avec les attributs spécifiés
     */
    public static Card createCard(String color, String name){
        if(HEADS.contains(name)){
            return new Card(color,name, 10);
        }
        else if(name.equals("As")){
            return new Card(color,name, 1);
        }
        else{
            return new Card(color,name, Integer.parseInt(name));
        }
    }
}
package card.game;

import card.util.FactoryCard;

public class DemoCard {

    public static void main(String[] args) {
        System.out.println("=== DEMO DES CARTES ===");

        // Création d'un jeu de 32 cartes
        Deck deck32 = FactoryCard.create32Deck();
        System.out.println("\nJeu de 32 cartes créé :");
        System.out.println(deck32);

        // Mélange du deck
        deck32.shuffle();
        System.out.println("\nAprès mélange :");
        System.out.println(deck32);

        // Pioche de la première carte
        Card drawn = deck32.draw();
        System.out.println("\nCarte piochée : " + drawn);
        System.out.println("Taille du deck après pioche : " + deck32.getDeck().size());

        // Ajout d’une carte au dessus du deck
        System.out.println("\nAjout d'une carte personnalisée en haut du deck...");
        Card customCard = FactoryCard.createCard("Trèfle", "As");
        deck32.addCard(customCard);
        System.out.println("Deck après ajout :");
        System.out.println(deck32);

        // Suppression d'une carte à un index donné
        System.out.println("\nSuppression de la carte à l'index 3...");
        deck32.deleteCard(3);
        System.out.println("Deck après suppression :");
        System.out.println(deck32);

        // Test de la coupe du deck
        System.out.println("\nCoupe du deck aléatoirement...");
        deck32.cut();
        System.out.println("Deck après coupe :");
        System.out.println(deck32);

        // Création d’un jeu vide
        Deck emptyDeck = FactoryCard.create0Deck();
        System.out.println("\nDeck vide créé : " + emptyDeck.getDeck().size() + " cartes");

        System.out.println("\n=== FIN DE LA DÉMONSTRATION ===");
    }
}

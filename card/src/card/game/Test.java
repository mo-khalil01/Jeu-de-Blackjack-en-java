package card.game;

import card.util.FactoryCard;

public class Test {

    public static void main(String[] args) {
        System.out.println("=== TEST AUTOMATISÉ DES CARTES ===");

        // Test création de deck 32 cartes
        Deck deck32 = FactoryCard.create32Deck();
        assert deck32.getDeck().size() == 32 : "Le deck de 32 cartes ne contient pas 32 cartes !";

        // Test mélange
        String avantMelange = deck32.toString();
        deck32.shuffle();
        String apresMelange = deck32.toString();
        assert !avantMelange.equals(apresMelange) : "Le deck n’a pas été mélangé correctement !";

        // Test pioche
        int tailleAvantPioche = deck32.getDeck().size();
        Card c = deck32.draw();
        assert deck32.getDeck().size() == tailleAvantPioche - 1 : "La taille du deck après pioche est incorrecte !";
        assert c != null : "Aucune carte n’a été piochée !";

        // Test ajout de carte
        Card ajout = FactoryCard.createCard("Trèfle", "As");
        deck32.addCard(ajout);
        assert deck32.getDeck().getFirst().equals(ajout) : "La carte ajoutée n’est pas en haut du deck !";

        //Test suppression
        int tailleAvantSuppression = deck32.getDeck().size();
        deck32.deleteCard(3);
        assert deck32.getDeck().size() == tailleAvantSuppression - 1 : "La suppression de la carte n’a pas réduit la taille du deck !";

        //Test coupe
        String avantCoupe = deck32.toString();
        deck32.cut();
        String apresCoupe = deck32.toString();
        assert !avantCoupe.equals(apresCoupe) : "La coupe du deck ne semble pas avoir été effectuée !";

        //  Test deck vide
        Deck empty = FactoryCard.create0Deck();
        assert empty.getDeck().isEmpty() : "Le deck vide n’est pas vide !";

        System.out.println(" Tous les tests sont passés avec succès !");
    }
}

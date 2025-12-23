package card.game;

/**
 * Représente une carte à jouer avec une couleur, un nom et une valeur.
 * Cette classe encapsule les propriétés fondamentales d'une carte de jeu.
 */
public class Card {
    private String color;
    private String name;
    private int value;

    /**
     * Construit une nouvelle carte avec les attributs spécifiés.
     * 
     * @param color la couleur de la carte (Pique, Coeur, Carreau, Trèfle)
     * @param name le nom de la carte (1, 2, ... , 10, Valet, Reine, Roi, As)
     * @param value la valeur numérique de la carte
     */
    public Card(String color, String name, int value ){
        this.color = color;
        this.name = name;
        this.value = value;
    }
    
    /**
     * Retourne la couleur de la carte.
     * 
     * @return la couleur de la carte
     */
    public String getColor(){
        return this.color;
    }

    /**
     * Retourne le nom de la carte.
     * 
     * @return le nom de la carte
     */
    public String getName(){
        return this.name;
    }

    /**
     * Retourne la valeur numérique de la carte.
     * 
     * @return la valeur de la carte
     */
    public int getValue(){
        return this.value;
    }

    /**
     * Modifie la valeur de la carte.
     * 
     * @param value la nouvelle valeur à attribuer à la carte
     */
    public void setValue(int value){
        this.value = value;
    }

    /**
     * Retourne une représentation textuelle de la carte.
     * 
     * @return une chaîne au format "nom couleur" (ex: "As Pique")
     */
    @Override
    public String toString(){
        return this.name +" "+this.color;
    }
    
}
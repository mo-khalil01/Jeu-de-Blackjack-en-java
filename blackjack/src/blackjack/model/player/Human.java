package blackjack.model.player;

import java.util.Scanner;

/**
 * Représente un joueur humain au blackjack.
 * Ce joueur interagit via la console pour prendre ses décisions
 * (continuer, doubler, montant de la mise).
 */
public class Human extends Player{
    
    /**
     * Construit un nouveau joueur humain avec un nom et un capital initial.
     * 
     * @param name le nom du joueur
     * @param pot le capital initial du joueur
     */
    public Human(String name, int pot){
        super(name, pot);
    }

    /**
     * Construit un nouveau joueur humain avec un nom et un capital par défaut de 100.
     * 
     * @param name le nom du joueur
     */
    public Human(String name){
        super(name);
        
    }

    /**
     * Demande à l'utilisateur une réponse oui/non via la console.
     * Répète la question jusqu'à obtenir une réponse valide ("oui" ou "non").
     * 
     * @param type le type de question à poser (ex: "Continue", "DoubleDump")
     * @return true si la réponse est "oui", false si c'est "non"
     */
    public boolean demandeScan(String type){
        String message = type + " ? oui/non : ";
        Scanner scan = new Scanner(System.in);
        String go = "";

        while(!go.equals("oui") && !go.equals("non")){
            System.out.println(message);
            go = scan.nextLine();
            if(!go.equals("oui") && !go.equals("non")){
                System.out.println("reponse invalide \n"); 
                continue;
            } 
        }

        if(go.equals("oui")){
            return true;
        }
        return false;
    }

    /**
     * Demande au joueur humain s'il souhaite continuer à tirer des cartes.
     * 
     * @return true si le joueur souhaite continuer, false sinon
     */
    @Override
    public boolean continueChoice(){
        return this.demandeScan("Continue");
    }

    /**
     * Demande au joueur humain s'il souhaite doubler sa mise.
     * 
     * @return true si le joueur souhaite doubler, false sinon
     */
    @Override
    public boolean doubleDump(){
        return this.demandeScan("DoubleDump");
    }

    /**
     * Demande au joueur humain de saisir le montant de sa mise.
     * Vérifie que la mise est un nombre valide compris entre betMin et betMax.
     * Répète la demande jusqu'à obtenir une valeur valide.
     * 
     * @param betMin la mise minimale autorisée
     * @param betMax la mise maximale autorisée
     * @return le montant de la mise saisie par le joueur
     */
    @Override
    public int placeBet(int betMin, int betMax) {
        Scanner scan = new Scanner(System.in);
        int valeur = -1;
        boolean valide = false;

        while (!valide) {
            System.out.println("Combien voulez-vous parier ? (entre " + betMin + " et " + betMax + "): ");
            String mise = scan.nextLine();

            try {
                valeur = Integer.parseInt(mise);
                if (valeur >= betMin && valeur <= betMax) {
                    valide = true;
                } else {
                    System.out.println("La mise doit être comprise entre " + betMin + " et " + betMax + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("La valeur entrée n'est pas valide, veuillez entrer un nombre.");
            }
        }

        return valeur;
    }

}
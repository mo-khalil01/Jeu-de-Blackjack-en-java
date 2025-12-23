package blackjack.util;

/**
 * Interface définissant le pattern Observer (Observable).
 * Les classes implémentant cette interface peuvent être observées par des objets Observateur
 * et les notifier lors de changements d'état. Ce pattern permet de découpler le modèle
 * (les données) de la vue (l'affichage).
 */
public interface Observable {
    
    /**
     * Ajoute un observateur à la liste des objets à notifier.
     * L'observateur sera informé de tous les changements d'état futurs.
     * 
     * @param o l'observateur à ajouter
     */
    public void ajouterObservateur(Observateur o);
    
    /**
     * Retire un observateur de la liste des objets à notifier.
     * L'observateur ne recevra plus de notifications après sa suppression.
     * 
     * @param o l'observateur à supprimer
     */
    public void supprimerObservateur(Observateur o);
    
    /**
     * Notifie tous les observateurs enregistrés d'un changement d'état.
     * Chaque observateur verra sa méthode mettreAJour() appelée.
     */
    public void notifierObservateurs();
}
package blackjack.util;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite implémentant le pattern Observer (côté Observable).
 * Cette classe fournit une implémentation de base pour gérer une liste d'observateurs
 * et les notifier des changements d'état. Les classes concrètes héritant de celle-ci
 * peuvent se concentrer sur leur logique métier et appeler notifierObservateurs()
 * quand leur état change.
 */
public abstract class ModeleObservable implements Observable {
    
    /**
     * Liste des observateurs enregistrés qui seront notifiés des changements.
     */
    private List<Observateur> observateurs = new ArrayList<>();

    /**
     * Ajoute un observateur à la liste des objets à notifier.
     * L'observateur recevra toutes les notifications futures de changement d'état.
     * 
     * @param observateur l'observateur à ajouter à la liste
     */
    @Override
    public void ajouterObservateur(Observateur observateur) {
        observateurs.add(observateur);
    }

    /**
     * Retire un observateur de la liste des objets à notifier.
     * L'observateur ne recevra plus aucune notification après sa suppression.
     * 
     * @param observateur l'observateur à retirer de la liste
     */
    @Override
    public void supprimerObservateur(Observateur observateur) {
        observateurs.remove(observateur);
    }

    /**
     * Notifie tous les observateurs enregistrés d'un changement d'état.
     * Appelle la méthode mettreAJour() de chaque observateur dans la liste.
     * Affiche également un message de debug indiquant le nombre d'observateurs notifiés.
     */
    @Override
    public void notifierObservateurs() {
        System.out.println(">>> Notifiant " + observateurs.size() + " observateur(s)...");
        for (Observateur observateur : observateurs) {
            observateur.mettreAJour();
        }
    }
}
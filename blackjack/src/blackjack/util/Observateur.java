package blackjack.util;

/**
 * Interface définissant un observateur dans le pattern Observer.
 * Les classes implémentant cette interface peuvent observer des objets Observable
 * et réagir automatiquement à leurs changements d'état. Ce pattern est couramment
 * utilisé pour synchroniser une vue avec son modèle.
 */
public interface Observateur {
    
    /**
     * Méthode appelée automatiquement lorsque l'objet observé notifie un changement.
     * L'implémentation de cette méthode doit définir comment l'observateur réagit
     * à la notification (par exemple, rafraîchir l'affichage, recalculer des valeurs, etc.).
     */
    public void mettreAJour();
}
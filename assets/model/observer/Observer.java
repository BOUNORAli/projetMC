package model.observer;

/**
 * Interface représentant un Observateur dans le patron Observateur.
 * Tout observateur doit implémenter la méthode update pour recevoir
 * les notifications d'un Observable.
 */
public interface Observer {

    /**
     * Méthode appelée lorsqu'un Observable appelle notifyXxx().
     *
     * @param observable L'objet émettant la notification (Texte, Collection, etc.)
     * @param message    Le message de notification (description du changement)
     */
    void update(Object observable, String message);
}

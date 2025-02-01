package model.observer;

/**
 * Interface représentant un sujet dans le patron Observateur.
 * <p>
 * Un sujet définit les méthodes permettant d'ajouter, de supprimer et de notifier ses observateurs.
 * </p>
 * 
 * @version 1.0
 */
public interface Sujet {

    /**
     * Ajoute un observateur au sujet.
     *
     * @param o l'observateur à ajouter
     */
    void ajouterObservateur(Observateur o);

    /**
     * Supprime un observateur du sujet.
     *
     * @param o l'observateur à supprimer
     */
    void supprimerObservateur(Observateur o);

    /**
     * Notifie tous les observateurs avec un message.
     *
     * @param message le message de notification
     */
    void notifierObservateurs(String message);
}

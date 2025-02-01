package model.observer;

/**
 * Interface représentant un observateur dans le patron Observateur.
 * <p>
 * Un observateur définit la méthode d'actualisation qui sera appelée lors
 * de la notification d'un sujet.
 * </p>
 * 
 * @version 1.0
 */
public interface Observateur {

    /**
     * Actualise l'observateur.
     *
     * @param sujet   le sujet qui notifie
     * @param message le message de notification
     */
    void actualiser(Object sujet, String message);
}

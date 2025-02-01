package model.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant un "sujet observable" dans le patron Observateur.
 * Elle gère deux listes distinctes d'observateurs :
 * <ul>
 *   <li>adminObservers (notifiés via notifyAdmin)</li>
 * </ul>
 */
public abstract class Observable {

    /** Liste des observateurs "admin" */
    private List<Observer> adminObservers = new ArrayList<>();

    /**
     * Ajoute un observateur dans la liste des "adminObservers".
     * @param obs Observateur à ajouter
     */
    public void addAdminObserver(Observer obs) {
        if (!adminObservers.contains(obs)) {
            adminObservers.add(obs);
        }
    }

    /**
     * Retire un observateur de la liste "adminObservers".
     * @param obs Observateur à retirer
     */
    public void removeAdminObserver(Observer obs) {
        adminObservers.remove(obs);
    }

    /**
     * Notifie TOUS les observateurs de la liste "adminObservers" avec le message indiqué.
     * @param message Message à transmettre
     */
    protected void notifyAdmin(String message) {
        for (Observer obs : adminObservers) {
            obs.update(this, message);
        }
    }
}

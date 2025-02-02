package controller;

import model.Modele;
import view.IVue;

/**
 * Interface du contrôleur (e‑contrôleur).
 * <p>
 * Cette interface expose la méthode permettant de configurer le contrôleur (en liant le modèle et la vue)
 * ainsi que la méthode de démarrage de l'application.
 * Dans le diagramme UML, c'est cette interface qui possède les associations vers les interfaces du modèle et de la vue.
 * </p>
 *
 * @version 1.0
 */
public interface IControleur {

    /**
     * Configure le contrôleur en lui fournissant le modèle et les vues.
     *
     * @param modele   instance du modèle (via l'interface {@link Modele})
     * @param vueAdmin instance de la vue pour l'administrateur (via l'interface {@link IVue})
     * @param vueAnnot instance de la vue pour l'annotateur (via l'interface {@link IVue})
     */
    void setModelAndView(Modele modele, IVue vueAdmin, IVue vueAnnot);

    /**
     * Démarre l'application.
     */
    default void startApplication() throws Exception{
        throw new Exception("Il ne faut pas utiliser cette fonction ici.");
    }
}

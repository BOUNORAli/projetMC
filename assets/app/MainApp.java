package app;

import controller.IControleur;
import controller.LoginController;
import controller.MainController;

/**
 * La classe principale contenant la méthode main().
 * <p>
 * Elle instancie le contrôleur via l'interface {@link IControleur} et démarre l'application.
 * </p>
 *
 * @version 1.0
 */
public class MainApp {

    /**
     * Point d'entrée de l'application.
     *
     * @param args les arguments de la ligne de commande (non utilisés)
     */

    // new login controller
    public static void main(String[] args) throws Exception {
        IControleur controller = new LoginController();
        controller.startApplication();
    }
}

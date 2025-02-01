package app;

import controller.MainController;

/**
 * Classe principale contenant la méthode main().
 * <p>
 * Lance le MainController et démarre l'application.
 */
public class MainApp {

    /**
     * Point d'entrée de l'application.
     * @param args arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        MainController controller = new MainController();
        controller.startApplication();
    }
}

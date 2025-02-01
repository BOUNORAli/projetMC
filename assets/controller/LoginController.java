package controller;

import java.io.IOException;
import java.util.Scanner;

import model.Administrateur;
import model.Annotateur;
import model.Modele;
import model.ModeleAnnotation;
import model.Texte;
import model.Utilisateur;
import view.IVue;
import view.VueLogin;

/**
 * Contrôleur dédié à la gestion de la connexion (Login).
 * <p>
 * Cette classe implémente l'interface {@code IControleur} et gère l'initialisation
 * des composants de l'application pour le login. Elle configure par défaut les instances
 * du modèle et des vues si aucune injection externe n'a été effectuée via
 * {@link #setModelAndView(Modele, IVue, IVue)}.
 * Après avoir chargé les données et configuré les abonnements, elle délègue la gestion
 * des menus aux méthodes de {@link MainController}.
 * </p>
 *
 * @version 1.0
 */
public class LoginController implements IControleur {

    /** Instance du modèle utilisée par le contrôleur. */
    private Modele modele;
    /** Vue destinée aux administrateurs. */
    private IVue vueAdmin;
    /** Vue destinée aux annotateurs. */
    private IVue vueAnnot;
    /** Scanner pour la saisie utilisateur. */
    private Scanner scanner = new Scanner(System.in);
    /**
     * Instance du MainController qui gère les menus principaux après le login.
     */
    private MainController main;

    /** Chemin vers le fichier CSV des utilisateurs. */
    private final String usersCsv = "resources/utilisateurs.csv";
    /** Chemin vers le fichier CSV des textes. */
    private final String textesCsv = "resources/textes.csv";
    /** Chemin vers le fichier CSV des annotations. */
    private final String annotationsCsv = "resources/annotations.csv";
    /** Chemin vers le fichier CSV des collections. */
    private final String collectionsCsv = "resources/collections.csv";

    /**
     * Configure le contrôleur en liant le modèle et les vues.
     *
     * @param modele   l'instance du modèle (via l'interface {@link Modele})
     * @param vueAdmin l'instance de la vue pour l'administrateur (via l'interface {@link IVue})
     * @param vueAnnot l'instance de la vue pour l'annotateur (via l'interface {@link IVue})
     */
    @Override
    public void setModelAndView(Modele modele, IVue vueAdmin, IVue vueAnnot) {
        this.modele = modele;
        this.vueAdmin = vueAdmin;
        this.vueAnnot = vueAnnot;
    }

    /**
     * Démarre l'application de login.
     * <p>
     * Si la configuration (modèle et vues) n'a pas été injectée, des instances par défaut
     * sont créées. Ensuite, les données sont chargées et une instance de MainController est
     * initialisée et configurée. Une boucle de connexion s'exécute pour authentifier l'utilisateur.
     * Après déconnexion, les données sont sauvegardées.
     * </p>
     */
    @Override
    public void startApplication() {
        // Configuration par défaut si setModelAndView n'a pas été appelé.
        if (modele == null || vueAdmin == null || vueAnnot == null) {
            this.modele = new ModeleAnnotation();
            this.vueAdmin = new view.VueAdministrateur();
            this.vueAnnot = new view.VueAnnotateur();
        }

        loadData();
        main = new MainController();
        main.setModelAndView(modele, vueAdmin, vueAnnot);

        // Boucle de connexion
        boolean run = true;
        while (run) {
            Utilisateur user = loginUser();
            if (user == null) {
                run = false;
            } else {
                if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    main.runAdminMenu((Administrateur) user);
                } else if ("ANNOTATEUR".equalsIgnoreCase(user.getRole())) {
                    main.runAnnotateurMenu((Annotateur) user);
                } else {
                    System.err.println("Rôle inconnu: " + user.getRole());
                }
            }
        }

        // Sauvegarde des données CSV
        try {
            modele.saveAll(usersCsv, textesCsv, annotationsCsv, collectionsCsv);
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde: " + e.getMessage());
        }

        System.out.println("Fermeture de l'application. Au revoir !");
    }

    /**
     * Gère la connexion de l'utilisateur.
     * <p>
     * Affiche un menu de connexion et demande les identifiants.
     * Si les informations sont correctes, retourne l'utilisateur correspondant ;
     * sinon, affiche un message d'erreur et redemande la saisie.
     * </p>
     *
     * @return l'utilisateur connecté ou {@code null} en cas de déconnexion
     */
    private Utilisateur loginUser() {
        while (true) {
            System.out.println("\n=== Écran de connexion ===");
            System.out.println("1) Se connecter");
            System.out.println("0) Quitter");
            System.out.print("Votre choix: ");
            String c = scanner.nextLine();
            switch (c) {
                case "0":
                    return null;
                case "1":
                    VueLogin.printText("Entrez votre ID: ");
                    String uid = VueLogin.getText();
                    VueLogin.printText("Entrez votre mot de passe: ");
                    String mdp = VueLogin.getText();
                    Utilisateur u = modele.getUtilisateursMap().get(uid);
                    if (u == null) {
                        VueLogin.printText("Utilisateur inconnu.");
                    } else {
                        if (u.getMotDePasse().equals(mdp)) {
                            VueLogin.printText("Connexion réussie : " + u.getNom());
                            return u;
                        } else {
                            VueLogin.printText("Mot de passe incorrect.");
                        }
                    }
                    break;
                default:
                    VueLogin.printText("Choix invalide.");
            }
        }
    }

    /**
     * Charge les données depuis les fichiers CSV et configure les abonnements aux notifications.
     * <p>
     * Charge les utilisateurs, textes, annotations et collections depuis les fichiers CSV.
     * Pour chaque texte chargé, la vue administrateur est abonnée pour recevoir des notifications.
     * </p>
     */
    private void loadData() {
        try {
            modele.loadAll(usersCsv, textesCsv, annotationsCsv, collectionsCsv);
        } catch (IOException e) {
            System.err.println("Erreur chargement CSV: " + e.getMessage());
        }

        // Abonnement de la vue administrateur aux notifications de chaque texte
        for (Texte t : modele.getTextesMap().values()) {
            t.ajouterObservateur((model.observer.Observateur) vueAdmin);
        }
    }
}

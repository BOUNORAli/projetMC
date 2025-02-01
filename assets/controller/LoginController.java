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


public class LoginController implements IControleur{

    // Références privées (injection via setModelAndView)
    private Modele modele;
    private IVue vueAdmin;
    private IVue vueAnnot;
    private Scanner scanner = new Scanner(System.in);
    private MainController main;

    // Chemins des fichiers CSV
    private final String usersCsv = "resources/utilisateurs.csv";
    private final String textesCsv = "resources/textes.csv";
    private final String annotationsCsv = "resources/annotations.csv";
    private final String collectionsCsv = "resources/collections.csv";


    /**
     * Configure le contrôleur en liant le modèle et les vues.
     *
     * @param modele   instance du modèle
     * @param vueAdmin instance de la vue pour l'administrateur
     * @param vueAnnot instance de la vue pour l'annotateur
     */
    @Override
    public void setModelAndView(Modele modele, IVue vueAdmin, IVue vueAnnot) {
        this.modele = modele;
        this.vueAdmin = vueAdmin;
        this.vueAnnot = vueAnnot;
    }

    /**
     * Démarre l'application.
     * <p>
     * Si la configuration n'a pas été injectée depuis l'extérieur, des instances par défaut sont créées.
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

        // 3) Boucle de connexion
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

        // 4) Sauvegarde des données CSV
        try {
            modele.saveAll(usersCsv, textesCsv, annotationsCsv, collectionsCsv);
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde: " + e.getMessage());
        }

        System.out.println("Fermeture de l'application. Au revoir !");
    }

    /**
     * Méthode privée pour gérer la connexion de l'utilisateur.
     *
     * @return l'utilisateur connecté ou null en cas de déconnexion
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

    private void loadData(){
        // 1) Chargement des données CSV
        try {
            modele.loadAll(usersCsv, textesCsv, annotationsCsv, collectionsCsv);
        } catch (IOException e) {
            System.err.println("Erreur chargement CSV: " + e.getMessage());
        }

        // 2) Abonnement de la vue administrateur aux notifications des textes
        for (Texte t : modele.getTextesMap().values()) {
            t.ajouterObservateur((model.observer.Observateur) vueAdmin);
        }
    }
}

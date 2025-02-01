package view;

import model.Annotation;
import model.Annotateur;
import model.Utilisateur;
import model.Texte;
import java.util.List;
import java.util.Scanner;

/**
 * Vue textuelle pour l'annotateur.
 * <p>
 * Implémente l'interface {@link IVue} et reçoit les notifications du modèle.
 * </p>
 * 
 * @version 1.0
 */
public class VueAnnotateur implements IVue {

    private Scanner scanner;

    /**
     * Constructeur.
     */
    public VueAnnotateur() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Méthode d'actualisation appelée lors de la notification du sujet.
     *
     * @param sujet   le sujet qui notifie
     * @param message le message de notification
     */
    @Override
    public void actualiser(Object sujet, String message) {
        // Affichage simple de la notification
        System.out.println("[Notification Annotateur] " + message);
    }

    @Override
    public int menuPrincipal(Utilisateur utilisateur) {
        // L'utilisateur est supposé être un annotateur.
        Annotateur annot = (Annotateur) utilisateur;
        System.out.println("\n===== Menu Annotateur (" + annot.getNom() + ") =====");
        System.out.println("1) Choisir une collection");
        System.out.println("2) Voir toutes mes annotations");
        System.out.println("3) Créer une nouvelle collection");
        System.out.println("0) Déconnexion");
        System.out.print("Votre choix: ");
        return lireEntier();
    }

    @Override
    public void afficherCollections(List<String> collections) {
        if (collections.isEmpty()) {
            System.out.println("Aucune collection disponible pour le moment.");
            return;
        }
        System.out.println("\n----- LISTE DES COLLECTIONS -----");
        int index = 1;
        for (String colName : collections) {
            System.out.println("[" + index + "] " + colName);
            index++;
        }
        System.out.println("----------------------------------");
    }

    @Override
    public String demanderNomCollection() {
        System.out.println("Entrez le nom de la collection (0 pour annuler) : ");
        return scanner.nextLine();
    }

    @Override
    public void afficherTextes(List<Texte> textes, String nomCollection) {
        if (textes.isEmpty()) {
            System.out.println("Aucun texte dans la collection « " + nomCollection + " ».");
            return;
        }
        System.out.println("\n----- TEXTES DANS LA COLLECTION « " + nomCollection + " » -----");
        int index = 1;
        for (Texte t : textes) {
            System.out.println("[" + index + "] ID=" + t.getId() +
                    " | Contenu : « " + t.getContenu() + " »" +
                    " | #" + t.getAnnotations().size() + " annotations");
            index++;
        }
        System.out.println("--------------------------------------------------------------");
    }

    @Override
    public int menuTextes() {
        System.out.println("\n[1] Annoter un texte");
        System.out.println("[2] Modifier une de MES annotations");
        System.out.println("[3] Ajouter un texte");
        System.out.println("[0] Retour");
        System.out.print("Votre choix: ");
        return lireEntier();
    }

    @Override
    public String demanderTexteId() {
        System.out.println("Entrez l'ID du texte (0 pour annuler): ");
        return scanner.nextLine();
    }

    /**
     * Méthode spécifique pour demander le contenu d'une annotation.
     *
     * @return le contenu saisi ou "0" pour annuler
     */
    public String demanderContenuAnnotation() {
        System.out.println("Entrez le contenu de l'annotation (0 pour annuler): ");
        return scanner.nextLine();
    }

    @Override
    public void afficherAnnotations(List<Annotation> annotations) {
        System.out.println("----- Vos Annotations -----");
        if (annotations.isEmpty()) {
            System.out.println("(Aucune annotation pour l'instant.)");
            return;
        }
        int index = 1;
        for (Annotation ann : annotations) {
            System.out.println("Annotation #" + index + ":");
            System.out.println("  - ID de l'annotation: " + ann.getAnnotationId());
            System.out.println("  - ID du texte       : " + ann.getTexteId());
            System.out.println("  - Auteur           : " + ann.getAuteurId());
            System.out.println("  - Contenu          : " + ann.getContenu());
            System.out.println("  - Valide?          : " + (ann.isValide() ? "Oui" : "Non"));
            System.out.println();
            index++;
        }
    }

    @Override
    public String demanderAnnotationId() {
        System.out.println("Entrez l'ID de l'annotation (0 pour annuler): ");
        return scanner.nextLine();
    }

    @Override
    public String demanderNouveauContenu() {
        System.out.println("Entrez le nouveau contenu (0 pour annuler): ");
        return scanner.nextLine();
    }

    @Override
    public String demanderNouveauTexte() {
        System.out.println("Entrez le contenu du nouveau texte (0 pour annuler): ");
        return scanner.nextLine();
    }

    /**
     * Lit un entier saisi par l'utilisateur.
     *
     * @return l'entier saisi ou -1 en cas d'erreur
     */
    private int lireEntier() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

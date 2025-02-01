package view;

import model.observer.Observer;
import model.Annotation;
import model.Administrateur;
import model.Texte;

import java.util.List;
import java.util.Scanner;

public class VueAdministrateur implements Observer {
    private Scanner scanner;

    public VueAdministrateur() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void update(Object observable, String message) {
    }

    public int menuPrincipal(Administrateur admin) {
        System.out.println("\n===== Menu Administrateur (" + admin.getNom() + ") =====");
        System.out.println("1) Choisir une collection");
        System.out.println("2) Créer une nouvelle collection");
        System.out.println("0) Déconnexion");
        System.out.print("Votre choix: ");
        return lireEntier();
    }

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
    

    public String demanderNomCollection() {
        System.out.println("Entrez le nom de la collection (0 pour annuler) : ");
        return scanner.nextLine();
    }

    public void afficherTextes(List<Texte> textes, String nomCollection) {
        if (textes.isEmpty()) {
            System.out.println("Aucun texte dans la collection « " + nomCollection + " ».");
            return;
        }
    
        System.out.println("\n----- TEXTES DANS LA COLLECTION « " + nomCollection + " » -----");
        int index = 1;
        for (Texte t : textes) {
            System.out.println(
                "[" + index + "] ID=" + t.getId() +
                " | Contenu : « " + t.getContenu() + " »" +
                " | #" + t.getAnnotations().size() + " annotations"
            );
            index++;
        }
        System.out.println("--------------------------------------------------------------");
    }
    

    public int menuTextesAdmin() {
        System.out.println("\n[1] Valider une annotation");
        System.out.println("[2] Corriger une annotation");
        System.out.println("[3] Ajouter un texte");
        System.out.println("[0] Retour");
        System.out.print("Votre choix: ");
        return lireEntier();
    }

    public String demanderTexteId() {
        System.out.println("Entrez l'ID du texte (0 pour annuler): ");
        return scanner.nextLine();
    }

    public void afficherAnnotations(List<Annotation> annList) {
        if (annList.isEmpty()) {
            System.out.println("Aucune annotation.");
            return;
        }
    
        System.out.println("\n----- LISTE DES ANNOTATIONS -----");
        int index = 1;
        for (Annotation ann : annList) {
            System.out.println("Annotation #" + index + " :");
            System.out.println("  - ID           : " + ann.getAnnotationId());
            System.out.println("  - Texte lié    : " + ann.getTexteId());
            System.out.println("  - Auteur       : " + ann.getAuteurId());
            System.out.println("  - Contenu      : " + ann.getContenu());
            System.out.println("  - Valide       : " + (ann.isValide() ? "Oui" : "Non"));
            System.out.println(); // ligne vide
            index++;
        }
    }
    

    public String demanderAnnotationId() {
        System.out.println("Entrez l'ID de l'annotation (0 pour annuler): ");
        return scanner.nextLine();
    }

    public String demanderNouveauContenu() {
        System.out.println("Entrez le nouveau contenu (0 pour annuler): ");
        return scanner.nextLine();
    }

    public String demanderNouveauTexte() {
        System.out.println("Entrez le contenu du nouveau texte (0 pour annuler): ");
        return scanner.nextLine();
    }

    private int lireEntier() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

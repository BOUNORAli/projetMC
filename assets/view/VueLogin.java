package view;

import java.util.Scanner;

/**
 * Classe utilitaire pour la gestion de l'affichage et de la saisie lors de la connexion.
 * <p>
 * Cette classe fournit des méthodes statiques pour afficher du texte et obtenir la saisie de l'utilisateur.
 * </p>
 *
 * @version 1.0
 */
public class VueLogin {

    /**
     * Affiche le texte spécifié sans retour à la ligne.
     *
     * @param text le texte à afficher
     */
    public static void printText(String text) {
        System.out.print(text);
    }

    /**
     * Lit et retourne une ligne de texte saisie par l'utilisateur.
     * <p>
     * Utilise un nouveau Scanner à chaque appel (attention à la gestion des ressources).
     * </p>
     *
     * @return la ligne saisie par l'utilisateur
     */
    public static String getText() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        return input;
    }
}

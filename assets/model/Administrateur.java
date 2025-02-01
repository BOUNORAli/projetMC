package model;

/**
 * Classe représentant un administrateur (héritant de Utilisateur).
 * Possède des méthodes pour valider et corriger des annotations.
 */
public class Administrateur extends Utilisateur {

    /**
     * Constructeur
     * @param id         l'ID (ex: "admin1")
     * @param nom        le nom (ex: "Bob")
     * @param email      l'email
     * @param motDePasse mot de passe
     */
    public Administrateur(String id, String nom, String email, String motDePasse) {
        super(id, nom, email, "ADMIN", motDePasse);
    }

    /**
     * Valide l'annotation en l'appelant sur le texte (valide=true),
     * et notifie l'annotateur.
     * @param texte Texte concerné
     * @param ann   Annotation à valider
     */
    public void validerAnnotation(Texte texte, Annotation ann) {
        texte.validerAnnotation(ann);
    }

    /**
     * Corrige l'annotation en changeant son contenu, la met valide=true,
     * et notifie l'annotateur.
     * @param texte      Texte concerné
     * @param ann        Annotation à corriger
     * @param newContent Nouveau contenu
     */
    public void corrigerAnnotation(Texte texte, Annotation ann, String newContent) {
        texte.corrigerAnnotation(ann, newContent);
    }
}

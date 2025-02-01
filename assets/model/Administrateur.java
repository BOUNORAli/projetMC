package model;

/**
 * Classe représentant un administrateur.
 * <p>
 * Hérite de {@link Utilisateur} et fournit des méthodes spécifiques pour valider et corriger des annotations.
 * </p>
 * 
 * @version 1.0
 */
public class Administrateur extends Utilisateur {

    /**
     * Constructeur.
     *
     * @param id         identifiant (ex: "admin1")
     * @param nom        nom (ex: "Bob")
     * @param email      email
     * @param motDePasse mot de passe
     */
    public Administrateur(String id, String nom, String email, String motDePasse) {
        super(id, nom, email, "ADMIN", motDePasse);
    }

    /**
     * Valide une annotation en appelant la méthode correspondante sur le texte.
     *
     * @param texte le texte concerné
     * @param ann   l'annotation à valider
     */
    public void validerAnnotation(Texte texte, Annotation ann) {
        texte.validerAnnotation(ann);
    }

    /**
     * Corrige une annotation en modifiant son contenu et en la validant.
     *
     * @param texte      le texte concerné
     * @param ann        l'annotation à corriger
     * @param newContent nouveau contenu
     */
    public void corrigerAnnotation(Texte texte, Annotation ann, String newContent) {
        texte.corrigerAnnotation(ann, newContent);
    }
}

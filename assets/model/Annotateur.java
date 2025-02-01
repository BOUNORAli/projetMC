package model;

/**
 * Classe représentant un annotateur.
 * <p>
 * Hérite de {@link Utilisateur} et fournit des méthodes pour créer et modifier des annotations.
 * </p>
 * 
 * @version 1.0
 */
public class Annotateur extends Utilisateur {

    /**
     * Constructeur.
     *
     * @param id         identifiant (ex: "user1")
     * @param nom        nom (ex: "Alice")
     * @param email      email
     * @param motDePasse mot de passe
     */
    public Annotateur(String id, String nom, String email, String motDePasse) {
        super(id, nom, email, "ANNOTATEUR", motDePasse);
    }

    /**
     * Crée une annotation sur un texte.
     *
     * @param texte        le texte à annoter
     * @param annotationId identifiant unique (ex: "A1")
     * @param contenu      contenu de l'annotation
     * @return l'annotation créée
     */
    public Annotation annoterTexte(Texte texte, String annotationId, String contenu) {
        Annotation ann = new Annotation(annotationId, texte.getId(), this.id, contenu);
        texte.ajouterAnnotation(ann); // Notifie les observateurs
        return ann;
    }

    /**
     * Modifie le contenu d'une annotation et la marque comme non valide.
     *
     * @param ann            l'annotation à modifier
     * @param nouveauContenu le nouveau contenu
     */
    public void modifierAnnotation(Annotation ann, String nouveauContenu) {
        if (ann.getAuteurId().equals(this.id)) {
            ann.setContenu(nouveauContenu);
            ann.setValide(false);
        }
    }
}

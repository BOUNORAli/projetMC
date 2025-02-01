package model;

/**
 * Classe représentant un annotateur (héritant de Utilisateur).
 * Possède des méthodes pour créer et modifier des annotations.
 */
public class Annotateur extends Utilisateur {

    /**
     * Constructeur
     * @param id        l'ID (ex: "user1")
     * @param nom       le nom (ex: "Alice")
     * @param email     l'email
     * @param motDePasse mot de passe
     */
    public Annotateur(String id, String nom, String email, String motDePasse) {
        super(id, nom, email, "ANNOTATEUR", motDePasse);
    }

    /**
     * Crée une annotation sur un texte et appelle texte.ajouterAnnotation(...)
     * @param texte         Le texte à annoter
     * @param annotationId  ID unique (ex: "A3")
     * @param contenu       Contenu de l'annotation
     * @return L'annotation créée
     */
    public Annotation annoterTexte(Texte texte, String annotationId, String contenu) {
        Annotation ann = new Annotation(annotationId, texte.getId(), this.id, contenu);
        texte.ajouterAnnotation(ann); // notifie l'admin
        return ann;
    }

    /**
     * Modifie le contenu d'une annotation si l'annotateur en est l'auteur,
     * et la rend non valide (valide=false).
     * @param ann            L'annotation à modifier
     * @param nouveauContenu Le nouveau contenu
     */
    public void modifierAnnotation(Annotation ann, String nouveauContenu) {
        if (ann.getAuteurId().equals(this.id)) {
            ann.setContenu(nouveauContenu);
            ann.setValide(false);
        }
    }
}

package model;

/**
 * Classe représentant une annotation sur un texte.
 * <p>
 * Une annotation possède un identifiant, l'identifiant du texte associé, l'identifiant de l'auteur,
 * le contenu et un indicateur de validité.
 * </p>
 * 
 * @version 1.0
 */
public class Annotation {
    private String annotationId;
    private String texteId;
    private String auteurId;
    private String contenu;
    private boolean valide;

    /**
     * Constructeur.
     *
     * @param annotationId identifiant unique (ex: "A1")
     * @param texteId      identifiant du texte (ex: "T1")
     * @param auteurId     identifiant de l'auteur
     * @param contenu      contenu de l'annotation
     */
    public Annotation(String annotationId, String texteId, String auteurId, String contenu) {
        this.annotationId = annotationId;
        this.texteId = texteId;
        this.auteurId = auteurId;
        this.contenu = contenu;
        this.valide = false;
    }

    /**
     * Retourne l'identifiant de l'annotation.
     *
     * @return l'identifiant
     */
    public String getAnnotationId() {
        return annotationId;
    }

    /**
     * Retourne l'identifiant du texte associé.
     *
     * @return l'identifiant du texte
     */
    public String getTexteId() {
        return texteId;
    }

    /**
     * Retourne l'identifiant de l'auteur.
     *
     * @return l'identifiant de l'auteur
     */
    public String getAuteurId() {
        return auteurId;
    }

    /**
     * Retourne le contenu de l'annotation.
     *
     * @return le contenu
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * Indique si l'annotation est valide.
     *
     * @return true si valide, false sinon
     */
    public boolean isValide() {
        return valide;
    }

    /**
     * Met à jour le contenu de l'annotation.
     *
     * @param c nouveau contenu
     */
    public void setContenu(String c) {
        this.contenu = c;
    }

    /**
     * Définit la validité de l'annotation.
     *
     * @param v true si valide, false sinon
     */
    public void setValide(boolean v) {
        this.valide = v;
    }

    @Override
    public String toString() {
        return "Annotation{" +
                "annotationId='" + annotationId + '\'' +
                ", texteId='" + texteId + '\'' +
                ", auteurId='" + auteurId + '\'' +
                ", contenu='" + contenu + '\'' +
                ", valide=" + valide +
                '}';
    }
}

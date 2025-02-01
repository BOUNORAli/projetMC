package model;

/**
 * Classe représentant une annotation sur un texte.
 * <ul>
 *   <li>annotationId (ex: A1)</li>
 *   <li>texteId (ex: T2)</li>
 *   <li>auteurId (ex: user1)</li>
 *   <li>contenu (String)</li>
 *   <li>valide (boolean)</li>
 * </ul>
 */
public class Annotation {
    private String annotationId;
    private String texteId;
    private String auteurId;
    private String contenu;
    private boolean valide;

    /**
     * Constructeur
     * @param annotationId ID unique (ex: "A1")
     * @param texteId      ID du texte (ex: "T2")
     * @param auteurId     ID de l'auteur annotateur
     * @param contenu      Contenu de l'annotation
     */
    public Annotation(String annotationId, String texteId, String auteurId, String contenu) {
        this.annotationId = annotationId;
        this.texteId = texteId;
        this.auteurId = auteurId;
        this.contenu = contenu;
        this.valide = false;
    }

    public String getAnnotationId() { return annotationId; }
    public String getTexteId()      { return texteId; }
    public String getAuteurId()     { return auteurId; }
    public String getContenu()      { return contenu; }
    public boolean isValide()       { return valide; }

    public void setContenu(String c) { this.contenu = c; }
    public void setValide(boolean v) { this.valide = v; }

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

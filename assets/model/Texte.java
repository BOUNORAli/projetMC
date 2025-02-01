package model;

import model.observer.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un Texte.
 * <p>
 * Hérite de Observable afin de notifier les Observers :
 * <ul>
 *   <li>notifyAdmin(...) lorsque l'annotateur ajoute une annotation</li>
 * </ul>
 */
public class Texte extends Observable {

    private String id;
    private String contenu;
    private List<Annotation> annotations;

    /**
     * Constructeur
     * @param id      ex: "T1"
     * @param contenu contenu du texte
     */
    public Texte(String id, String contenu) {
        this.id = id;
        this.contenu = contenu;
        this.annotations = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getContenu() {
        return contenu;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    /**
     * Ajoute une annotation au texte et notifie l'admin (notifyAdmin).
     * @param ann L'annotation à ajouter
     */
    public void ajouterAnnotation(Annotation ann) {
        annotations.add(ann);
        System.out.println("Dans ajouterAnnotation: je notifie l'admin (debug)");
        notifyAdmin("Texte [" + id + "] => nouvelle annotation [" 
                    + ann.getAnnotationId() + "] par " + ann.getAuteurId());
    }

    /**
     * Valide une annotation (valide=true)
     * @param ann L'annotation à valider
     */
    public void validerAnnotation(Annotation ann) {
        ann.setValide(true);
    }

    /**
     * Corrige le contenu d'une annotation, la passe en valide=true
     * @param ann        L'annotation à corriger
     * @param newContent Le nouveau contenu
     */
    public void corrigerAnnotation(Annotation ann, String newContent) {
        ann.setContenu(newContent);
        ann.setValide(true);
    }

    @Override
    public String toString() {
        return "Texte{" +
               "id='" + id + '\'' +
               ", contenu='" + contenu + '\'' +
               ", nbAnnotations=" + annotations.size() +
               '}';
    }
}

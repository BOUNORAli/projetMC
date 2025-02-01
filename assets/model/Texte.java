package model;

import model.observer.Observateur;
import model.observer.Sujet;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un texte.
 * <p>
 * Un texte possède un identifiant, un contenu et une liste d'annotations.
 * Il implémente l'interface {@link Sujet} pour permettre la notification
 * de ses observateurs lors d'événements (par exemple, l'ajout d'une annotation).
 * </p>
 * 
 * @version 1.0
 */
public class Texte implements Sujet {

    private String id;
    private String contenu;
    private List<Annotation> annotations;

    /** Liste des observateurs enregistrés */
    private List<Observateur> observateurs = new ArrayList<>();

    /**
     * Constructeur.
     *
     * @param id      identifiant du texte (ex: "T1")
     * @param contenu contenu du texte
     */
    public Texte(String id, String contenu) {
        this.id = id;
        this.contenu = contenu;
        this.annotations = new ArrayList<>();
    }

    /**
     * Retourne l'identifiant du texte.
     *
     * @return l'identifiant
     */
    public String getId() {
        return id;
    }

    /**
     * Retourne le contenu du texte.
     *
     * @return le contenu
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * Retourne la liste des annotations associées.
     *
     * @return la liste des annotations
     */
    public List<Annotation> getAnnotations() {
        return annotations;
    }

    /**
     * Ajoute une annotation au texte et notifie les observateurs.
     *
     * @param ann l'annotation à ajouter
     */
    public void ajouterAnnotation(Annotation ann) {
        annotations.add(ann);
        System.out.println("Dans ajouterAnnotation: je notifie l'administrateur (debug)");
        notifierObservateurs("Texte [" + id + "] => nouvelle annotation [" + ann.getAnnotationId() + "] par " + ann.getAuteurId());
    }

    /**
     * Valide une annotation.
     *
     * @param ann l'annotation à valider
     */
    public void validerAnnotation(Annotation ann) {
        ann.setValide(true);
    }

    /**
     * Corrige une annotation et la valide.
     *
     * @param ann        l'annotation à corriger
     * @param newContent nouveau contenu
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

    // ----------------- Implémentation de Sujet -----------------

    /**
     * Ajoute un observateur.
     *
     * @param o l'observateur à ajouter
     */
    @Override
    public void ajouterObservateur(Observateur o) {
        if (!observateurs.contains(o)) {
            observateurs.add(o);
        }
    }

    /**
     * Supprime un observateur.
     *
     * @param o l'observateur à supprimer
     */
    @Override
    public void supprimerObservateur(Observateur o) {
        observateurs.remove(o);
    }

    /**
     * Notifie tous les observateurs avec le message fourni.
     *
     * @param message le message de notification
     */
    @Override
    public void notifierObservateurs(String message) {
        for (Observateur o : observateurs) {
            o.actualiser(this, message);
        }
    }
}

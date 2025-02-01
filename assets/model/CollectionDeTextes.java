package model;

import model.observer.Observateur;
import model.observer.Sujet;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une collection de textes.
 * <p>
 * Une collection est identifiée par son nom et contient une liste de textes.
 * Elle implémente l'interface {@link Sujet} si une notification doit être envoyée
 * lors de l'ajout d'un texte.
 * </p>
 * 
 * @version 1.0
 */
public class CollectionDeTextes implements Sujet {

    private String nom;
    private List<Texte> textes;

    /** Liste des observateurs enregistrés */
    private List<Observateur> observateurs = new ArrayList<>();

    /**
     * Constructeur.
     *
     * @param nom nom de la collection (ex: "MaCollection")
     */
    public CollectionDeTextes(String nom) {
        this.nom = nom;
        this.textes = new ArrayList<>();
    }

    /**
     * Retourne le nom de la collection.
     *
     * @return le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne la liste des textes de la collection.
     *
     * @return la liste des textes
     */
    public List<Texte> getTextes() {
        return textes;
    }

    /**
     * Ajoute un texte à la collection.
     *
     * @param t le texte à ajouter
     */
    public void ajouterTexte(Texte t) {
        textes.add(t);
        // Vous pouvez notifier les observateurs ici si besoin
        notifierObservateurs("Nouvel ajout dans la collection [" + nom + "]: Texte [" + t.getId() + "]");
    }

    @Override
    public String toString() {
        return "CollectionDeTextes{" +
                "nom='" + nom + '\'' +
                ", nbTextes=" + textes.size() +
                '}';
    }

    // ----------------- Implémentation de Sujet -----------------

    @Override
    public void ajouterObservateur(Observateur o) {
        if (!observateurs.contains(o)) {
            observateurs.add(o);
        }
    }

    @Override
    public void supprimerObservateur(Observateur o) {
        observateurs.remove(o);
    }

    @Override
    public void notifierObservateurs(String message) {
        for (Observateur o : observateurs) {
            o.actualiser(this, message);
        }
    }
}

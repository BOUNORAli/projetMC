package model;

import model.observer.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une Collection de Textes (ou "CollectionDeTextes").
 * <p>
 * Hérite de Observable si on souhaite notifier lors de l'ajout d'un texte,
 * mais ce n'est pas forcément obligatoire.
 */
public class CollectionDeTextes extends Observable {

    private String nom;
    private List<Texte> textes;

    /**
     * Constructeur
     * @param nom Le nom de la collection (ex: "MaCollection")
     */
    public CollectionDeTextes(String nom) {
        this.nom = nom;
        this.textes = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public List<Texte> getTextes() {
        return textes;
    }

    /**
     * Ajoute un texte à la collection.
     * @param t Le texte à ajouter
     */
    public void ajouterTexte(Texte t) {
        textes.add(t);
        // Optionnel : notifyAdmin("Nouveau texte dans la collection " + nom);
    }

    @Override
    public String toString() {
        return "CollectionDeTextes{" +
               "nom='" + nom + '\'' +
               ", nbTextes=" + textes.size() +
               '}';
    }
}

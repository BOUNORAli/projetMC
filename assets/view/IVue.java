package view;

import model.Annotation;
import model.Texte;
import model.Utilisateur;
import java.util.List;
import model.observer.Observateur;

/**
 * Interface de la vue.
 * <p>
 * Définit l'ensemble des méthodes d'affichage et de saisie utilisées par le contrôleur.
 * Elle étend {@link Observateur} pour recevoir les notifications du modèle.
 * </p>
 * 
 * @version 1.0
 */
public interface IVue extends Observateur {

    /**
     * Affiche le menu principal en fonction de l'utilisateur.
     *
     * @param utilisateur l'utilisateur courant
     * @return le choix effectué
     */
    int menuPrincipal(Utilisateur utilisateur);

    /**
     * Affiche la liste des collections.
     *
     * @param collections la liste des noms de collections
     */
    void afficherCollections(List<String> collections);

    /**
     * Demande à l'utilisateur le nom d'une collection.
     *
     * @return le nom saisi ou "0" pour annuler
     */
    String demanderNomCollection();

    /**
     * Affiche la liste des textes d'une collection.
     *
     * @param textes        la liste des textes
     * @param nomCollection le nom de la collection
     */
    void afficherTextes(List<Texte> textes, String nomCollection);

    /**
     * Affiche le menu des actions sur les textes.
     *
     * @return le choix effectué
     */
    int menuTextes();

    /**
     * Demande l'ID d'un texte.
     *
     * @return l'ID saisi ou "0" pour annuler
     */
    String demanderTexteId();

    /**
     * Affiche la liste des annotations.
     *
     * @param annotations la liste des annotations à afficher
     */
    void afficherAnnotations(List<Annotation> annotations);

    /**
     * Demande l'ID d'une annotation.
     *
     * @return l'ID saisi ou "0" pour annuler
     */
    String demanderAnnotationId();

    /**
     * Demande le nouveau contenu (pour modification).
     *
     * @return le contenu saisi ou "0" pour annuler
     */
    String demanderNouveauContenu();

    /**
     * Demande le contenu d'un nouveau texte.
     *
     * @return le contenu saisi ou "0" pour annuler
     */
    String demanderNouveauTexte();
}

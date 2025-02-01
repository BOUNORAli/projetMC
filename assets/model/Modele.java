package model;

import java.io.IOException;
import java.util.Map;

/**
 * Interface définissant les opérations du modèle.
 * <p>
 * Permet de charger/sauvegarder les entités, d'accéder aux données en mémoire
 * et de générer des identifiants.
 * </p>
 * 
 * @author 
 * @version 1.0
 */
public interface Modele {

    /**
     * Charge toutes les entités depuis les fichiers CSV.
     *
     * @param usersCsv        fichier CSV des utilisateurs
     * @param textesCsv       fichier CSV des textes
     * @param annotationsCsv  fichier CSV des annotations
     * @param collectionsCsv  fichier CSV des collections
     * @throws IOException en cas d'erreur de lecture
     */
    void loadAll(String usersCsv, String textesCsv, String annotationsCsv, String collectionsCsv) throws IOException;

    /**
     * Sauvegarde toutes les entités dans les fichiers CSV.
     *
     * @param usersCsv        fichier CSV des utilisateurs
     * @param textesCsv       fichier CSV des textes
     * @param annotationsCsv  fichier CSV des annotations
     * @param collectionsCsv  fichier CSV des collections
     * @throws IOException en cas d'erreur d'écriture
     */
    void saveAll(String usersCsv, String textesCsv, String annotationsCsv, String collectionsCsv) throws IOException;

    /**
     * Retourne la map de tous les utilisateurs.
     *
     * @return la map des utilisateurs (clé = ID)
     */
    Map<String, Utilisateur> getUtilisateursMap();

    /**
     * Retourne la map de tous les textes.
     *
     * @return la map des textes (clé = ID)
     */
    Map<String, Texte> getTextesMap();

    /**
     * Retourne la map de toutes les annotations.
     *
     * @return la map des annotations (clé = ID)
     */
    Map<String, Annotation> getAnnotationsMap();

    /**
     * Retourne la map de toutes les collections de textes.
     *
     * @return la map des collections (clé = nom)
     */
    Map<String, CollectionDeTextes> getCollectionsMap();

    /**
     * Ajoute une collection en mémoire.
     *
     * @param c la collection à ajouter
     */
    void addCollection(CollectionDeTextes c);

    /**
     * Ajoute un texte en mémoire.
     *
     * @param t le texte à ajouter
     */
    void addTexte(Texte t);

    /**
     * Ajoute une annotation en mémoire.
     *
     * @param a l'annotation à ajouter
     */
    void addAnnotation(Annotation a);

    /**
     * Génère un nouvel identifiant de texte.
     *
     * @return l'ID généré (ex: "T1", "T2", ...)
     */
    String generateTexteId();

    /**
     * Génère un nouvel identifiant d'annotation.
     *
     * @return l'ID généré (ex: "A1", "A2", ...)
     */
    String generateAnnotationId();
}

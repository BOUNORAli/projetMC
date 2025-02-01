package model;

import java.io.IOException;
import java.util.Map;

/**
 * Interface définissant les opérations du modèle :
 * <ul>
 *   <li>Chargement/sauvegarde des entités (utilisateurs, textes, annotations, collections)</li>
 *   <li>Accès aux maps des entités</li>
 *   <li>Ajout d'entités</li>
 *   <li>Génération d'IDs auto-incrémentés</li>
 * </ul>
 */
public interface Modele {

    /**
     * Charge toutes les entités depuis les CSV indiqués.
     * @param usersCsv        Fichier CSV des utilisateurs
     * @param textesCsv       Fichier CSV des textes
     * @param annotationsCsv  Fichier CSV des annotations
     * @param collectionsCsv  Fichier CSV des collections
     * @throws IOException En cas de problème de lecture
     */
    void loadAll(String usersCsv, String textesCsv, String annotationsCsv, String collectionsCsv) throws IOException;

    /**
     * Sauvegarde toutes les entités dans les CSV indiqués.
     * @param usersCsv        Fichier CSV des utilisateurs
     * @param textesCsv       Fichier CSV des textes
     * @param annotationsCsv  Fichier CSV des annotations
     * @param collectionsCsv  Fichier CSV des collections
     * @throws IOException En cas de problème d'écriture
     */
    void saveAll(String usersCsv, String textesCsv, String annotationsCsv, String collectionsCsv) throws IOException;

    /**
     * Retourne la map de tous les Utilisateurs.
     * @return La map (clé=ID, valeur=Utilisateur)
     */
    Map<String, Utilisateur> getUtilisateursMap();

    /**
     * Retourne la map de tous les Textes.
     * @return La map (clé=ID, valeur=Texte)
     */
    Map<String, Texte> getTextesMap();

    /**
     * Retourne la map de toutes les Annotations.
     * @return La map (clé=ID, valeur=Annotation)
     */
    Map<String, Annotation> getAnnotationsMap();

    /**
     * Retourne la map de toutes les CollectionsDeTextes.
     * @return La map (clé=nom, valeur=CollectionDeTextes)
     */
    Map<String, CollectionDeTextes> getCollectionsMap();

    /**
     * Ajoute une nouvelle collection en mémoire.
     * @param c La CollectionDeTextes à ajouter
     */
    void addCollection(CollectionDeTextes c);

    /**
     * Ajoute un nouveau texte en mémoire.
     * @param t Le texte à ajouter
     */
    void addTexte(Texte t);

    /**
     * Ajoute une nouvelle annotation en mémoire.
     * @param a L'annotation à ajouter
     */
    void addAnnotation(Annotation a);

    /**
     * Génère un nouvel ID de texte (ex : T1, T2, ...).
     * @return L'ID généré
     */
    String generateTexteId();

    /**
     * Génère un nouvel ID d'annotation (ex : A1, A2, ...).
     * @return L'ID généré
     */
    String generateAnnotationId();
}

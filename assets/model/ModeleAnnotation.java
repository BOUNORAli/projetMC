package model;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Implémentation concrète de l'interface Modele.
 * <p>
 * Elle gère la lecture et l'écriture en CSV de :
 * <ul>
 *   <li>utilisateurs.csv</li>
 *   <li>textes.csv</li>
 *   <li>annotations.csv</li>
 *   <li>collections.csv</li>
 * </ul>
 * et stocke les entités en mémoire (Maps). Elle génère des IDs auto-incrémentés pour les textes et annotations.
 */
public class ModeleAnnotation implements Modele {

    private static final String SEP = ";";

    // Maps en mémoire
    private Map<String, Utilisateur> utilisateursMap;
    private Map<String, Texte> textesMap;
    private Map<String, Annotation> annotationsMap;
    private Map<String, CollectionDeTextes> collectionsMap;

    private int nextTexteNumber;
    private int nextAnnotationNumber;

    /**
     * Constructeur : initialise les maps et les compteurs d'ID.
     */
    public ModeleAnnotation() {
        this.utilisateursMap = new HashMap<>();
        this.textesMap = new HashMap<>();
        this.annotationsMap = new HashMap<>();
        this.collectionsMap = new HashMap<>();
        this.nextTexteNumber = 1;
        this.nextAnnotationNumber = 1;
    }

    @Override
    public void loadAll(
        String usersCsv,
        String textesCsv,
        String annotationsCsv,
        String collectionsCsv
    ) throws IOException {
        lireUtilisateurs(usersCsv);
        lireTextes(textesCsv);
        lireAnnotations(annotationsCsv);
        lireCollections(collectionsCsv);
    }

    @Override
    public void saveAll(
        String usersCsv,
        String textesCsv,
        String annotationsCsv,
        String collectionsCsv
    ) throws IOException {
        ecrireUtilisateurs(usersCsv);
        ecrireTextes(textesCsv);
        ecrireAnnotations(annotationsCsv);
        ecrireCollections(collectionsCsv);
    }

    @Override
    public Map<String, Utilisateur> getUtilisateursMap() {
        return utilisateursMap;
    }

    @Override
    public Map<String, Texte> getTextesMap() {
        return textesMap;
    }

    @Override
    public Map<String, Annotation> getAnnotationsMap() {
        return annotationsMap;
    }

    @Override
    public Map<String, CollectionDeTextes> getCollectionsMap() {
        return collectionsMap;
    }

    @Override
    public void addCollection(CollectionDeTextes c) {
        collectionsMap.put(c.getNom(), c);
    }

    @Override
    public void addTexte(Texte t) {
        textesMap.put(t.getId(), t);
    }

    @Override
    public void addAnnotation(Annotation a) {
        annotationsMap.put(a.getAnnotationId(), a);
    }

    @Override
    public String generateTexteId() {
        String tid = "T" + nextTexteNumber;
        nextTexteNumber++;
        return tid;
    }

    @Override
    public String generateAnnotationId() {
        String aid = "A" + nextAnnotationNumber;
        nextAnnotationNumber++;
        return aid;
    }

    /* ==================== Lecture CSV ==================== */

    private void lireUtilisateurs(String filePath) throws IOException {
        File f = new File(filePath);
        if (!f.exists()) {
            System.err.println("Fichier utilisateurs introuvable: " + filePath);
            return;
        }
        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null) {
                if(line.isBlank()) continue;
                // user1;Alice;alice@example.com;ANNOTATEUR;1234
                String[] parts = line.split(SEP);
                if(parts.length < 5) {
                    System.err.println("Ligne user invalide: " + line);
                    continue;
                }
                String uid   = parts[0];
                String nom   = parts[1];
                String email = parts[2];
                String role  = parts[3];
                String mdp   = parts[4];

                if("ADMIN".equalsIgnoreCase(role)) {
                    Administrateur admin = new Administrateur(uid, nom, email, mdp);
                    utilisateursMap.put(uid, admin);
                }
                else if("ANNOTATEUR".equalsIgnoreCase(role)) {
                    Annotateur an = new Annotateur(uid, nom, email, mdp);
                    utilisateursMap.put(uid, an);
                }
                else {
                    System.err.println("Rôle inconnu: " + role);
                }
            }
        }
    }

    private void lireTextes(String filePath) throws IOException {
        File f = new File(filePath);
        if(!f.exists()) {
            System.err.println("Fichier textes introuvable: " + filePath);
            return;
        }
        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null) {
                if(line.isBlank()) continue;
                // T1;Contenu du texte
                String[] parts = line.split(SEP, 2);
                if(parts.length < 2) {
                    System.err.println("Ligne texte invalide: " + line);
                    continue;
                }
                String tid = parts[0];
                String cont= parts[1];

                Texte t = new Texte(tid, cont);
                textesMap.put(tid, t);

                int num = extractNumber(tid);
                if(num >= nextTexteNumber) {
                    nextTexteNumber = num + 1;
                }
            }
        }
    }

    private void lireAnnotations(String filePath) throws IOException {
        File f = new File(filePath);
        if(!f.exists()) {
            System.err.println("Fichier annotations introuvable: " + filePath);
            return;
        }
        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null) {
                if(line.isBlank()) continue;
                // A1;T1;user1;bla bla;false
                String[] parts = line.split(SEP);
                if(parts.length < 5) {
                    System.err.println("Ligne annotation invalide: " + line);
                    continue;
                }
                String annId  = parts[0];
                String tId    = parts[1];
                String autId  = parts[2];
                String cont   = parts[3];
                boolean val   = Boolean.parseBoolean(parts[4]);

                Texte t = textesMap.get(tId);
                if(t == null) {
                    System.err.println("Texte introuvable: " + tId);
                    continue;
                }
                Annotation ann = new Annotation(annId, tId, autId, cont);
                ann.setValide(val);
                t.getAnnotations().add(ann);
                annotationsMap.put(annId, ann);

                int num = extractNumber(annId);
                if(num >= nextAnnotationNumber) {
                    nextAnnotationNumber = num + 1;
                }
            }
        }
    }

    private void lireCollections(String filePath) throws IOException {
        File f = new File(filePath);
        if(!f.exists()) {
            System.err.println("Fichier collections introuvable: " + filePath);
            return;
        }
        try(BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while((line = br.readLine()) != null) {
                if(line.isBlank()) continue;
                // MaCollection;T1
                String[] parts = line.split(SEP);
                if(parts.length < 2) {
                    System.err.println("Ligne collection invalide: " + line);
                    continue;
                }
                String colName = parts[0];
                String tId     = parts[1];

                CollectionDeTextes c = collectionsMap.get(colName);
                if(c == null) {
                    c = new CollectionDeTextes(colName);
                    collectionsMap.put(colName, c);
                }
                Texte t = textesMap.get(tId);
                if(t != null) {
                    c.ajouterTexte(t);
                } else {
                    System.err.println("Texte introuvable: " + tId);
                }
            }
        }
    }

    /* ==================== Ecriture CSV ==================== */

    private void ecrireUtilisateurs(String filePath) throws IOException {
        try(PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for(Utilisateur u : utilisateursMap.values()) {
                pw.print(u.getId());    pw.print(SEP);
                pw.print(u.getNom());   pw.print(SEP);
                pw.print(u.getEmail()); pw.print(SEP);
                pw.print(u.getRole());  pw.print(SEP);
                pw.println(u.getMotDePasse());
            }
        }
    }

    private void ecrireTextes(String filePath) throws IOException {
        try(PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for(Texte t : textesMap.values()) {
                pw.print(t.getId()); pw.print(SEP);
                pw.println(t.getContenu());
            }
        }
    }

    private void ecrireAnnotations(String filePath) throws IOException {
        try(PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for(Annotation ann : annotationsMap.values()) {
                pw.print(ann.getAnnotationId()); pw.print(SEP);
                pw.print(ann.getTexteId());      pw.print(SEP);
                pw.print(ann.getAuteurId());     pw.print(SEP);
                pw.print(ann.getContenu());      pw.print(SEP);
                pw.println(ann.isValide());
            }
        }
    }

    private void ecrireCollections(String filePath) throws IOException {
        try(PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for(CollectionDeTextes c : collectionsMap.values()) {
                for(Texte t : c.getTextes()) {
                    pw.print(c.getNom()); pw.print(SEP);
                    pw.println(t.getId());
                }
            }
        }
    }

    /* ==================== Utilitaires ==================== */

    private int extractNumber(String s) {
        // ex: T12 => 12, A3 => 3
        if(s.length() < 2) return 0;
        try {
            return Integer.parseInt(s.substring(1));
        } catch(NumberFormatException e) {
            return 0;
        }
    }
}

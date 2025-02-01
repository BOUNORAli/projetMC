package controller;

import model.*;
import view.VueAnnotateur;
import view.VueAdministrateur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainController {

    private Modele modele; // L'interface Modele
    private VueAnnotateur vueAnnotateur;
    private VueAdministrateur vueAdministrateur;

    // CSV filenames
    private String usersCsv       = "resources/utilisateurs.csv";
    private String textesCsv      = "resources/textes.csv";
    private String annotationsCsv = "resources/annotations.csv";
    private String collectionsCsv = "resources/collections.csv";

    private Scanner scanner = new Scanner(System.in);

    public MainController() {
        // On instancie l'implémentation
        this.modele = new ModeleAnnotation();
        this.vueAnnotateur = new VueAnnotateur();
        this.vueAdministrateur = new VueAdministrateur();
    }

    public void startApplication() {
        // 1) Charger
        try {
            modele.loadAll(usersCsv, textesCsv, annotationsCsv, collectionsCsv);
        } catch (IOException e) {
            System.err.println("Erreur chargement CSV: " + e.getMessage());
        }

        // 2) Abonner la vue admin et la vue annotateur à chaque texte
        for (Texte t : modele.getTextesMap().values()) {
            t.addAdminObserver(vueAdministrateur);
        }

        // 3) Boucle de connexion
        boolean run = true;
        while (run) {
            Utilisateur user = loginUser();
            if (user == null) {
                run = false;
            } else {
                if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    runAdminMenu((Administrateur) user);
                } else if ("ANNOTATEUR".equalsIgnoreCase(user.getRole())) {
                    runAnnotateurMenu((Annotateur) user);
                } else {
                    System.err.println("Rôle inconnu: " + user.getRole());
                }
            }
        }

        // 4) Sauvegarde finale
        try {
            modele.saveAll(usersCsv, textesCsv, annotationsCsv, collectionsCsv);
        } catch (IOException e) {
            System.err.println("Erreur sauvegarde: " + e.getMessage());
        }

        System.out.println("Fermeture de l'application. Au revoir !");
    }

    private Utilisateur loginUser() {
        while(true) {
            System.out.println("\n=== Écran de connexion ===");
            System.out.println("1) Se connecter");
            System.out.println("0) Quitter");
            System.out.print("Votre choix: ");
            String c = scanner.nextLine();
            switch(c) {
                case "0":
                    return null;
                case "1":
                    System.out.print("Entrez votre ID: ");
                    String uid = scanner.nextLine();
                    System.out.print("Entrez votre mot de passe: ");
                    String mdp = scanner.nextLine();
                    Utilisateur u = modele.getUtilisateursMap().get(uid);
                    if(u == null) {
                        System.err.println("Utilisateur inconnu.");
                    } else {
                        if(u.getMotDePasse().equals(mdp)) {
                            System.out.println("Connexion réussie : " + u.getNom());
                            return u;
                        } else {
                            System.err.println("Mot de passe incorrect.");
                        }
                    }
                    break;
                default:
                    System.err.println("Choix invalide.");
            }
        }
    }

    // -------------------------------------------------
    // MENU ADMIN
    // -------------------------------------------------
    private void runAdminMenu(Administrateur admin) {
        boolean back = false;
        while (!back) {
            int choice = vueAdministrateur.menuPrincipal(admin);
            switch(choice) {
                case 0:
                    back = true;
                    break;
                case 1:
                    choisirCollectionAdmin(admin);
                    break;
                case 2:
                    creerNouvelleCollection();
                    break;
                default:
                    System.err.println("Choix invalide.");
            }
        }
    }

    private void choisirCollectionAdmin(Administrateur admin) {
        Map<String, CollectionDeTextes> colMap = modele.getCollectionsMap();
        List<String> noms = new ArrayList<>(colMap.keySet());
        vueAdministrateur.afficherCollections(noms);

        String colName = vueAdministrateur.demanderNomCollection();
        if("0".equals(colName)) return;
        CollectionDeTextes c = colMap.get(colName);
        if(c == null) {
            System.err.println("Collection introuvable.");
            return;
        }

        boolean loop = true;
        while(loop) {
            vueAdministrateur.afficherTextes(c.getTextes(),c.getNom());
            int sub = vueAdministrateur.menuTextesAdmin();
            switch(sub) {
                case 0:
                    loop = false;
                    break;
                case 1:
                    validerAnnotation(admin, c);
                    break;
                case 2:
                    corrigerAnnotation(admin, c);
                    break;
                case 3:
                    ajouterTexteAdmin(c);
                    break;
                default:
                    System.err.println("Choix invalide.");
            }
        }
    }

    private void validerAnnotation(Administrateur admin, CollectionDeTextes c) {
        String tid = vueAdministrateur.demanderTexteId();
        if("0".equals(tid)) return;
        Texte t = modele.getTextesMap().get(tid);
        if(t == null || !c.getTextes().contains(t)) {
            System.err.println("Texte introuvable dans cette collection.");
            return;
        }
        List<Annotation> annList = t.getAnnotations();
        vueAdministrateur.afficherAnnotations(annList);
        String annId = vueAdministrateur.demanderAnnotationId();
        if("0".equals(annId)) return;
        Annotation ann = modele.getAnnotationsMap().get(annId);
        if(ann == null || !ann.getTexteId().equals(tid)) {
            System.err.println("Annotation introuvable pour ce texte.");
            return;
        }
        admin.validerAnnotation(t, ann);
        System.out.println("Annotation validée.");
    }

    private void corrigerAnnotation(Administrateur admin, CollectionDeTextes c) {
        String tid = vueAdministrateur.demanderTexteId();
        if("0".equals(tid)) return;
        Texte t = modele.getTextesMap().get(tid);
        if(t == null || !c.getTextes().contains(t)) {
            System.err.println("Texte introuvable dans la collection.");
            return;
        }
        List<Annotation> annList = t.getAnnotations();
        vueAdministrateur.afficherAnnotations(annList);
        String annId = vueAdministrateur.demanderAnnotationId();
        if("0".equals(annId)) return;
        Annotation ann = modele.getAnnotationsMap().get(annId);
        if(ann == null || !ann.getTexteId().equals(tid)) {
            System.err.println("Annotation introuvable pour ce texte.");
            return;
        }
        String newCont = vueAdministrateur.demanderNouveauContenu();
        if("0".equals(newCont)) return;
        admin.corrigerAnnotation(t, ann, newCont);
        System.out.println("Annotation corrigée et validée.");
    }

    private void ajouterTexteAdmin(CollectionDeTextes c) {
        String cont = vueAdministrateur.demanderNouveauTexte();
        if("0".equals(cont)) return;
        String tId = modele.generateTexteId();
        Texte t = new Texte(tId, cont);
        // On abonne pour voir (admin, annot)
        t.addAdminObserver(vueAdministrateur);

        c.ajouterTexte(t);
        modele.addTexte(t);
        System.out.println("Texte " + tId + " ajouté à la collection " + c.getNom());
    }

    private void creerNouvelleCollection() {
        String colName = vueAdministrateur.demanderNomCollection();
        if("0".equals(colName)) return;
        if(modele.getCollectionsMap().containsKey(colName)) {
            System.err.println("Cette collection existe déjà.");
            return;
        }
        CollectionDeTextes c = new CollectionDeTextes(colName);
        modele.addCollection(c);
        System.out.println("Collection créée: " + colName);
    }

    // -------------------------------------------------
    //   MENU ANNOTATEUR
    // -------------------------------------------------
    private void runAnnotateurMenu(Annotateur annot) {
        boolean back = false;
        while(!back) {
            int c = vueAnnotateur.menuPrincipal(annot);
            switch(c) {
                case 0:
                    back = true;
                    break;
                case 1:
                    choisirCollectionAnnot(annot);
                    break;
                case 2:
                    voirMesAnnotations(annot);
                    break;
                case 3:
                    creerNouvelleCollectionAnnot();
                    break;
                default:
                    System.err.println("Choix invalide.");
            }
        }
    }

    private void choisirCollectionAnnot(Annotateur annot) {
        Map<String, CollectionDeTextes> colMap = modele.getCollectionsMap();
        List<String> noms = new ArrayList<>(colMap.keySet());
        vueAnnotateur.afficherCollections(noms);

        String colName = vueAnnotateur.demanderNomCollection();
        if("0".equals(colName)) return;
        CollectionDeTextes c = colMap.get(colName);
        if(c == null) {
            System.err.println("Collection introuvable.");
            return;
        }

        boolean loop = true;
        while(loop) {
            vueAnnotateur.afficherTextes(c.getTextes(),c.getNom());
            int sub = vueAnnotateur.menuTextesAnnot();
            switch(sub) {
                case 0:
                    loop = false;
                    break;
                case 1:
                    annoterTexte(annot, c);
                    break;
                case 2:
                    modifierMonAnnotation(annot, c);
                    break;
                case 3:
                    ajouterTexteAnnot(annot, c);
                    break;
                default:
                    System.err.println("Choix invalide.");
            }
        }
    }

    private void annoterTexte(Annotateur annot, CollectionDeTextes c) {
        String tid = vueAnnotateur.demanderTexteId();
        if("0".equals(tid)) return;
        Texte t = modele.getTextesMap().get(tid);
        if(t == null || !c.getTextes().contains(t)) {
            System.err.println("Texte introuvable dans cette collection.");
            return;
        }
        String contenu = vueAnnotateur.demanderContenuAnnotation();
        if("0".equals(contenu)) return;
        String annId = modele.generateAnnotationId();
        Annotation ann = annot.annoterTexte(t, annId, contenu);
        modele.addAnnotation(ann);
        System.out.println("Annotation créée: " + annId);
    }

    private void modifierMonAnnotation(Annotateur annot, CollectionDeTextes c) {
        String tid = vueAnnotateur.demanderTexteId();
        if("0".equals(tid)) return;
        Texte t = modele.getTextesMap().get(tid);
        if(t == null || !c.getTextes().contains(t)) {
            System.err.println("Texte introuvable dans la collection.");
            return;
        }
        List<Annotation> annList = t.getAnnotations();
        if(annList.isEmpty()) {
            System.err.println("Aucune annotation sur ce texte.");
            return;
        }
        for (Annotation a : annList) {
            System.out.println(a);
        }
        String annId = vueAnnotateur.demanderAnnotationId();
        if("0".equals(annId)) return;
        Annotation a = modele.getAnnotationsMap().get(annId);
        if(a == null) {
            System.err.println("Annotation introuvable.");
            return;
        }
        if(!a.getAuteurId().equals(annot.getId())) {
            System.err.println("Cette annotation n'est pas la vôtre !");
            return;
        }
        String newCont = vueAnnotateur.demanderNouveauContenu();
        if("0".equals(newCont)) return;
        annot.modifierAnnotation(a, newCont);
        System.out.println("Annotation modifiée (non valide).");
    }

    private void ajouterTexteAnnot(Annotateur annot, CollectionDeTextes c) {
        String contenu = vueAnnotateur.demanderNouveauTexte();
        if("0".equals(contenu)) return;
        String tId = modele.generateTexteId();
        Texte t = new Texte(tId, contenu);

        // Abonnements
        t.addAdminObserver(vueAdministrateur);

        c.ajouterTexte(t);
        modele.addTexte(t);
        System.out.println("Texte " + tId + " ajouté dans la collection " + c.getNom());
    }

    private void voirMesAnnotations(Annotateur annot) {
        List<Annotation> list = new ArrayList<>();
        for (Annotation a : modele.getAnnotationsMap().values()) {
            if (a.getAuteurId().equals(annot.getId())) {
                list.add(a);
            }
        }
        vueAnnotateur.afficherMesAnnotations(list);
    }

    private void creerNouvelleCollectionAnnot() {
        String colName = vueAnnotateur.demanderNomCollection();
        if("0".equals(colName)) return;
        if(modele.getCollectionsMap().containsKey(colName)) {
            System.err.println("Cette collection existe déjà.");
            return;
        }
        CollectionDeTextes c = new CollectionDeTextes(colName);
        modele.addCollection(c);
        System.out.println("Nouvelle collection créée: " + colName);
    }
}

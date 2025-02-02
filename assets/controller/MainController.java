package controller;

import model.*;
import view.IVue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Implémentation de l'interface {@link IControleur}.
 * <p>
 * Ce contrôleur ne communique avec le modèle et la vue que via les interfaces (Modele et IVue).
 * La relation avec le modèle et la vue est définie dans l'interface IControleur via la méthode setModelAndView,
 * ce qui permet de ne pas montrer de relation directe entre MainController et ces interfaces dans le diagramme UML.
 * </p>
 *
 * @version 1.0
 */
public class MainController implements IControleur {

    // Références privées (injection via setModelAndView)
    private Modele modele;
    private IVue vueAdmin;
    private IVue vueAnnot;

    /**
     * Constructeur par défaut.
     * <p>
     * La configuration des dépendances se fera via {@link #setModelAndView(Modele, IVue, IVue)}.
     * </p>
     */
    public MainController() {
        // Aucun instanciation ici : on attend l'injection par setModelAndView.
    }

    /**
     * Configure le contrôleur en liant le modèle et les vues.
     *
     * @param modele   instance du modèle
     * @param vueAdmin instance de la vue pour l'administrateur
     * @param vueAnnot instance de la vue pour l'annotateur
     */
    @Override
    public void setModelAndView(Modele modele, IVue vueAdmin, IVue vueAnnot) {
        this.modele = modele;
        this.vueAdmin = vueAdmin;
        this.vueAnnot = vueAnnot;
    }

    
    

    // ------------- Menu Administrateur -------------

    public void runAdminMenu(Administrateur admin) {
        boolean back = false;
        while (!back) {
            int choice = vueAdmin.menuPrincipal(admin);
            switch (choice) {
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
        vueAdmin.afficherCollections(noms);

        String colName = vueAdmin.demanderNomCollection();
        if ("0".equals(colName))
            return;
        CollectionDeTextes c = colMap.get(colName);
        if (c == null) {
            System.err.println("Collection introuvable.");
            return;
        }

        boolean loop = true;
        while (loop) {
            vueAdmin.afficherTextes(c.getTextes(), c.getNom());
            int sub = vueAdmin.menuTextes();
            switch (sub) {
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
        String tid = vueAdmin.demanderTexteId();
        if ("0".equals(tid))
            return;
        Texte t = modele.getTextesMap().get(tid);
        if (t == null || !c.getTextes().contains(t)) {
            System.err.println("Texte introuvable dans cette collection.");
            return;
        }
        List<Annotation> annList = t.getAnnotations();
        vueAdmin.afficherAnnotations(annList);
        String annId = vueAdmin.demanderAnnotationId();
        if ("0".equals(annId))
            return;
        Annotation ann = modele.getAnnotationsMap().get(annId);
        if (ann == null || !ann.getTexteId().equals(tid)) {
            System.err.println("Annotation introuvable pour ce texte.");
            return;
        }
        admin.validerAnnotation(t, ann);
        System.out.println("Annotation validée.");
    }

    private void corrigerAnnotation(Administrateur admin, CollectionDeTextes c) {
        String tid = vueAdmin.demanderTexteId();
        if ("0".equals(tid))
            return;
        Texte t = modele.getTextesMap().get(tid);
        if (t == null || !c.getTextes().contains(t)) {
            System.err.println("Texte introuvable dans la collection.");
            return;
        }
        List<Annotation> annList = t.getAnnotations();
        vueAdmin.afficherAnnotations(annList);
        String annId = vueAdmin.demanderAnnotationId();
        if ("0".equals(annId))
            return;
        Annotation ann = modele.getAnnotationsMap().get(annId);
        if (ann == null || !ann.getTexteId().equals(tid)) {
            System.err.println("Annotation introuvable pour ce texte.");
            return;
        }
        String newCont = vueAdmin.demanderNouveauContenu();
        if ("0".equals(newCont))
            return;
        admin.corrigerAnnotation(t, ann, newCont);
        System.out.println("Annotation corrigée et validée.");
    }

    private void ajouterTexteAdmin(CollectionDeTextes c) {
        String cont = vueAdmin.demanderNouveauTexte();
        if ("0".equals(cont))
            return;
        String tId = modele.generateTexteId();
        Texte t = new Texte(tId, cont);
        t.ajouterObservateur((model.observer.Observateur) vueAdmin);
        c.ajouterTexte(t);
        modele.addTexte(t);
        System.out.println("Texte " + tId + " ajouté à la collection " + c.getNom());
    }

    private void creerNouvelleCollection() {
        String colName = vueAdmin.demanderNomCollection();
        if ("0".equals(colName))
            return;
        if (modele.getCollectionsMap().containsKey(colName)) {
            System.err.println("Cette collection existe déjà.");
            return;
        }
        CollectionDeTextes c = new CollectionDeTextes(colName);
        modele.addCollection(c);
        System.out.println("Collection créée: " + colName);
    }

    // ------------- Menu Annotateur -------------

    public void runAnnotateurMenu(Annotateur annot) {
        boolean back = false;
        while (!back) {
            int c = vueAnnot.menuPrincipal(annot);
            switch (c) {
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
        vueAnnot.afficherCollections(noms);

        String colName = vueAnnot.demanderNomCollection();
        if ("0".equals(colName))
            return;
        CollectionDeTextes c = colMap.get(colName);
        if (c == null) {
            System.err.println("Collection introuvable.");
            return;
        }

        boolean loop = true;
        while (loop) {
            vueAnnot.afficherTextes(c.getTextes(), c.getNom());
            int sub = vueAnnot.menuTextes();
            switch (sub) {
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
        String tid = vueAnnot.demanderTexteId();
        if ("0".equals(tid))
            return;
        Texte t = modele.getTextesMap().get(tid);
        if (t == null || !c.getTextes().contains(t)) {
            System.err.println("Texte introuvable dans cette collection.");
            return;
        }
        String contenu = "";
        if (vueAnnot instanceof view.VueAnnotateur) {
            contenu = ((view.VueAnnotateur) vueAnnot).demanderContenuAnnotation();
        } else {
            contenu = vueAnnot.demanderNouveauTexte();
        }
        if ("0".equals(contenu))
            return;
        String annId = modele.generateAnnotationId();
        Annotation ann = annot.annoterTexte(t, annId, contenu);
        modele.addAnnotation(ann);
        System.out.println("Annotation créée: " + annId);
    }

    private void modifierMonAnnotation(Annotateur annot, CollectionDeTextes c) {
        String tid = vueAnnot.demanderTexteId();
        if ("0".equals(tid))
            return;
        Texte t = modele.getTextesMap().get(tid);
        if (t == null || !c.getTextes().contains(t)) {
            System.err.println("Texte introuvable dans la collection.");
            return;
        }
        List<Annotation> annList = t.getAnnotations();
        if (annList.isEmpty()) {
            System.err.println("Aucune annotation sur ce texte.");
            return;
        }
        for (Annotation a : annList) {
            System.out.println(a);
        }
        String annId = vueAnnot.demanderAnnotationId();
        if ("0".equals(annId))
            return;
        Annotation a = modele.getAnnotationsMap().get(annId);
        if (a == null) {
            System.err.println("Annotation introuvable.");
            return;
        }
        if (!a.getAuteurId().equals(annot.getId())) {
            System.err.println("Cette annotation n'est pas la vôtre !");
            return;
        }
        String newCont = vueAnnot.demanderNouveauContenu();
        if ("0".equals(newCont))
            return;
        annot.modifierAnnotation(a, newCont);
        System.out.println("Annotation modifiée (non valide).");
    }

    private void ajouterTexteAnnot(Annotateur annot, CollectionDeTextes c) {
        String contenu = vueAnnot.demanderNouveauTexte();
        if ("0".equals(contenu))
            return;
        String tId = modele.generateTexteId();
        Texte t = new Texte(tId, contenu);
        t.ajouterObservateur((model.observer.Observateur) vueAdmin);
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
        vueAnnot.afficherAnnotations(list);
    }

    private void creerNouvelleCollectionAnnot() {
        String colName = vueAnnot.demanderNomCollection();
        if ("0".equals(colName))
            return;
        if (modele.getCollectionsMap().containsKey(colName)) {
            System.err.println("Cette collection existe déjà.");
            return;
        }
        CollectionDeTextes c = new CollectionDeTextes(colName);
        modele.addCollection(c);
        System.out.println("Nouvelle collection créée: " + colName);
    }
}

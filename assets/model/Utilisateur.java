package model;

/**
 * Classe abstraite représentant un utilisateur.
 * <p>
 * Un utilisateur possède un identifiant, un nom, un email, un rôle et un mot de passe.
 * </p>
 * 
 * @author 
 * @version 1.0
 */
public abstract class Utilisateur {
    protected String id;
    protected String nom;
    protected String email;
    protected String role;
    protected String motDePasse;

    /**
     * Constructeur.
     *
     * @param id         identifiant de l'utilisateur
     * @param nom        nom de l'utilisateur
     * @param email      email de l'utilisateur
     * @param role       rôle ("ADMIN" ou "ANNOTATEUR")
     * @param motDePasse mot de passe de l'utilisateur
     */
    public Utilisateur(String id, String nom, String email, String role, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.role = role;
        this.motDePasse = motDePasse;
    }

    /**
     * Retourne l'identifiant.
     *
     * @return l'identifiant
     */
    public String getId() {
        return id;
    }

    /**
     * Retourne le nom.
     *
     * @return le nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne l'email.
     *
     * @return l'email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retourne le rôle.
     *
     * @return le rôle
     */
    public String getRole() {
        return role;
    }

    /**
     * Retourne le mot de passe.
     *
     * @return le mot de passe
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    @Override
    public String toString() {
        return role + "{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

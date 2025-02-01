package model;

/**
 * Classe abstraite représentant un Utilisateur du système.
 * Un utilisateur possède :
 * <ul>
 *   <li>un identifiant (String)</li>
 *   <li>un nom</li>
 *   <li>un email</li>
 *   <li>un rôle : "ADMIN" ou "ANNOTATEUR"</li>
 *   <li>un mot de passe</li>
 * </ul>
 */
public abstract class Utilisateur {
    protected String id;
    protected String nom;
    protected String email;
    protected String role;       // "ADMIN" ou "ANNOTATEUR"
    protected String motDePasse; // stocké en clair

    /**
     * Constructeur de l'utilisateur
     * @param id        identifiant (ex: "user1")
     * @param nom       nom (ex: "Alice")
     * @param email     email
     * @param role      "ADMIN" ou "ANNOTATEUR"
     * @param motDePasse mot de passe
     */
    public Utilisateur(String id, String nom, String email, String role, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.role = role;
        this.motDePasse = motDePasse;
    }

    public String getId()         { return id; }
    public String getNom()        { return nom; }
    public String getEmail()      { return email; }
    public String getRole()       { return role; }
    public String getMotDePasse() { return motDePasse; }

    @Override
    public String toString() {
        return role + "{" +
               "id='" + id + '\'' +
               ", nom='" + nom + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}

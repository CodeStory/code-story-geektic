package geeks;

public class Geek {
  public final String nom;
  public String[] likes;

  public Geek(String nom) {
    this.nom = nom;
  }

  public Geek(String nom, String... likes) {
    this.nom = nom;
    this.likes = likes;
  }

  public String getNom() {
    return nom;
  }
}

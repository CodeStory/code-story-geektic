package geeks;

public class Geek {
  public final String nom;
  public final String[] likes;
  public String imageUrl;

  public Geek(String nom, String... likes) {
    this.nom = nom;
    this.likes = likes;
  }

  public String getNom() {
    return nom;
  }

  public String getImageUrl() {
    return imageUrl;
  }
}

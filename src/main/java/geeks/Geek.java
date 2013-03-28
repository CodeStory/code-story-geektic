package geeks;

import java.util.Random;

public class Geek {
	public final String nom;
  public String[] likes;
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

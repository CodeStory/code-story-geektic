package geeks;

import java.util.Random;

public class Geek {
	public static final Random RANDOM = new Random();

	public final String nom;
  public final String image;
  public String[] likes;

  public Geek(String nom, String... likes) {
    this.nom = nom;
    this.likes = likes;
		this.image = "geek" + RANDOM.nextInt(8);
  }

  public String getNom() {
    return nom;
  }
}

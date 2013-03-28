package geeks;

public class Geek {
	public final String nom;
	public String like1;
	public String like2;
	public String like3;

	public Geek(String nom) {
		this.nom = nom;
	}

  public String getNom() {
    return nom;
  }

  public String getLike1() {
    return like1;
  }
}

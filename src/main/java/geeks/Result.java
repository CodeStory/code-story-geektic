package geeks;

import com.google.common.hash.Hashing;

public class Result {
  public final String prenom;
  public final String gravatar;
  public final String ville;
  public final String like1;
  public final String like2;
  public final String like3;

  public Result(String prenom, String email, String ville, String like1, String like2, String like3) {
    this.prenom = prenom;
    this.gravatar = "http://gravatar.com/avatar/" + Hashing.md5().hashBytes(email.getBytes());
    this.ville = ville;
    this.like1 = like1;
    this.like2 = like2;
    this.like3 = like3;
  }
}

package geeks;

import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;

public class Geek {
  public String nom;
  public String prenom;
  public String email;
  public String ville;
  public String like1;
  public String like2;
  public String like3;
  public String gravatar;

  public void anonymize() {
    this.gravatar = "http://gravatar.com/avatar/" + Hashing.md5().hashBytes(email.getBytes());
    this.email = null;
  }

  public static void main(String[] args) throws Exception {
    Geek geek = new Geek();
    geek.email = "david@gageot.net";
    geek.nom = "David";
    geek.prenom = "Gageot";
    System.out.println(new Gson().toJson(geek));
  }

  public boolean matches(String search) {
    if (Strings.isNullOrEmpty(search)) {
      return false;
    }
    return matchesAny(search, like1, like2, like3);
  }

  private static boolean matchesAny(String search, String... terms) {
    String trimmedSearch = search.trim();

    for (String term : terms) {
      if (matches(trimmedSearch, term.trim())) {
        return true;
      }
    }

    return false;
  }

  private static boolean matches(String search, String term) {
    return search.equalsIgnoreCase(term)
      || plural(search).equalsIgnoreCase(term)
      || search.equalsIgnoreCase(term + "s");
  }

  private static String plural(String word) {
    return word + "s";
  }
}

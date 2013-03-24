package geeks;

import com.google.common.base.Strings;

public class Geek {
  public String nom;
  public String prenom;
  public String email;
  public String ville;
  public String like1;
  public String like2;
  public String like3;

  public Result toResult() {
    return new Result(prenom, ville, like1, like2, like3, email);
  }

  public boolean matches(String search) {
    if (Strings.isNullOrEmpty(search)) {
      return false;
    }
    if (search.length() < 3) {
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

package geeks;

import static com.google.common.base.Strings.isNullOrEmpty;

public class Geek {
  public String prenom;
  public String email;
  public String ville;
  public String like1;
  public String like2;
  public String like3;

  public Result toResult() {
    return new Result(prenom, email, ville, like1, like2, like3);
  }

  public boolean matches(String search) {
    return !isNullOrEmpty(search) && (search.length() >= 3) && matchesAny(search, like1, like2, like3);
  }

  private static boolean matchesAny(String search, String... terms) {
    for (String term : terms) {
      if (matches(search.trim(), term.trim())) {
        return true;
      }
    }

    return false;
  }

  private static boolean matches(String search, String term) {
    return search.equalsIgnoreCase(term)
      || plural(search).equalsIgnoreCase(term)
      || search.equalsIgnoreCase(plural(term));
  }

  private static String plural(String word) {
    return word + "s";
  }
}

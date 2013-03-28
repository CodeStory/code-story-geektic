package geeks;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class GeeksTest {
  Geeks geeks = new Geeks();

  @Test
  public void no_geek_matches_empty_keywords() {
    geeks.addGeek(geek("Xavier", "java"));
    geeks.addGeek(geek("Martin", "scala"));

    assertThat(geeks.search("")).isEmpty();
  }

  @Test
  public void should_search_java_geek() {
    geeks.addGeek(geek("Xavier", "java"));

    assertThat(geeks.search("java")).onProperty("nom").containsOnly("Xavier");
  }

  @Test
  public void should_search_one_java_geek() {
    geeks.addGeek(geek("Xavier", "java"));
    geeks.addGeek(geek("Martin", "scala"));

    assertThat(geeks.search("scala")).onProperty("nom").containsOnly("Martin");
  }

  static Geek geek(String name, String like) {
    Geek martin = new Geek(name);
    martin.like1 = like;
    return martin;
  }
}

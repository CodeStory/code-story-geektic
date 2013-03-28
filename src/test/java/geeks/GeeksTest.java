package geeks;

import org.junit.Test;

import java.io.IOException;

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

  @Test
  public void should_search_case_insensitive() {
    geeks.addGeek(geek("Xavier", "java"));
    geeks.addGeek(geek("Martin", "scala"));

    assertThat(geeks.search("SCaLa")).onProperty("nom").containsOnly("Martin");
  }

  @Test
  public void should_search_on_any_keyword() {
		geeks.addGeek(geek("Xavier", "java", "coffee"));

		assertThat(geeks.search("coffee")).onProperty("nom").containsOnly("Xavier");
	}

	@Test
	public void should_load_geeks() throws IOException {
		geeks.load();

		assertThat(geeks.search("java")).onProperty("nom").containsOnly("Ardhuin", "Baligand", "Biville", "Bonvillain", "Cheype", "Dhordain", "Gageot", "Guérin", "Hanin", "Labouisse", "Le Merdy", "Le Merdy", "Leclaire", "Renou", "Tremblay", "Voisin", "Wauquier", "Gosling");
	}

	static Geek geek(String name, String... likes) {
    return new Geek(name, likes);
  }
}

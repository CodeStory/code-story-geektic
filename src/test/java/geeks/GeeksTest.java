package geeks;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static org.fest.assertions.Assertions.assertThat;

public class GeeksTest {
  private final Geeks geeks = new Geeks();
  private File geeksFile;

  @Before
  public void setUp() throws Exception {
    geeksFile = File.createTempFile("geektic", ".json");
    geeksFile.deleteOnExit();
    geeks.setLocalDataLocation(geeksFile);
  }

  @Test
  public void no_geek_matches_empty_keywords() {
    geeks.addGeek(geek("Xavier", "image", "java"));
    geeks.addGeek(geek("Martin", "image", "scala"));

    assertThat(geeks.search("")).isEmpty();
  }

  @Test
  public void should_search_java_geek() {
    geeks.addGeek(geek("Xavier", "http://exemple.org/sample.jpg", "java"));

    Collection<Geek> javaGeeks = geeks.search("java");
    assertThat(javaGeeks).onProperty("nom").containsOnly("Xavier");
    assertThat(javaGeeks).onProperty("imageUrl").containsOnly("http://exemple.org/sample.jpg");
  }

  @Test
  public void should_search_one_java_geek() {
    geeks.addGeek(geek("Xavier", "image", "java"));
    geeks.addGeek(geek("Martin", "image", "scala"));

    assertThat(geeks.search("scala")).onProperty("nom").containsOnly("Martin");
  }

  @Test
  public void should_search_case_insensitive() {
    geeks.addGeek(geek("Xavier", "image", "java"));
    geeks.addGeek(geek("Martin", "image", "scala"));

    assertThat(geeks.search("SCaLa")).onProperty("nom").containsOnly("Martin");
  }

  @Test
  public void should_search_on_any_keyword() {
    geeks.addGeek(geek("Xavier", "image", "java", "coffee"));

    assertThat(geeks.search("coffee")).onProperty("nom").containsOnly("Xavier");
  }

  @Test
  public void should_search_on_two_keywords() {
    geeks.addGeek(geek("Xavier", "image", "java", "coffee"));
    geeks.addGeek(geek("Christophe", "image", "java", "linux"));

    assertThat(geeks.search("coffee linux")).onProperty("nom").containsOnly("Xavier", "Christophe");
  }

  @Test
  public void should_search_on_two_keywords_avoid_duplaicates() {
    geeks.addGeek(geek("Xavier", "image", "java", "coffee"));
    geeks.addGeek(geek("Christophe", "image", "java", "linux"));

    assertThat(geeks.search("coffee java")).hasSize(2);
  }

  @Test
  public void should_store_geeks() throws Exception {
    geeks.load();
    geeks.addGeek(geek("Azerty", "image", "rien"));
    geeks.load();
    assertThat(geeks.search("rien")).onProperty("nom").containsOnly("Azerty");
  }

  @Test
  public void should_load_geeks() throws IOException {
    geeks.load();
    assertThat(geeks.search("java")).onProperty("nom").containsOnly("Ardhuin", "Baligand", "Biville", "Bonvillain", "Cheype", "Dhordain", "Gageot", "Guérin", "Hanin", "Labouisse", "Le Merdy", "Le Merdy", "Leclaire", "Renou", "Tremblay", "Voisin", "Wauquier", "Gosling");
  }

  @Test
  public void should_load_geeks_local_geeks() throws IOException {
    Files.write("[   {\n" +
      "    \"nom\": \"Hanin\",\n" +
      "    \"prenom\": \"Xavier\",\n" +
      "    \"email\": \"xavier.hanin@gmail.com\",\n" +
      "    \"ville\": \"Bordeaux\",\n" +
      "    \"likes\": [\"Developpement\",\n" +
      "      \"Java\",\n" +
      "      \"Web\"],\n" +
      "    \"hate1\": \"Perdre du temps\",\n" +
      "    \"hate2\": \"Le closed source\"\n" +
      "  }]", geeksFile, Charsets.UTF_8);
    geeks.load();
    assertThat(geeks.search("java")).onProperty("nom").containsOnly("Hanin");
  }

  static Geek geek(String name, String imageUrl, String... likes) {
    Geek geek = new Geek(name, likes);
    geek.imageUrl = imageUrl;
    return geek;
  }
}

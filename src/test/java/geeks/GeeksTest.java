package geeks;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class GeeksTest {

  @Test
  public void should_find_added_geek() throws Exception {
    Geek geek = new Geek("Xavier");
    geek.like1 = "java";

    Geeks geeks = new Geeks();
    geeks.addGeek(geek);

    assertThat(geeks.getGeeks()).onProperty("nom").containsOnly("Xavier");
  }

}

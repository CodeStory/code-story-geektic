package resources;

import geeks.Geek;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class SearchResourceTest {

  private SearchResource searchResource = new SearchResource();

  @Test
  public void testSearchGeek() throws Exception {
    List<String> geeks = searchResource.searchGeeks("java");
    assertThat(geeks).contains("David");
  }

  @Test
  public void should_display_no_geeks() {
    assertThat(searchResource.searchGeeks("")).isEmpty();
  }

  @Test
  public void should_search_scala_geeks() {
    List<String> geeks = searchResource.searchGeeks("scala");
    assertThat(geeks).contains("Martin");
  }

  @Test
  @Ignore
  public void should_find_added_geek() throws Exception {
    Geek geek = new Geek("Xavier");
    geek.like1 = "java";

    searchResource.addGeek(geek);

    assertThat(searchResource.searchGeeks("java")).contains("Xavier");
  }
}

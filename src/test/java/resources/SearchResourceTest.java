package resources;

import geeks.Geek;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class SearchResourceTest {
  SearchResource searchResource = new SearchResource();

  @Test
  public void should_find_nothing() {
    List<Geek> geeks = searchResource.json("");

    assertThat(geeks).isEmpty();
  }

  @Test
  public void should_find_geek_by_like() {
    assertThat(searchResource.json("Smalltalk")).hasSize(1);
    assertThat(searchResource.json("java")).hasSize(18);
  }
}

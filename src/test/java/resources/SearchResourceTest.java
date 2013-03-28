package resources;

import geeks.Geek;
import geeks.Geeks;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchResourceTest {
  @Mock
  Geeks geeks;

  @InjectMocks
  SearchResource searchResource;

  @Test
  public void should_search() {
    List<Geek> geeks = asList(new Geek("David"));
    when(this.geeks.search("java")).thenReturn(geeks);

    assertThat(searchResource.searchGeeks("java")).isSameAs(geeks);
  }
}

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class HomePageTest extends AbstractPageTest {
  @Before
  public void go_to_homepage() {
    goTo("/");
  }

  @Test
  public void should_display_homepage() {
    assertThat(title()).isEqualTo("CodeStory - Geektic");
  }

  @Test
  public void should_display_no_geeks() {
    assertThat(find("#geeks").getText()).isNull();
  }

  @Test
  public void should_search_java_geeks() {
    fill("#searchInput").with("java");

    assertThat(find("#geeks").getText()).contains("David");
  }

  @Test
  public void should_search_scala_geeks() {
    fill("#searchInput").with("scala");

    assertThat(find("#geeks").getText()).contains("Martin");
  }

  @Test
  public void should_clear_search_field() {
    fill("#searchInput").with("java");
    await().until("#geeks").withText().contains("David");

    clear("#searchInput");
    await().until("#geeks").withText().equalTo("");
  }
}
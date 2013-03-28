import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class HomePageTest extends AbstractPageTest {
  @Test
  public void should_display_homepage() {
    goTo("/");

    assertThat(title()).isEqualTo("CodeStory - Geektic");
  }

  @Test
  public void should_display_no_geeks() {
    goTo("/");

    assertThat(find("#geeks").getText()).isEmpty();
  }

  @Test
  public void should_search_java_geeks() {
    goTo("/");

    fill("#searchInput").with("java");

    assertThat(find("#geeks").getText()).contains("David");
  }

  @Test
  public void should_search_scala_geeks() {
    goTo("/");

    fill("#searchInput").with("scala");

    assertThat(find("#geeks").getText()).contains("Martin");
  }

  @Test
  public void should_clear_search_field() {
    goTo("/");

    fill("#searchInput").with("java");

    await().until("#geeks").withText().contains("David");

    clear("#searchInput");

    await().until("#geeks").withText().equalTo("");
  }
}
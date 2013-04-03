import com.google.common.base.Predicate;
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
    assertThat(find("#geeks").getText()).isEmpty();
  }

  @Test
  public void should_search_geeks_who_love_cats() {
    fill("#searchInput").with("chats");

    assertThat(find("#geeks .square .caption").getText()).contains("Gageot");
    assertThat(find("#geeks .square .caption").getText()).contains("Java Tests Chats");
  }

  @Test
  public void should_search_scala_geeks() {
    fill("#searchInput").with("scala");

    assertThat(find("#geeks").getText()).contains("Odersky");
  }

  @Test
  public void should_clear_search_field() {
    fill("#searchInput").with("java");
    await().until("#geeks").withText().contains("David");

    clear("#searchInput");
    await().until("#geeks").withText().equalTo("");
  }

  @Test
  public void should_have_an_url_with_the_tags() {
    fill("#searchInput").with("java");

    await().until(new Predicate<Object>() {
      @Override
      public boolean apply(Object input) {
        return url().contains("?keyword=java");
      }
    });
  }
}
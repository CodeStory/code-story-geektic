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
    assertThat(find("#geeks").getText()).isEmpty();
  }

  @Test
  public void should_search_geeks_who_love_cats() {
    fill("#searchInput").with("chats");

    await().until("#geeks .square .caption").withText().contains("Gageot");
    await().until("#geeks .square .caption").withText().contains("Java Tests Chats");
  }

  @Test
  public void should_search_scala_geeks() {
    fill("#searchInput").with("scala");

    await().until("#geeks").withText().contains("Odersky");
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
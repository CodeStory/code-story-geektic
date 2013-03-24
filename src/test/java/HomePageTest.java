import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class HomePageTest extends AbstractPageTest {
  @Test
  public void should_display_homepage() {
    goTo("/");

    assertThat(title()).isEqualTo("CodeStory - Geektic");
  }
}
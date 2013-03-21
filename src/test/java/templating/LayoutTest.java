package templating;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class LayoutTest {
  @Test
  public void should_apply_layout() {
    Layout layout = new Layout("header/[[body]]/footer");

    String content = layout.apply("<body>Hello</body>");

    assertThat(content).isEqualTo("header/<body>Hello</body>/footer");
  }
}

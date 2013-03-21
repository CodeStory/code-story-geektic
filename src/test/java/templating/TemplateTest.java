package templating;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TemplateTest {
  Template template = new Template();

  @Test
  public void should_get_original_content() {
    String content = "<CONTENT WITHOUT TEMPLATING>";
    String result = template.apply(content, ImmutableMap.of());

    assertThat(result).isEqualTo(content);
  }

  @Test
  public void should_replace_variable() {
    String content = "BEFORE-[[KEY]]-AFTER";
    String result = template.apply(content, ImmutableMap.of("KEY", "VALUE"));

    assertThat(result).isEqualTo("BEFORE-VALUE-AFTER");
  }

  @Test
  public void should_ignore_standard_mustaches() {
    String content = "BEFORE-[[KEY]]-{{IGNORED}}-AFTER";
    String result = template.apply(content, ImmutableMap.of("KEY", "VALUE"));

    assertThat(result).isEqualTo("BEFORE-VALUE-{{IGNORED}}-AFTER");
  }

  @Test
  public void should_ignore_body() {
    String content = "BEFORE-[[body]]-AFTER";
    String result = template.apply(content, ImmutableMap.of());

    assertThat(result).isEqualTo("BEFORE-[[body]]-AFTER");
  }

  @Test
  public void should_include_version() {
    String content = "[[version]]";
    String result = template.apply(content, ImmutableMap.of());

    assertThat(result).isEqualTo("GIT_HASH");
  }
}

package webserver;

import org.junit.Test;
import webserver.internal.PathMatcher;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

public class PathMatcherTest {
  @Test
  public void should_match_exact_path() {
    assertThat(new PathMatcher("/").match("/").matches).isTrue();
    assertThat(new PathMatcher("/path").match("/path").matches).isTrue();
    assertThat(new PathMatcher("/a/b/c").match("/a/b/c").matches).isTrue();
  }

  @Test
  public void should_reject_inexact_path() {
    assertThat(new PathMatcher("/").match("/other").matches).isFalse();
    assertThat(new PathMatcher("/path").match("/other").matches).isFalse();
  }

  @Test
  public void should_reject_additional_slashes() {
    assertThat(new PathMatcher("/").match("//").matches).isFalse();
  }

  @Test
  public void should_match_path_with_param() {
    assertThat(new PathMatcher("/{param}").match("/hello").matches).isTrue();
  }

  @Test
  public void should_reject_path_with_missing_param() {
    assertThat(new PathMatcher("/{param}").match("/").matches).isFalse();
  }

  @Test
  public void should_reject_path_with_additional_param() {
    assertThat(new PathMatcher("/{param}").match("/PARAM1/PARAM2").matches).isFalse();
  }

  @Test
  public void should_recognize_invalid_param_syntax() {
    assertThat(new PathMatcher("/{param}").match("/{param").matches).isTrue();
    assertThat(new PathMatcher("/{param").match("/invalid").matches).isFalse();
    assertThat(new PathMatcher("/p{aram}").match("/invalid").matches).isFalse();
    assertThat(new PathMatcher("/{para}m").match("/invalid").matches).isFalse();
  }

  @Test
  public void should_match_partial_param() {
    assertThat(new PathMatcher("/A{param}C").match("/ABC").matches).isTrue();
    assertThat(new PathMatcher("/A{param}C").match("/ABC").params).includes(entry("param", "B"));
  }

  @Test
  public void should_extract_params() {
    assertThat(new PathMatcher("/{param1}/{param2}").match("/A/B").params)
      .hasSize(2)
      .includes(entry("param1", "A"))
      .includes(entry("param2", "B"));
  }

  @Test
  public void should_match_real_world_example() {
    assertThat(new PathMatcher("/static/{version}/css/{path}.css").match("/static/GIT_HASH/css/bootstrap.css").matches).isTrue();
  }
}

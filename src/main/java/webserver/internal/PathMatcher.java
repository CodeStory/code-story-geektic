package webserver.internal;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathMatcher {
  private static final CharMatcher SLASH = CharMatcher.anyOf("/");
  private static final Splitter ON_SLASH = Splitter.on(SLASH);
  private static final Pattern PATH_PATTERN = Pattern.compile(".*\\{(.+)\\}.*");

  private final int slashCount;
  private final Iterable<String> parts;

  public PathMatcher(String expected) {
    this.slashCount = SLASH.countIn(expected);
    this.parts = ON_SLASH.split(expected);
  }

  public Result match(String actual) {
    if (slashCount != CharMatcher.anyOf("/").countIn(actual)) {
      return Result.REJECT;
    }

    Map<String, String> params = Maps.newLinkedHashMap();

    Iterator<String> expectedParts = parts.iterator();
    Iterator<String> actualParts = ON_SLASH.split(actual).iterator();
    while (expectedParts.hasNext()) {
      String expectedPart = expectedParts.next();
      String actualPart = actualParts.next();

      Matcher expectedMatcher = PATH_PATTERN.matcher(expectedPart);
      if (expectedMatcher.matches()) {
        String pattern = expectedPart.replaceFirst("\\{.+\\}", "(.+)");
        Matcher actualMatcher = Pattern.compile(pattern).matcher(actualPart);
        if (!actualMatcher.matches()) {
          return Result.REJECT;
        }

        params.put(expectedMatcher.group(1), actualMatcher.group(1));
      } else if (!expectedPart.equals(actualPart)) {
        return Result.REJECT;
      }
    }

    return Result.match(params);
  }

  public static class Result {
    static final Result REJECT = new Result(false, ImmutableMap.<String, String>of());

    public boolean matches;
    public ImmutableMap<String, String> params;

    private Result(boolean matches, Map<String, String> params) {
      this.matches = matches;
      this.params = ImmutableMap.copyOf(params);
    }

    static Result match(Map<String, String> params) {
      return new Result(true, params);
    }
  }
}

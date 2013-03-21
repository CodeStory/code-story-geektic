package templating;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

public class Template {
  private final DefaultMustacheFactory mustacheFactory = new DefaultMustacheFactory();

  public String apply(String content, Map<?, ?> variables) {
    Mustache mustache = mustacheFactory.compile(new StringReader(content), content, "[[", "]]");

    Map<?, ?> additional = ImmutableMap.of("body", "[[body]]", "version", readGitHash());
    try {
      StringWriter output = new StringWriter();
      mustache.execute(output, new Object[]{variables, additional}).flush();
      return output.toString();
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  private static String readGitHash() {
    try {
      String hash = Resources.toString(Resources.getResource("version.txt"), Charsets.UTF_8);
      return hash.replace("$Format:%H$", "GIT_HASH");
    } catch (IOException e) {
      throw new IllegalStateException("Unable to read version.txt in the classpath");
    }
  }
}

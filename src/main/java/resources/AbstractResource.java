package resources;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Files;
import templating.ContentWithVariables;
import templating.Layout;
import templating.Template;
import templating.YamlFrontMatter;
import webserver.NotFoundException;
import webserver.Response;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public abstract class AbstractResource {
  private static final long ONE_YEAR = 1000L * 3600 * 24 * 365;

  protected Response ok(Object entity, long modified) {
    return Response.ok(entity).lastModified(new Date(modified)).expires(new Date(modified + ONE_YEAR)).header("Cache-Control", "public").build();
  }

  protected Response ok(File file) {
    return ok(file, file.lastModified());
  }

  protected Response okTemplatize(File file) {
    return ok(templatize(file, ImmutableMap.of()), file.lastModified());
  }

  private String templatize(File file, Map<?, ?> variables) {
    ContentWithVariables yaml = new YamlFrontMatter().parse(read(file));

    Map<String, String> yamlVariables = yaml.getVariables();
    String content = yaml.getContent();

    String layout = yamlVariables.get("layout");
    if (layout != null) {
      content = new Layout(read(file(layout))).apply(content);
    }

    return new Template().apply(content, ImmutableMap.builder().putAll(variables).putAll(yamlVariables).build());
  }

  protected String read(File file) {
    try {
      return Files.toString(file, Charsets.UTF_8);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  protected File file(String path) {
    if (!exists(path)) {
      throw new NotFoundException();
    }
    return new File("web", path);
  }

  protected boolean exists(String path) {
    if (path.endsWith("/")) {
      return false;
    }

    try {
      File root = new File("web");
      File file = new File(root, path);
      if (!file.exists() || !file.getCanonicalPath().startsWith(root.getCanonicalPath())) {
        return false;
      }

      return true;
    } catch (IOException e) {
      return false;
    }
  }
}

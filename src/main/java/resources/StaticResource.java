package resources;

import org.jcoffeescript.JCoffeeScriptCompileException;
import org.jcoffeescript.JCoffeeScriptCompiler;
import org.jcoffeescript.Option;
import org.lesscss.LessCompiler;
import org.lesscss.LessException;
import webserver.GET;
import webserver.PathParam;
import webserver.Response;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class StaticResource extends AbstractResource {
  private final LessCompiler lessCompiler;
  private final JCoffeeScriptCompiler coffeeScriptCompiler;

  public StaticResource() {
    lessCompiler = new LessCompiler();
    coffeeScriptCompiler = new JCoffeeScriptCompiler(Arrays.asList(Option.BARE));
  }

  @GET(path = "/static/{version}/css/{path}", produces = "text/css;charset=UTF-8")
  public Response cssOrLess(@PathParam("path") String path) throws IOException, LessException {
    path = "css/" + path;
    // Css
    if (exists(path)) {
      File css = file(path);
      return okTemplatize(css);
    }

    // Less
    File output = new File("target", path);
    synchronized (lessCompiler) {
      lessCompiler.compile(file(path.replace(".css", ".less")), output, false);
    }
    return okTemplatize(output);
  }

  @GET(path = "/static/{version}/js/{path}", produces = "application/javascript;charset=UTF-8")
  public Response js(@PathParam("path") String path) throws JCoffeeScriptCompileException {
    path = "js/" + path;
    // Js
    if (exists(path)) {
      File js = file(path);
      return ok(js);
    }

    // Coffee
    File coffee = file(path.replace(".js", ".coffee"));
    String js;
    synchronized (coffeeScriptCompiler) {
      js = coffeeScriptCompiler.compile(read(coffee));
    }
    return ok(js, coffee.lastModified());
  }

  @GET(path = "/static/{version}/img/{path}", produces = "image/png")
  public Response png(@PathParam("path") String path) {
    path = "img/" + path;
    return ok(file(path));
  }
}

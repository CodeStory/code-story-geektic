package resources;

import com.google.common.io.Files;
import webserver.Response;
import webserver.annotations.GET;
import webserver.annotations.PathParam;
import webserver.compilers.CoffeeScriptCompiler;
import webserver.compilers.LessCompiler;

import javax.inject.Inject;

import java.io.File;
import java.io.IOException;

public class StaticResource extends AbstractResource {
  private final LessCompiler lessCompiler;
  private final CoffeeScriptCompiler coffeeScriptCompiler;

  @Inject
  public StaticResource(CoffeeScriptCompiler coffeeScriptCompiler, LessCompiler lessCompiler) {
    this.coffeeScriptCompiler = coffeeScriptCompiler;
    this.lessCompiler = lessCompiler;
  }

  @GET(path = "/static/{version}/css/{path}", produces = "text/css;charset=UTF-8")
  public File css(@PathParam("path") String path) throws IOException {
    return file("static/css/" + path);
  }

  @GET(path = "/static/{version}/js/{path}", produces = "application/javascript;charset=UTF-8")
  public File js(@PathParam("path") String path) throws IOException {
    return file("static/js/" + path);
  }

  @GET(path = "/static/{version}/img/{path}")
  public Response png(@PathParam("path") String path) {
    String contentType = "image/" + Files.getFileExtension(path);

    return ok(file("static/img/" + path)).header("Content-type", contentType);
  }

  @GET(path = "/static/{version}/less/{path}", produces = "text/css;charset=UTF-8")
  public Response less(@PathParam("path") String path) throws IOException {
    File less = file(path);
    return ok(templatize(lessCompiler.compile(less))).lastModified(less);
  }

  @GET(path = "/static/{version}/coffee/{path}", produces = "application/javascript;charset=UTF-8")
  public Response coffee(@PathParam("path") String path) throws IOException {
    File coffee = file(path);
    return ok(coffeeScriptCompiler.compile(coffee)).lastModified(coffee);
  }
}

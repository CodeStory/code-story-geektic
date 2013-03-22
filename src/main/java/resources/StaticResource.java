package resources;

import webserver.compilers.CoffeeScriptCompiler;
import webserver.compilers.LessCompiler;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import java.io.File;

@Path("/static/{version}")
public class StaticResource extends AbstractResource {
  private final LessCompiler lessCompiler;
  private final CoffeeScriptCompiler coffeeScriptCompiler;

  @Inject
  public StaticResource(CoffeeScriptCompiler coffeeScriptCompiler, LessCompiler lessCompiler) {
    this.coffeeScriptCompiler = coffeeScriptCompiler;
    this.lessCompiler = lessCompiler;
  }

  @GET
  @Path("{path : .*\\.css}")
  @Produces("text/css;charset=UTF-8")
  public File css(@PathParam("path") String path) {
    return file("static", path);
  }

  @GET
  @Path("{path : .*\\.js}")
  @Produces("application/javascript;charset=UTF-8")
  public File js(@PathParam("path") String path) {
    return file("static", path);
  }

  @GET
  @Path("{path : .*\\.less}")
  @Produces("text/css;charset=UTF-8")
  public Response less(@PathParam("path") String path) {
    File less = file(path);
    return ok(templatize(lessCompiler.compile(less)), less.lastModified());
  }

  @GET
  @Path("{path : .*\\.coffee}")
  @Produces("application/javascript;charset=UTF-8")
  public Response coffee(@PathParam("path") String path) {
    File coffee = file(path);
    return ok(coffeeScriptCompiler.compile(coffee), coffee.lastModified());
  }

  @GET
  @Path("{path : .*\\.png}")
  @Produces("image/png")
  public File png(@PathParam("path") String path) {
    return file("static", path);
  }

  @GET
  @Path("{path : .*\\.jpg}")
  @Produces("image/jpeg")
  public File jpg(@PathParam("path") String path) {
    return file("static", path);
  }
}

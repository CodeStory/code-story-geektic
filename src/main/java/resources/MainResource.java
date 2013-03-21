package resources;

import webserver.GET;
import webserver.Response;

import java.io.File;

public class MainResource extends AbstractResource {
  @GET(path = "/", produces = "text/html;charset=UTF-8")
  public Response index() {
    return okTemplatize(file("index.html"));
  }

  @GET(path = "/appcache.manifest", produces = "text/cache-manifest;charset=UTF-8")
  public Response appcache() {
    File manifest = file("appcache.manifest");
    return okTemplatize(manifest);
  }
}

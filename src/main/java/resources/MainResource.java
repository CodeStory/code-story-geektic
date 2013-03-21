package resources;

import webserver.annotations.GET;

public class MainResource extends AbstractResource {
  @GET(path = "/", produces = "text/html;charset=UTF-8")
  public String index() {
    return templatize(file("index.html"));
  }

  @GET(path = "/appcache.manifest", produces = "text/cache-manifest;charset=UTF-8")
  public String appcache() {
    return templatize(file("appcache.manifest"));
  }
}

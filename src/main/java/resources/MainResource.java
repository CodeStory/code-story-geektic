package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import java.io.File;

@Path("/")
public class MainResource extends AbstractResource {
  @GET
  @Produces("text/html;charset=UTF-8")
  public Response index() {
    File file = file("index.html");
    return ok(templatize(read(file)), file.lastModified());
  }
}

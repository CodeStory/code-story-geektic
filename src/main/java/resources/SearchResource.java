package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Path("/search")
public class SearchResource extends AbstractResource {

  @GET
  public List<String> searchGeeks(@QueryParam("keywords") String keywords) {
    if ("java".equals(keywords)) {
      return Arrays.asList("David");
    }
    if ("scala".equals(keywords)) {
      return Arrays.asList("Martin");
    }

    return Collections.emptyList();
  }

}

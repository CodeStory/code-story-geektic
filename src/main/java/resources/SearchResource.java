package resources;

import geeks.Geek;
import geeks.Geeks;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Path("/search")
public class SearchResource extends AbstractResource {
  private final Geeks geeks;

  @Inject
  public SearchResource(Geeks geeks) {
    this.geeks = geeks;
  }

  @GET
  @Produces("application/json;encoding=utf-8")
  public Collection<Geek> searchGeeks(@QueryParam("keywords") String keywords) {
    return geeks.search(keywords);
  }
}

package resources;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import geeks.Geek;
import geeks.Result;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import java.util.List;

@Path("/search")
public class SearchResource extends AbstractResource {
  private Supplier<Geek[]> geeksSupplier;

  public SearchResource() {
    geeksSupplier = Suppliers.memoize(new Supplier<Geek[]>() {
      @Override
      public Geek[] get() {
        return new Gson().<Geek[]>fromJson(read(file("geeks.json")), Geek[].class);
      }
    });
  }

  @GET
  @Produces("application/json;charset=UTF-8")
  public List<Result> json(@QueryParam("q") String term) {
    List<Result> results = Lists.newArrayList();

    for (Geek geek : geeksSupplier.get()) {
      if (geek.matches(term)) {
        results.add(geek.toResult());
      }
    }

    return results;
  }
}

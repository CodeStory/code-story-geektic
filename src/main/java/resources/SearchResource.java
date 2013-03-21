package resources;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import geeks.Geek;
import geeks.Result;
import webserver.annotations.GET;
import webserver.annotations.QueryParam;

import java.util.List;

public class SearchResource extends AbstractResource {
  @GET(path = "/search", produces = "application/json;charset=UTF-8")
  public List<Result> json(@QueryParam("q") String term) {
    List<Result> results = Lists.newArrayList();

    for (Geek geek : readGeeks()) {
      if (geek.matches(term)) {
        results.add(geek.toResult());
      }
    }

    return results;
  }

  private Geek[] readGeeks() {
    return new Gson().<Geek[]>fromJson(read(file("geeks.json")), Geek[].class);
  }
}

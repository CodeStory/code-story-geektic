package resources;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import geeks.Geek;
import webserver.annotations.GET;
import webserver.annotations.QueryParam;

import java.util.List;

public class SearchResource extends AbstractResource {
  @GET(path = "/search", produces = "application/json;charset=UTF-8")
  public List<Geek> json(@QueryParam("q") String term) {
    List<Geek> results = Lists.newArrayList();

    for (Geek geek : readGeeks()) {
      if (geek.matches(term)) {
        geek.anonymize();
        results.add(geek);
      }
    }

    return results;
  }

  private Geek[] readGeeks() {
    return new Gson().<Geek[]>fromJson(read(file("geeks.json")), Geek[].class);
  }
}

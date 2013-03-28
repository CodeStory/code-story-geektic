package geeks;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Geeks {
  private Set<Geek> geekSet;

  public Geeks() {
    this.geekSet = new HashSet<>();
  }

  public void addGeek(Geek geek) {
    geekSet.add(geek);
  }

  public Collection<Geek> getGeeks() {
    return geekSet;
  }

}

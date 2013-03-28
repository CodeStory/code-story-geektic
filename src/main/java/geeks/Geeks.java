package geeks;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Geeks {
  private Set<Geek> geekSet;

  public Geeks() {
    this.geekSet = new HashSet<>();
  }

  public void addGeek(Geek geek) {
    geekSet.add(geek);
  }

  public Collection<Geek> search(String keyword) {
    List<Geek> friends = Lists.newArrayList();

    for (Geek geek : geekSet) {
      if (geek.like1.equals(keyword)) {
        friends.add(geek);
      }
    }

    return friends;
  }
}

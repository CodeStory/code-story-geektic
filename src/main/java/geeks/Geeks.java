package geeks;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

@Singleton
public class Geeks {
  private Set<Geek> geekSet;

  public Geeks() {
    this.geekSet = Sets.newCopyOnWriteArraySet();
  }

  public void addGeek(Geek geek) {
    geekSet.add(geek);
  }

  public Collection<Geek> search(String keyword) {
    List<Geek> friends = Lists.newArrayList();

    for (Geek geek : geekSet) {
      if (asList(geek.likes).contains(keyword)) {
        friends.add(geek);
      }
    }

    return friends;
  }
}

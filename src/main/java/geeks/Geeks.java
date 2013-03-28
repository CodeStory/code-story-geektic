package geeks;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.inject.Singleton;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Singleton
public class Geeks {
	private static final Random RANDOM = new Random();

	private Set<Geek> geekSet;

	public Geeks() {
		this.geekSet = Sets.newCopyOnWriteArraySet();
	}

	public void addGeek(Geek geek) {
		geekSet.add(geek);
	}

	public Collection<Geek> search(String keyword) {
		List<Geek> friends = Lists.newArrayList();

		if (Strings.isNullOrEmpty(keyword)) {
			return friends;
		}

		for (Geek geek : geekSet) {
			for (String like : geek.likes) {
				if (like.equalsIgnoreCase(keyword)) {
					friends.add(geek);
					break;
				}
			}
		}

		return friends;
	}

	public void load() throws IOException {
		String json = Files.toString(new File("web/geeks.json"), Charsets.UTF_8);

		for (Geek geek : new Gson().<Geek[]>fromJson(json, Geek[].class)) {
			geek.image = "geek" + RANDOM.nextInt(8);

			addGeek(geek);
		}
	}
}

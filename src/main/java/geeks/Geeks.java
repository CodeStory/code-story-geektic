package geeks;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.inject.Singleton;
import webserver.templating.Template;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static webserver.templating.Template.readGitHash;

@Singleton
public class Geeks {
	private static final Random RANDOM = new Random();
	private Set<Geek> geekSet;
	private File localDataLocation = new File("local-geeks.json");

	public Geeks() {
		this.geekSet = Sets.newCopyOnWriteArraySet();
	}

	public void addGeek(Geek geek) {
		geekSet.add(geek);
		try {
			Files.write(new Gson().toJson(geekSet), localDataLocation, Charsets.UTF_8);
		} catch (IOException e) {
			// FIXME
		}
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
		File geeksFile;
		String json = null;
		if (localDataLocation.exists()) {
			geeksFile = localDataLocation;
			json = Files.toString(geeksFile, Charsets.UTF_8);
		}
		if (Strings.isNullOrEmpty(json)) {
			geeksFile = new File("web/geeks.json");
			json = Files.toString(geeksFile, Charsets.UTF_8);
		}

		geekSet.clear();
		for (Geek geek : new Gson().<Geek[]>fromJson(json, Geek[].class)) {
			geek.imageUrl = String.format("static/%s/img/geek%s.jpg", readGitHash(), RANDOM.nextInt(8));

			addGeek(geek);
		}
	}

	public File getLocalDataLocation() {
		return localDataLocation;
	}

	public void setLocalDataLocation(File localDataLocation) {
		this.localDataLocation = localDataLocation;
	}
}

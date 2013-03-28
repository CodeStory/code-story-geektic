package twitter;

import com.google.inject.Inject;
import geeks.Geek;
import geeks.Geeks;
import twitter4j.Status;

public class TwitterCommands {

	@Inject
	private Geeks geeks;


	public Geek onTweet(Status status) {
		Geek geek = new Geek(status.getUser().getName());
		geeks.addGeek(geek);

		return geek;
	}

}

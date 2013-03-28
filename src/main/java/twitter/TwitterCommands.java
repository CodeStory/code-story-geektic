package twitter;

import com.google.common.base.Splitter;
import com.google.inject.Inject;
import geeks.Geek;
import geeks.Geeks;
import twitter4j.Status;

import java.util.Iterator;
import java.util.regex.Pattern;

import static com.google.common.base.Splitter.on;

public class TwitterCommands {
	public static final Splitter TWEET_PATTERN = on(Pattern.compile("(#geektic| )")).omitEmptyStrings();

	@Inject
	private Geeks geeks;

	public Geek onTweet(Status status) {
		Geek geek = new Geek(status.getUser().getName());
    String text = status.getText();

		Iterator<String> likes = TWEET_PATTERN.split(text).iterator();
		if (likes.hasNext()) geek.like1 = likes.next();
		if (likes.hasNext()) geek.like2 = likes.next();
		if (likes.hasNext()) geek.like3 = likes.next();

		geeks.addGeek(geek);

		return geek;
	}
}

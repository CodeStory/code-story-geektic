package twitter;

import com.google.inject.Inject;
import geeks.Geek;
import geeks.Geeks;
import twitter4j.Status;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Splitter.on;

public class TwitterCommands {
	public static final Pattern TWEET_PATTERN = Pattern.compile(".*#geektic (.+)");

	@Inject
	private Geeks geeks;

	public Geek onTweet(Status status) {
		Geek geek = new Geek(status.getUser().getName());
    String text = status.getText();

		Matcher matcher = TWEET_PATTERN.matcher(text);
		if (matcher.matches()) {
			Iterator<String> likes = on(' ').split(matcher.group(1)).iterator();
			if (likes.hasNext()) geek.like1 = likes.next();
			if (likes.hasNext()) geek.like2 = likes.next();
			if (likes.hasNext()) geek.like3 = likes.next();
		}

		geeks.addGeek(geek);

		return geek;
	}
}

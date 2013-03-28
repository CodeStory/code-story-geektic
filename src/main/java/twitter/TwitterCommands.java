package twitter;

import com.google.common.base.Splitter;
import com.google.inject.Inject;
import geeks.Geek;
import geeks.Geeks;
import twitter4j.Status;

import java.util.Iterator;
import java.util.regex.Pattern;

public class TwitterCommands {
	@Inject
	private Geeks geeks;

	public static void main(String[] args) {
		String text = "#geektic LIKE1 LIKE2 LIKE3";
		for (String part : Splitter.on(Pattern.compile("(#geektic| )")).omitEmptyStrings().split(text)) {
			System.out.println(part);
		}

	}

	public Geek onTweet(Status status) {
		Geek geek = new Geek(status.getUser().getName());

		String text = status.getText();
		Iterator<String> likes = Splitter.on(Pattern.compile("(#geektic| )")).omitEmptyStrings().split(text).iterator();
		if (likes.hasNext()){
			geek.like1 = likes.next();
		}
		if (likes.hasNext()){
			geek.like2 = likes.next();
		}
		if (likes.hasNext()){
			geek.like3 = likes.next();
		}

		geeks.addGeek(geek);

		return geek;
	}
}

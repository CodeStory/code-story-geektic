package twitter;

import com.google.inject.Inject;
import geeks.Geek;
import geeks.Geeks;
import twitter4j.Status;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Splitter.on;
import static com.google.common.collect.Iterables.toArray;

public class TwitterCommands {
  public static final Pattern TWEET_PATTERN = Pattern.compile(".*#geektic (.+)");

  @Inject
  private Geeks geeks;

  public void onTweet(Status status) {
    String text = status.getText();

    Matcher matcher = TWEET_PATTERN.matcher(text);
    if (!matcher.matches()) {
      return;
    }

    String[] likes = toArray(on(' ').split(matcher.group(1)), String.class);

    Geek geek = new Geek(status.getUser().getName(), likes);
    String profileImageURL = status.getUser().getBiggerProfileImageURL();
    if (profileImageURL != null) {
      geek.imageUrl = profileImageURL;
    }
    geeks.addGeek(geek);
  }
}

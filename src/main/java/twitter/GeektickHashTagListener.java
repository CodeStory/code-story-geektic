package twitter;

import com.google.inject.Inject;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class GeektickHashTagListener extends StatusAdapter {

  @Inject
  private TwitterCommands twitterCommands;

  public void start() {
		String accesstoken = System.getenv("ACCESSTOKEN");
		String accesstokensecret = System.getenv("ACCESSTOKENSECRET");
		String consumerkey = System.getenv("CONSUMERKEY");
		String consumersecret = System.getenv("CONSUMERSECRET");

		TwitterStream twitterStream = new TwitterStreamFactory(
      new ConfigurationBuilder()
        .setOAuthAccessToken(accesstoken)
        .setOAuthAccessTokenSecret(accesstokensecret)
        .setOAuthConsumerKey(consumerkey)
        .setOAuthConsumerSecret(consumersecret)
        .build()
    ).getInstance();

    twitterStream.addListener(this);
    twitterStream.filter(new FilterQuery().track(new String[] {"#geektic"}));
  }

  @Override
	public void onStatus(Status status) {
    twitterCommands.onTweet(status);
	}

	@Override
	public void onException(Exception ex) {
		ex.printStackTrace();
	}

}

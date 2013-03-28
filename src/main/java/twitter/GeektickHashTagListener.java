package twitter;

import com.google.inject.Inject;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class GeektickHashTagListener implements StatusListener {

  @Inject
  private TwitterCommands twitterCommands;

  public void start() {
    TwitterStream twitterStream = new TwitterStreamFactory(
      new ConfigurationBuilder()
        .setOAuthAccessToken(System.getenv("ACCESSTOKEN"))
        .setOAuthAccessTokenSecret(System.getenv("ACCESSTOKENSECRET"))
        .setOAuthConsumerKey(System.getenv("CONSUMERKEY"))
        .setOAuthConsumerSecret(System.getenv("CONSUMERSECRET"))
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
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
	}

	@Override
	public void onException(Exception ex) {
		ex.printStackTrace();
	}

}

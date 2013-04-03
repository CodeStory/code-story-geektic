package twitter;

import com.google.inject.Inject;
import twitter4j.FilterQuery;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class GeektickHashTagListener extends StatusAdapter {
  @Inject
  private TwitterCommands twitterCommands;

  public void start() {
    String accessToken = System.getenv("ACCESSTOKEN");
    String accessTokenSecret = System.getenv("ACCESSTOKENSECRET");
    String consumerKey = System.getenv("CONSUMERKEY");
    String consumerSecret = System.getenv("CONSUMERSECRET");

    TwitterStream twitterStream = new TwitterStreamFactory(
      new ConfigurationBuilder()
        .setOAuthAccessToken(accessToken)
        .setOAuthAccessTokenSecret(accessTokenSecret)
        .setOAuthConsumerKey(consumerKey)
        .setOAuthConsumerSecret(consumerSecret)
        .build()
    ).getInstance();

    twitterStream.addListener(this);
    twitterStream.filter(new FilterQuery().track(new String[]{"#geektic"}));
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

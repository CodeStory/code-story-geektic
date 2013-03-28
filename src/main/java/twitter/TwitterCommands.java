package twitter;

import geeks.Geek;
import twitter4j.Status;

public class TwitterCommands {


	public Geek onTweet(Status status) {
		return new Geek(status.getUser().getName());
	}

}

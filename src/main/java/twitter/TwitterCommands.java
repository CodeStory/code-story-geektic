package twitter;

import twitter4j.Status;

public class TwitterCommands {


	public String onTweet(Status status) {
		return status.getUser().getName();
	}

}

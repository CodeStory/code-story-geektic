package twitter;

import geeks.Geek;
import org.junit.Test;
import org.mockito.Mockito;
import twitter4j.Status;
import twitter4j.User;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TwitterCommandsTest {



	@Test
	public void should_create_geek_on_tweet() throws Exception {
		Status status = mock(Status.class);
		User user = mock(User.class);
		when(status.getUser()).thenReturn(user);
		when(user.getName()).thenReturn("Xavier Hanin");
		assertThat(new TwitterCommands().onTweet(status)).isEqualTo("Xavier Hanin");
	}



}

package twitter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.Status;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GeektickHashTagListenerTest {

	@InjectMocks
	private GeektickHashTagListener listener;

	@Mock
	private Status status;

	@Mock
	private TwitterCommands twitterCommands;

	@Test
	public void testOnStatus() throws Exception {
		listener.onStatus(status);

        verify(twitterCommands).onTweet(status);
	}
}

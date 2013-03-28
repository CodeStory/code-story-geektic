package twitter;

import geeks.Geek;
import geeks.Geeks;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import twitter4j.Status;
import twitter4j.User;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterCommandsTest {
	@Mock
	Status status;
	@Mock
	User user;
	@Mock
	private Geeks geeks;
	@Captor
	private ArgumentCaptor<Geek> geekCaptor;

	@InjectMocks
	private TwitterCommands twitterCommands;

	@Test
	public void should_create_geek_on_tweet() {
		when(status.getUser()).thenReturn(user);
		when(status.getText()).thenReturn("#geektic LIKE1 LIKE2 LIKE3");
		when(user.getName()).thenReturn("Xavier Hanin");

		twitterCommands.onTweet(status);

		verify(geeks).addGeek(geekCaptor.capture());

		Geek newGeek = geekCaptor.getValue();
		assertThat(newGeek.nom).isEqualTo("Xavier Hanin");
		assertThat(newGeek.like1).isEqualTo("LIKE1");
		assertThat(newGeek.like2).isEqualTo("LIKE2");
		assertThat(newGeek.like3).isEqualTo("LIKE3");
	}
}

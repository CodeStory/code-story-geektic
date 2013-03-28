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

import java.net.MalformedURLException;
import java.net.URL;

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
	public void should_create_geek_on_tweet() throws MalformedURLException {
		when(status.getUser()).thenReturn(user);
    when(user.getName()).thenReturn("Xavier Hanin");
    when(user.getProfileImageURL()).thenReturn(new URL("http://exemple.org/foo.jpg"));
		when(status.getText()).thenReturn("#geektic LIKE1 LIKE2 LIKE3");

		twitterCommands.onTweet(status);

		verify(geeks).addGeek(geekCaptor.capture());

		Geek newGeek = geekCaptor.getValue();
		assertThat(newGeek.nom).isEqualTo("Xavier Hanin");
    assertThat(newGeek.likes).contains("LIKE1", "LIKE2", "LIKE3");
    assertThat(newGeek.imageUrl).contains("http://exemple.org/foo.jpg");
	}

	@Test
	public void should_recognize_only_3_likes_after_geektic_hashtag() {
		when(status.getUser()).thenReturn(user);
		when(status.getText()).thenReturn("Hello World #geektic LIKE1 LIKE2 LIKE3 LIKE4");

		twitterCommands.onTweet(status);

		verify(geeks).addGeek(geekCaptor.capture());

		Geek newGeek = geekCaptor.getValue();
    assertThat(newGeek.likes).contains("LIKE1", "LIKE2", "LIKE3");
	}
}

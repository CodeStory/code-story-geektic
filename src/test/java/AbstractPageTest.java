import com.google.common.io.Files;
import com.google.inject.AbstractModule;
import com.google.inject.util.Modules;
import main.MainGeekticConfiguration;
import main.MainGeekticServer;
import misc.PhantomJsTest;
import org.junit.Before;

import java.util.Random;

import static org.mockito.Mockito.spy;

public abstract class AbstractPageTest extends PhantomJsTest {
  private static final int TRY_COUNT = 10;
  private static final int DEFAULT_PORT = 8183;
  private static Random RANDOM_PORT = new Random();

  private static int port;
  private static MainGeekticServer server;

  protected static Random random = spy(new Random(0));

  @Before
  public void startServerOnce() {
    if (server != null) {
      return;
    }

    for (int i = 0; i < TRY_COUNT; i++) {
      try {
        port = randomPort();
        server = new MainGeekticServer(Modules.override(new MainGeekticConfiguration(Files.createTempDir())).with(testConfiguration()));
        server.start(port);
        return;
      } catch (Exception e) {
        System.err.println("Unable to bind server: " + e);
      }
    }
    throw new IllegalStateException("Unable to start server");
  }

  private static AbstractModule testConfiguration() {
    return new AbstractModule() {
      @Override
      protected void configure() {
        bind(Random.class).toInstance(random);
      }
    };
  }

  private synchronized int randomPort() {
    return DEFAULT_PORT + RANDOM_PORT.nextInt(1000);
  }

  @Override
  protected String defaultUrl() {
    return "http://localhost:" + port;
  }
}
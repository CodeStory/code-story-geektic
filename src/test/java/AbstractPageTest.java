import main.MainGeekticConfiguration;
import main.MainGeekticServer;
import misc.PhantomJsTest;
import org.junit.Before;

import java.util.Random;

public abstract class AbstractPageTest extends PhantomJsTest {
  private static final int TRY_COUNT = 10;
  private static final int DEFAULT_PORT = 8183;
  private static Random RANDOM_PORT = new Random();

  private static int port;
  private static MainGeekticServer server;

  @Before
  public void startServerOnce() {
    if (server != null) {
      return;
    }

    for (int i = 0; i < TRY_COUNT; i++) {
      try {
        port = randomPort();
        server = new MainGeekticServer(new MainGeekticConfiguration());
        server.start(port);
        return;
      } catch (Exception e) {
        System.err.println("Unable to bind server: " + e);
      }
    }
    throw new IllegalStateException("Unable to start server");
  }

  private synchronized int randomPort() {
    return DEFAULT_PORT + RANDOM_PORT.nextInt(1000);
  }

  @Override
  protected String defaultUrl() {
    return "http://localhost:" + port;
  }
}
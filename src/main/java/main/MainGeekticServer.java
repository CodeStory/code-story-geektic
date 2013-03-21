package main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import resources.MainResource;
import resources.SearchResource;
import resources.StaticResource;
import webserver.FastHttpServer;

import java.io.File;
import java.io.IOException;

import static com.google.common.base.Objects.firstNonNull;
import static java.lang.Integer.parseInt;

public class MainGeekticServer {
  private final Injector injector;

  public MainGeekticServer(Module module) {
    this.injector = Guice.createInjector(module);
  }

  public static void main(String[] args) throws IOException {
    int port = parseInt(firstNonNull(System.getenv("PORT"), "8080"));

    Module configuration = new MainGeekticConfiguration(new File("."));

    MainGeekticServer server = new MainGeekticServer(configuration);
    server.start(port);
  }

  public void start(int port) throws IOException {
    System.out.println("Starting server on port: " + port);

    new FastHttpServer(
      injector.getInstance(StaticResource.class),
      injector.getInstance(MainResource.class),
      injector.getInstance(SearchResource.class)
    ).start(port);
  }
}

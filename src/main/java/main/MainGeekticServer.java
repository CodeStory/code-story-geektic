package main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.jersey.api.container.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.simple.container.SimpleServerFactory;
import geeks.Geeks;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import resources.MainResource;
import resources.SearchResource;
import resources.StaticResource;
import twitter.GeektickHashTagListener;

import java.io.IOException;

import static com.google.common.base.Objects.firstNonNull;
import static com.sun.jersey.api.core.ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS;
import static com.sun.jersey.api.core.ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS;
import static java.lang.Integer.parseInt;

public class MainGeekticServer {
  private final Injector injector;

  public MainGeekticServer() {
    injector = Guice.createInjector();
  }

  public static void main(String[] args) throws IOException {
    int port = parseInt(firstNonNull(System.getenv("PORT"), "8080"));

    MainGeekticServer geekticServer = new MainGeekticServer();
    geekticServer.start(port);
    geekticServer.startTwitterListener();
  }

  public void start(int port) throws IOException {
    System.out.println("Starting server on port: " + port);

    loadGeeks();
    SimpleServerFactory.create("http://localhost:" + port, configuration());
  }

  private ResourceConfig configuration() throws IOException {
    DefaultResourceConfig config = new DefaultResourceConfig();

    config.getClasses().add(JacksonJsonProvider.class);

    config.getSingletons().add(injector.getInstance(MainResource.class));
    config.getSingletons().add(injector.getInstance(StaticResource.class));
    config.getSingletons().add(injector.getInstance(SearchResource.class));

    config.getProperties().put(PROPERTY_CONTAINER_REQUEST_FILTERS, GZIPContentEncodingFilter.class);
    config.getProperties().put(PROPERTY_CONTAINER_RESPONSE_FILTERS, GZIPContentEncodingFilter.class);

    return config;
  }

  private void loadGeeks() throws IOException {
    Geeks geeks = injector.getInstance(Geeks.class);
    geeks.load();
  }

  private void startTwitterListener() {
    GeektickHashTagListener listener = injector.getInstance(GeektickHashTagListener.class);
    listener.start();
  }
}

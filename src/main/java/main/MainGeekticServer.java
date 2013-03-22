package main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.sun.jersey.api.container.filter.GZIPContentEncodingFilter;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.simple.container.SimpleServerFactory;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import resources.MainResource;
import resources.SearchResource;
import resources.StaticResource;

import java.io.File;
import java.io.IOException;

import static com.google.common.base.Objects.firstNonNull;
import static com.sun.jersey.api.core.ResourceConfig.PROPERTY_CONTAINER_REQUEST_FILTERS;
import static com.sun.jersey.api.core.ResourceConfig.PROPERTY_CONTAINER_RESPONSE_FILTERS;
import static java.lang.Integer.parseInt;

public class MainGeekticServer {
  private final Injector injector;

  public MainGeekticServer(Module module) {
    this.injector = Guice.createInjector(module);
  }

  public static void main(String[] args) throws IOException {
    int port = parseInt(firstNonNull(System.getenv("PORT"), "8080"));

    Module configuration = new MainGeekticConfiguration(new File("."));

    new MainGeekticServer(configuration).start(port);
  }

  public void start(int port) throws IOException {
    System.out.println("Starting server on port: " + port);

    SimpleServerFactory.create("http://localhost:" + port, configuration());
  }

  private ResourceConfig configuration() {
    DefaultResourceConfig config = new DefaultResourceConfig();

    config.getClasses().add(JacksonJsonProvider.class);

    config.getSingletons().add(injector.getInstance(SearchResource.class));
    config.getSingletons().add(injector.getInstance(MainResource.class));
    config.getSingletons().add(injector.getInstance(StaticResource.class));

    config.getProperties().put(PROPERTY_CONTAINER_REQUEST_FILTERS, GZIPContentEncodingFilter.class);
    config.getProperties().put(PROPERTY_CONTAINER_RESPONSE_FILTERS, GZIPContentEncodingFilter.class);

    return config;
  }
}

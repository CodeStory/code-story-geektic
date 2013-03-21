package main;

import com.google.inject.AbstractModule;

import java.io.File;

import static com.google.inject.name.Names.named;

public class MainGeekticConfiguration extends AbstractModule {
  private final File workingDir;

  public MainGeekticConfiguration(File workingDir) {
    this.workingDir = workingDir;
  }

  @Override
  protected void configure() {
    bind(File.class).annotatedWith(named("votes.file")).toInstance(new File(workingDir, "votes"));
  }
}

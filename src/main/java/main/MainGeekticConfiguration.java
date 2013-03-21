package main;

import com.google.inject.AbstractModule;

import java.io.File;

public class MainGeekticConfiguration extends AbstractModule {
  private final File workingDir;

  public MainGeekticConfiguration(File workingDir) {
    this.workingDir = workingDir;
  }

  @Override
  protected void configure() {
  }
}

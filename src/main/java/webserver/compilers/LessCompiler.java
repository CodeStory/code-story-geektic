package webserver.compilers;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.lesscss.LessException;

import java.io.File;
import java.io.IOException;

public class LessCompiler {
  private final org.lesscss.LessCompiler compiler;

  public LessCompiler() {
    this.compiler = new org.lesscss.LessCompiler();
  }

  public synchronized String compile(File file) throws IOException {
    try {
      return compiler.compile(Files.toString(file, Charsets.UTF_8));
    } catch (LessException e) {
      throw new IOException("Invalid less file", e);
    }
  }
}

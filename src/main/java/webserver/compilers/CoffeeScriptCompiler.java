package webserver.compilers;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.jcoffeescript.JCoffeeScriptCompileException;
import org.jcoffeescript.JCoffeeScriptCompiler;
import org.jcoffeescript.Option;

import java.io.File;
import java.io.IOException;

import static java.util.Arrays.asList;

public class CoffeeScriptCompiler {
  private final JCoffeeScriptCompiler compiler;

  public CoffeeScriptCompiler() {
    compiler = new JCoffeeScriptCompiler(asList(Option.BARE));
  }

  public synchronized String compile(File file) throws IOException {
    try {
      return compiler.compile(Files.toString(file, Charsets.UTF_8));
    } catch (JCoffeeScriptCompileException e) {
      throw new IOException("Invalid coffeescript", e);
    }
  }
}

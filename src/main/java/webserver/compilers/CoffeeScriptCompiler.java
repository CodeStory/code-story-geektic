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

  public synchronized String compile(File file) {
    try {
      return compiler.compile(Files.toString(file, Charsets.UTF_8));
    } catch (IOException e) {
      throw new IllegalStateException("Unable to read coffeescript file", e);
    } catch (JCoffeeScriptCompileException e) {
      throw new IllegalStateException("Invalid coffeescript file", e);
    }
  }
}

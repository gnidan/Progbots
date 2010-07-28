package controller;

import java.net.URL;
import java.net.URLClassLoader;

public class PluginClassLoader extends URLClassLoader {
  public PluginClassLoader(URL[] urls, ClassLoader parent) {
    super(urls, parent);
  }

  public Class<?> loadClass(String name)
      throws ClassNotFoundException {
    Class loadedClass = findLoadedClass(name);


    if (loadedClass == null) {
      try {
        loadedClass = findClass(name);
      } catch (ClassNotFoundException e) {
        // Swallow exception
        //does not exist locally
      }

      if (loadedClass == null) {
        loadedClass = super.loadClass(name);
      }
    }
    return loadedClass;
  }

}



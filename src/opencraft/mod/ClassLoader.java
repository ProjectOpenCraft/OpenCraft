package opencraft.mod;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassLoader {
	
	public static IMod loadMod(File jar) {
		try {
			URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] {jar.toURI().toURL()});
			@SuppressWarnings("resource")
			JarFile jarFile = new JarFile(jar);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry element = entries.nextElement();
				if (element.getName().endsWith(".class")) {
					@SuppressWarnings("rawtypes")
					Class clazz = classLoader.loadClass(element.getName().replaceAll(".class", "").replaceAll("/", "."));
					Object obj = clazz.newInstance();
					if (obj instanceof IMod) {
						return (IMod) obj;
					}
				}
			}
		} catch(MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}

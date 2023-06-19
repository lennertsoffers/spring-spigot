package be.lennertsoffers.spigotbootstrapper.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class CompoundClassLoader extends ClassLoader {

    private final Collection<ClassLoader> loaders;

    public CompoundClassLoader(ClassLoader... loaders) {
        this.loaders = Arrays.asList(loaders);
    }

    @Override
    public URL getResource(String name) {
        for (ClassLoader loader : this.loaders) {
            if (loader != null) {
                URL resource = loader.getResource(name);
                if (resource != null) {
                    return resource;
                }
            }
        }
        return null;
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        for (ClassLoader loader : this.loaders) {
            if (loader != null) {
                InputStream is = loader.getResourceAsStream(name);
                if (is != null) {
                    return is;
                }
            }
        }
        return null;
    }

    @Override
    public Enumeration<URL> getResources(String name) throws IOException {
        List<URL> urls = new ArrayList<>();
        for (ClassLoader loader : this.loaders) {
            if (loader != null) {
                Enumeration<URL> resources = loader.getResources(name);
                while (resources.hasMoreElements()) {
                    URL resource = resources.nextElement();
                    if (resource != null && !urls.contains(resource)) {
                        urls.add(resource);
                    }
                }
            }
        }
        return Collections.enumeration(urls);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        for (ClassLoader loader : this.loaders) {
            Class<?> loadedClass = loadClassWithClassLoader(name, loader);

            if (loadedClass != null) return loadedClass;
        }
        throw new ClassNotFoundException("Cound not load class with name: " + name);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return this.loadClass(name);
    }

    @Override
    public String toString() {
        return String.format("CompoundClassloader %s", loaders);
    }

    private Class<?> loadClassWithClassLoader(String name, ClassLoader classLoader) {
        if (classLoader == null) return null;

        try {
            return classLoader.loadClass(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

}
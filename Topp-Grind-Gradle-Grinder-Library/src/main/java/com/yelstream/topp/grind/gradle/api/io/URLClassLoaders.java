package com.yelstream.topp.grind.gradle.api.io;

import lombok.experimental.UtilityClass;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Utility addressing instances of {@link URLClassLoader}.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
@UtilityClass
public class URLClassLoaders {  //TO-DO: Consider to remove this!
    /**
     * Creates a class-loader from a list of URLs.
     * @param classLoader Parent classloader.
     * @param urls URLs from which to load classes and resources.
     * @return Class-loader.
     */
    public URLClassLoader createClassLoader(ClassLoader classLoader,
                                            URL... urls) {
        return new URLClassLoader(urls,classLoader);
    }

    /**
     * Creates a class-loader from a list of URLs.
     * @param urls URLs from which to load classes and resources.
     * @return Class-loader.
     */
    public URLClassLoader createClassLoader(URL... urls) {
        return createClassLoader(ClassLoaders.createClassLoader(),urls);
    }
}

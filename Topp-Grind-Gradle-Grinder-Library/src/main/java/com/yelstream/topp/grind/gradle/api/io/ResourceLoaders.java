package com.yelstream.topp.grind.gradle.api.io;

import com.yelstream.topp.util.net.URLs;
import lombok.experimental.UtilityClass;
import org.gradle.api.artifacts.Configuration;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Utility addressing instances of {@link ResourceLoader}.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
@UtilityClass
public class ResourceLoaders {
    /**
     * Creates a resource loader for a Gradle configuration.
     * @param resourceOffset Resource offset.
     * @param configuration Gradle configuration.
     * @return Resource loader.
     */
    public static ResourceLoader createResourceLoader(ResourceOffset resourceOffset,
                                                      Configuration configuration) {
        Set<File> files=configuration.resolve();
        ResourceOffset newResourceOffset=resourceOffset.toBuilder().configuration(configuration).build();
        return createResourceLoader(newResourceOffset,files);
    }

    /**
     * Creates a resource loader for a set of files.
     * @param resourceOffset Resource offset.
     * @param files Files.
     * @return Resource loader.
     */
    public static ResourceLoader createResourceLoader(ResourceOffset resourceOffset,
                                                      Set<File> files) {
        List<ResourceLoader> resourceLoaders=new ArrayList<>();
        ResourceOffset newResourceOffset=resourceOffset.toBuilder().files(files).build();
        for (var file: files) {
            ResourceLoader resourceLoader=createResourceLoader(newResourceOffset,file);
            resourceLoaders.add(resourceLoader);
        }
        return new ChainedResourceLoader(resourceLoaders);
    }

    /**
     * Creates a resource loader for a file.
     * @param resourceOffset Resource offset.
     * @param file File.
     * @return Resource loader.
     */
    public static ResourceLoader createResourceLoader(ResourceOffset resourceOffset,
                                                      File file) {
        URL url=URLs.createURL(file);
        ResourceOffset newResourceOffset=resourceOffset.toBuilder().file(file).build();
        return createResourceLoader(newResourceOffset,url);
    }

    /**
     * Creates a resource loader for a URL.
     * @param resourceOffset Resource offset.
     * @param url URL.
     * @return Resource loader.
     */
    public static ResourceLoader createResourceLoader(ResourceOffset resourceOffset,
                                                      URL url) {
        ResourceOffset newResourceOffset=resourceOffset.toBuilder().url(url).build();
        URLClassLoader classLoader=URLClassLoaders.createClassLoader(url);
        return new URLClassLoaderResourceLoader(newResourceOffset,classLoader);
    }
}

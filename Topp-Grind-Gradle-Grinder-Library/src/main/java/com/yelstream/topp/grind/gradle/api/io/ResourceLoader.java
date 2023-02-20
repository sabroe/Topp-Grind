package com.yelstream.topp.grind.gradle.api.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Loader of static resources.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
public interface ResourceLoader extends AutoCloseable {
    /**
     * Gets the URL of a named resource.
     * @param name Resource name.
     * @return URL of named resource.
     */
    URL getResource(String name);

    /**
     * Gets a stream to the contents of a named resource.
     * @param name Resource name.
     * @return Stream to the contents of the resource.
     */
    InputStream getResourceAsStream(String name);

    /**
     * Gets descriptions of all occouring, named resources.
     * @param name Resource name.
     * @return List of resource location descriptors.
     */
    List<ResourceLocation> getResourceLocations(String name);

    /**
     * Get the description of a named resouirces.
     * @param name Resource name.
     * @return List of resource location descriptors.
     * @throws IllegalStateException Thrown in case more then one occurrence of the named resource exist.
     */
    ResourceLocation getResourceLocation(String name) throws IllegalStateException;

    @Override
    void close() throws IOException;
}

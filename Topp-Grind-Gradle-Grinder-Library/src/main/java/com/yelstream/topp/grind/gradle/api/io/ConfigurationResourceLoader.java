package com.yelstream.topp.grind.gradle.api.io;

import lombok.Getter;
import org.gradle.api.artifacts.Configuration;

/**
 * Loader of resources defined by a Gradle configuration.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
@Getter
public class ConfigurationResourceLoader extends ProxyResourceLoader {
    /**
     * Resource offset for the resources loaded.
     */
    private final ResourceOffset resourceOffset;

    /**
     * Gradle configuration defining the resources which may be loaded.
     */
    private final Configuration configuration;

    /**
     * Constructor.
     * @param configuration Gradle configuration.
     */
    public ConfigurationResourceLoader(Configuration configuration) {
        this.resourceOffset=ResourceOffset.builder().configuration(configuration).build();
        this.configuration=configuration;
        setResourceLoader(ResourceLoaders.createResourceLoader(resourceOffset,configuration));
    }
}

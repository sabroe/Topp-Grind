package com.yelstream.topp.grind.gradle.api.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.net.URL;

/**
 * Descriptor of a resource and its location.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
@Getter
@AllArgsConstructor
public class ResourceLocation {
    /**
     * Resource offset.
     */
    private final ResourceOffset resourceOffset;

    /**
     * Resource loader capable of loading the resource.
     */
    @Getter
    @NonNull  //Lombok!
    private final ResourceLoader resourceLoader;

    /**
     * Name of resource.
     */
    @Getter
    @NonNull  //Lombok!
    private final String resourceName;

    /**
     * Resource URL.
     */
    @Getter
    @NonNull  //Lombok!
    private final URL resourceURL;
}
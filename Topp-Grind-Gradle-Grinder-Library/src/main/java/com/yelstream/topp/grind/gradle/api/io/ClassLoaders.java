package com.yelstream.topp.grind.gradle.api.io;

import lombok.experimental.UtilityClass;

/**
 * Utilities addressing instances of {@link ClassLoader}.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
@UtilityClass
public class ClassLoaders {
    /**
     * Creates a class-loader.
     * @return Class-loader.
     */
    public ClassLoader createClassLoader() {
        return ClassLoader.class.getClassLoader();
    }
}

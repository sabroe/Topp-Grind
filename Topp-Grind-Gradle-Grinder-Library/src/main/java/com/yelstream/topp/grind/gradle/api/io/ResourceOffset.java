package com.yelstream.topp.grind.gradle.api.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.io.File;
import java.net.URL;
import java.util.Collection;

/**
 * Offset serving as a root for locating other resources.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
@Getter
@AllArgsConstructor
@Builder(builderClassName="Builder",toBuilder=true)
public class ResourceOffset {
    /**
     * Gradle source-sets containing the source-set and file-collection.
     * This is e.g. everything related to Java.
     */
    private final SourceSetContainer sourceSetContainer;

    /**
     * Gradle source-set containing the file-collection.
     * This is e.g. everything related to the "main" configuration.
     */
    private final SourceSet sourceSet;

    /**
     * Gradle file-collection defining resources which may be loaded.
     */
    private final FileCollection fileCollection;

    /**
     * Files from which resources may be loaded.
     */
    private final Collection<File> files;

    /**
     * File from which resources may be loaded.
     */
    private final File file;

    /**
     * Gradle configuration defining resources which may be loaded.
     */
    private final Configuration configuration;

    /**
     * URL defining a location from which resource may be loaded.
     */
    private final URL url;
}

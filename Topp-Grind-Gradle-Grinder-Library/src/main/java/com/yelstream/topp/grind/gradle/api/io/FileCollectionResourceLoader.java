package com.yelstream.topp.grind.gradle.api.io;

import lombok.Getter;
import org.gradle.api.file.FileCollection;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.io.File;
import java.util.Set;

/**
 * Loader of resources defined by a Gradle file-collection.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
@Getter
public class FileCollectionResourceLoader extends ProxyResourceLoader {
    /**
     * Resource offset for the resources loaded.
     */
    private final ResourceOffset resourceOffset;

    /**
     * Gradle source-sets containing the source-set and file-collection with resources.
     * This is e.g. everything related to Java.
     */
    private final SourceSetContainer sourceSets;

    /**
     * Gradle source-set containing the file-collection with resources.
     * This is e.g. everything related to the "main" configuration.
     */
    private final SourceSet sourceSet;

    /**
     * Gradle file-collection defining the resources which may be loaded.
     */
    private final FileCollection fileCollection;

    /**
     * Constructor.
     * @param sourceSets Source-sets containing the source-set with resources.
     * @param sourceSet Source-set containing the file-collection with resources.
     * @param fileCollection File-collection with resources.
     */
    public FileCollectionResourceLoader(SourceSetContainer sourceSets,
                                        SourceSet sourceSet,
                                        FileCollection fileCollection) {
        super();
        Set<File> files=fileCollection.getFiles();
        this.resourceOffset=ResourceOffset.builder().sourceSetContainer(sourceSets).sourceSet(sourceSet).fileCollection(fileCollection).files(files).build();
        this.sourceSets=sourceSets;
        this.sourceSet=sourceSet;
        this.fileCollection=fileCollection;
        setResourceLoader(ResourceLoaders.createResourceLoader(resourceOffset,files));
    }
}

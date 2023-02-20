package com.yelstream.topp.grind.gradle.api.io;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Loader of resources defined by a chain-of-responsibility of individual loaders.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
@AllArgsConstructor
public class ChainedResourceLoader implements ResourceLoader {
    /**
     * Resource loaders.
     */
    private final List<ResourceLoader> resourceLoaders;

    @Override
    public URL getResource(String name) {
        return resourceLoaders.stream().map(r->r.getResource(name)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        return resourceLoaders.stream().map(r->r.getResourceAsStream(name)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    @Override
    public List<ResourceLocation> getResourceLocations(String name) {
        List<ResourceLocation> res=new ArrayList<>();
        for (ResourceLoader resourceLoader: resourceLoaders) {
            List<ResourceLocation> resourceLocations=resourceLoader.getResourceLocations(name);
            if (resourceLocations!=null) {
                res.addAll(resourceLocations);
            }
        }
        return res;
    }

    @Override
    public ResourceLocation getResourceLocation(String name) {
        ResourceLocation res;
        List<ResourceLocation> resourceLocations=getResourceLocations(name);
        switch (resourceLocations.size()) {
            case 0: {
                res=null;
                break;
            }
            case 1: {
                res=resourceLocations.get(0);
                break;
            }
            default: {
                throw new IllegalStateException(String.format("Failure to get location of named resource; name is %s, locations are %s!",name,resourceLocations));
            }
        }
        return res;
    }

    @Override
    public void close() throws IOException {
        for (var resourceLoader: resourceLoaders) {
            resourceLoader.close();
        }
    }

    /**
     * Creates a chained resource loader.
     * @param resourceLoaders Resource loaders.
     * @return Chained resource loader.
     */
    public static ChainedResourceLoader of(ResourceLoader... resourceLoaders) {
        return new ChainedResourceLoader(List.of(resourceLoaders));
    }
}

package com.yelstream.topp.grind.gradle.api.io;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Loader of resources defined by a URL-based class-loader.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
@AllArgsConstructor
public class URLClassLoaderResourceLoader implements ResourceLoader {
    /**
     * Resource offset for the resources loaded.
     */
    private final ResourceOffset resourceoffset;

    /**
     * URL-based class-loader.
     */
    private final URLClassLoader classLoader;

    @Override
    public URL getResource(String name) {
        return classLoader.getResource(name);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        return classLoader.getResourceAsStream(name);
    }

    @Override
    public List<ResourceLocation> getResourceLocations(String name) {
        List<ResourceLocation> res=null;
        ResourceLocation location=getResourceLocation(name);
        if (location!=null) {
            res=new ArrayList<>();
            res.add(location);
        }
        return res;
    }

    @Override
    public ResourceLocation getResourceLocation(String name) {
        ResourceLocation res=null;
        URL url=getResource(name);
        if (url!=null) {
            res=new ResourceLocation(resourceoffset,this,name,url);
        }
        return res;
    }

    @Override
    public void close() throws IOException {
        classLoader.close();
    }
}

package com.yelstream.topp.grind.gradle.api.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Proxy for an instance of {@link ResourceLoader}.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2022-04-23
 */
@NoArgsConstructor
@AllArgsConstructor
public class ProxyResourceLoader implements ResourceLoader {
    @Getter
    @Setter
    private ResourceLoader resourceLoader;

    @Override
    public URL getResource(String name) {
        return resourceLoader.getResource(name);
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        return resourceLoader.getResourceAsStream(name);
    }

    @Override
    public List<ResourceLocation> getResourceLocations(String name) {
        return resourceLoader.getResourceLocations(name);
    }

    @Override
    public ResourceLocation getResourceLocation(String name) throws IllegalStateException {
        return resourceLoader.getResourceLocation(name);
    }

    @Override
    public void close() throws IOException {
        if (resourceLoader!=null) {
            resourceLoader.close();
            resourceLoader=null;
        }
    }
}

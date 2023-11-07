package io.github.panda885.fluija.resource;

import io.github.panda885.fluija.render.shader.Shader;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class ResourceLoader {
    private final ClassLoader classLoader;

    public ResourceLoader() {
        this.classLoader = getClass().getClassLoader();
    }

    public ResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public @NotNull String loadResourceAsString(final @NotNull String name) throws ResourceLoadingException {
        try {
            String path = "assets/" + name;
            try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
                if (inputStream == null)
                    throw new ResourceLoadingException("Could not find resource '" + name + "'");
                try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    return reader.lines().collect(Collectors.joining("\n"));
                }
            }
        } catch (IOException exception) {
            throw new ResourceLoadingException("Failed to load resource '" + name + "'", exception);
        }
    }

    public @NotNull Shader loadShader(@NotNull String vertexName, @NotNull String fragmentName) throws ResourceLoadingException {
        String vertexCode = loadResourceAsString("shaders/" + vertexName + ".vsh");
        String fragmentCode = loadResourceAsString("shaders/" + fragmentName + ".fsh");
        return Shader.compile(vertexCode, fragmentCode);
    }

    public @NotNull Shader loadShader(@NotNull String path) throws ResourceLoadingException {
        return loadShader(path, path);
    }

    public ClassLoader getClassLoader() {
        return classLoader;
    }
}

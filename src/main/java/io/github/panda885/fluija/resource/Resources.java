package io.github.panda885.fluija.resource;

import io.github.panda885.fluija.render.shader.Shader;

public class Resources {
    private final Shader colorShader;
    private final Shader texShader;
    private final Shader circleShader;

    public Resources(Shader colorShader, Shader texShader, Shader circleShader) {
        this.colorShader = colorShader;
        this.texShader = texShader;
        this.circleShader = circleShader;
    }

    public static Resources load() throws ResourceLoadingException {
        ResourceLoader resourceLoader = new ResourceLoader();
        return new Resources(
                resourceLoader.loadShader("color"),
                resourceLoader.loadShader("tex"),
                resourceLoader.loadShader("circle")
        );
    }

    public Shader getColorShader() {
        return colorShader;
    }

    public Shader getTexShader() {
        return texShader;
    }

    public Shader getCircleShader() {
        return circleShader;
    }
}

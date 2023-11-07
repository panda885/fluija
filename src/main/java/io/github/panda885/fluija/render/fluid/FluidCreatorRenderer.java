package io.github.panda885.fluija.render.fluid;

import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.fluid.creator.FluidCreator;
import io.github.panda885.fluija.math.Vector3f;
import io.github.panda885.fluija.render.DrawableRenderer;

public class FluidCreatorRenderer {
    private final Fluija fluija;

    public FluidCreatorRenderer(Fluija fluija) {
        this.fluija = fluija;
    }

    public void render(FluidCreator fluidCreator) {
        final DrawableRenderer drawableRenderer = this.fluija.getRenderer().getDrawableRenderer();

        fluidCreator.getParticles().forEach(particle -> {
            drawableRenderer.circle(particle, 3f, new Vector3f(1f));
        });

        final float halfWidth = fluidCreator.getWidth() / 2f;
        final float halfHeight = fluidCreator.getHeight() / 2f;
        drawableRenderer.lineColored(halfWidth, halfHeight, halfWidth, -halfHeight, new Vector3f(.1f));
        drawableRenderer.lineColored(halfWidth, -halfHeight, -halfWidth, -halfHeight, new Vector3f(.1f));
        drawableRenderer.lineColored(-halfWidth, -halfHeight, -halfWidth, halfHeight, new Vector3f(.1f));
        drawableRenderer.lineColored(-halfWidth, halfHeight, halfWidth, halfHeight, new Vector3f(.1f));
    }
}

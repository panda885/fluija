package io.github.panda885.fluija.render.fluid;

import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.math.Vector3f;
import io.github.panda885.fluija.render.DrawableRenderer;
import io.github.panda885.fluija.state.CreationState;

public class FluidCreationRenderer {
    private final Fluija fluija;

    public FluidCreationRenderer(Fluija fluija) {
        this.fluija = fluija;
    }

    public void render(CreationState state) {
        final DrawableRenderer drawableRenderer = this.fluija.getRenderer().getDrawableRenderer();

        state.getParticles().forEach(particle -> drawableRenderer.circle(particle, 3f, new Vector3f(1f)));

        final float halfWidth = state.getWidth() / 2f;
        final float halfHeight = state.getHeight() / 2f;
        drawableRenderer.lineColored(halfWidth,  halfHeight,  halfWidth,  -halfHeight, new Vector3f(.1f));
        drawableRenderer.lineColored(halfWidth,  -halfHeight, -halfWidth, -halfHeight, new Vector3f(.1f));
        drawableRenderer.lineColored(-halfWidth, -halfHeight, -halfWidth, halfHeight,  new Vector3f(.1f));
        drawableRenderer.lineColored(-halfWidth, halfHeight,  halfWidth,  halfHeight,  new Vector3f(.1f));
    }
}

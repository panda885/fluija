package io.github.panda885.fluija.render.fluid;

import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.fluid.Fluid;
import io.github.panda885.fluija.math.Vector2f;
import io.github.panda885.fluija.math.Vector3f;
import io.github.panda885.fluija.render.DrawableRenderer;

public class FluidRenderer {
    private final Fluija fluija;

    public FluidRenderer(Fluija fluija) {
        this.fluija = fluija;
    }

    public void render(Fluid fluid) {
        DrawableRenderer drawableRenderer = fluija.getRenderer().getDrawableRenderer();

        final Vector3f lowSpeed = new Vector3f(.155f, .531f, .964f);
        final Vector3f highSpeed = new Vector3f(.943f, .285f, .285f);

        for (int i = 0; i < fluid.getParticles().size(); i++) {
            Vector2f particle = fluid.getParticles().get(i);
            float speed = fluid.getVelocities().get(i).magnitude();

            drawableRenderer.circle(particle, 3f, Vector3f.lerp(lowSpeed, highSpeed, Math.min(speed / 100f, 1)));
        }
    }
}

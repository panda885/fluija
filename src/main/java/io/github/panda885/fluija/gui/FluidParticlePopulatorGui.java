package io.github.panda885.fluija.gui;

import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.math.Vector2f;
import io.github.panda885.fluija.state.CreationState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluidParticlePopulatorGui implements Gui {
    private final Fluija fluija;

    public FluidParticlePopulatorGui(Fluija fluija) {
        this.fluija = fluija;
    }

    @Override
    public @NotNull String getName() {
        return "Particle Populator";
    }

    @Override
    public @Nullable Vector2f getDefaultPosition() {
        return new Vector2f(20f, 20f);
    }

    @Override
    public void update() {
        CreationState state = this.fluija.getCreationState().orElse(null);
        if (state == null) return;

        Gui.bindCheckbox("random", state.getParticlePopulator()::isRandom, value -> {
            state.getParticlePopulator().setRandom(value);
            state.repopulate();
        });
        Gui.bindInputInt("seed", state.getParticlePopulator()::getSeed, value -> {
            state.getParticlePopulator().setSeed(value);
            state.repopulate();
        });
        Gui.bindInputInt("particles", state.getParticlePopulator().getParticles()::size, state::repopulate);
    }

    @Override
    public boolean shouldDestroy() {
        return fluija.getCreationState().isEmpty();
    }
}

package io.github.panda885.fluija.gui;

import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.state.SimulationState;
import org.jetbrains.annotations.NotNull;

public class FluidGui implements Gui {

    private final Fluija fluija;

    public FluidGui(Fluija fluija) {
        this.fluija = fluija;
    }

    @Override
    public @NotNull String getName() {
        return "Fluid";
    }

    @Override
    public void update() {
        SimulationState state = fluija.getSimulationState().orElse(null);
        if (state == null) return;

        Gui.bindSliderFloat("mass", 0f, 200f, () -> state.getFluid().mass, value -> state.getFluid().mass = value);
        Gui.bindSliderFloat("pressure multiplier", 0f, 1000f, () -> state.getFluid().pressureMultiplier, value -> state.getFluid().pressureMultiplier = value);
        Gui.bindSliderFloat("target density", 0f, 0.1f, () -> state.getFluid().targetDensity, value -> state.getFluid().targetDensity = value);
    }
}

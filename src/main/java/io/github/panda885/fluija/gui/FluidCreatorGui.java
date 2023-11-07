package io.github.panda885.fluija.gui;

import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.state.CreationState;
import io.github.panda885.fluija.state.SimulationState;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static imgui.ImGui.button;

public class FluidCreatorGui implements Gui {
    private final Fluija fluija;

    public FluidCreatorGui(Fluija fluija) {
        this.fluija = fluija;
    }

    @Override
    public @NotNull Optional<Float> getDefaultWidth() {
        return Optional.of(300f);
    }

    @Override
    public @NotNull String getName() {
        return "Fluid Creator";
    }

    public void update() {
        CreationState state = this.fluija.getCreationState().orElse(null);
        if (state == null) return;

        Gui.bindCheckbox("random", state.getFluidCreator()::isRandom, state.getFluidCreator()::setRandom);
        Gui.bindInputInt("seed", state.getFluidCreator()::getSeed, state.getFluidCreator()::setSeed);
        Gui.bindInputInt("width", state.getFluidCreator()::getWidth, state.getFluidCreator()::setWidth);
        Gui.bindInputInt("height", state.getFluidCreator()::getHeight, state.getFluidCreator()::setHeight);
        Gui.bindInputInt("particles", state.getFluidCreator().getParticles()::size, state.getFluidCreator()::setParticleCount);
        Gui.bindInputFloat("smoothing radius", state.getFluidCreator()::getSmoothingRadius, state.getFluidCreator()::setSmoothingRadius);

        if (button("Create")) {
            fluija.newState(new SimulationState(state.getFluidCreator().create()));
        }
    }

    @Override
    public boolean shouldDestroy() {
        return fluija.getCreationState().isEmpty();
    }
}

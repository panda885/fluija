package io.github.panda885.fluija.gui;

import imgui.ImGui;
import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.fluid.settings.FluidSimulationSettings;
import io.github.panda885.fluija.math.Vector2f;
import io.github.panda885.fluija.state.CreationState;
import io.github.panda885.fluija.state.SimulationState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FluidSimulationSettingsGui implements Gui {
    private final Fluija fluija;

    public FluidSimulationSettingsGui(Fluija fluija) {
        this.fluija = fluija;
    }

    @Override
    public @NotNull String getName() {
        return "Simulation Settings";
    }

    @Override
    public float getDefaultWidth() {
        return 200f;
    }

    @Override
    public @Nullable Vector2f getDefaultPosition() {
        return new Vector2f(-220f, 20f);
    }

    @Override
    public void update() {
        FluidSimulationSettings settings = fluija.getSimulationSettings().orElse(null);
        if (settings == null) return;

        Optional<CreationState> creationState = fluija.getCreationState();
        SimulationState simulationState = fluija.getSimulationState().orElse(null);
        if (creationState.isEmpty() && simulationState == null) return;

        if (creationState.isEmpty()) ImGui.beginDisabled();
        Gui.bindInputInt(
                "width",
                () -> creationState.map(CreationState::getWidth).orElseGet(() -> simulationState.getFluid().getWidth()),
                (value) -> creationState.ifPresent(state -> state.setWidth(value))
        );
        Gui.bindInputInt(
                "height",
                () -> creationState.map(CreationState::getHeight).orElseGet(() -> simulationState.getFluid().getHeight()),
                (value) -> creationState.ifPresent(state -> state.setHeight(value))
        );
        Gui.bindInputFloat(
                "smoothing radius",
                () -> creationState.map(CreationState::getSmoothingRadius).orElseGet(() -> simulationState.getFluid().getSmoothingRadius()),
                (value) -> creationState.ifPresent(state -> state.setSmoothingRadius(value))
        );
        if (creationState.isEmpty()) ImGui.endDisabled();

        Gui.bindSliderFloat("mass", 0f, 200f, settings::getMass, settings::setMass);
        Gui.bindSliderFloat("pressure multiplier", 0f, 10000f, settings::getPressureMultiplier, settings::setPressureMultiplier);
        Gui.bindSliderFloat("target density", 0f, 0.5f, settings::getTargetDensity, settings::setTargetDensity);
        Gui.bindSliderFloat("viscosity strength", 0f, 10f, settings::getViscosityStrength, settings::setViscosityStrength);
        Gui.bindInputFloat("gravity", settings::getGravity, settings::setGravity);

        if (creationState.isEmpty()) ImGui.beginDisabled();
        if (ImGui.button("Start")) {
            creationState.ifPresent(state -> state.create(fluija));
        }
        if (creationState.isEmpty()) ImGui.endDisabled();
    }
}

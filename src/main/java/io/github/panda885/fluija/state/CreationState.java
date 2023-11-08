package io.github.panda885.fluija.state;

import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.fluid.Fluid;
import io.github.panda885.fluija.fluid.populator.FluidParticlePopulator;
import io.github.panda885.fluija.fluid.settings.FluidSimulationSettings;
import io.github.panda885.fluija.gui.FluidParticlePopulatorGui;
import io.github.panda885.fluija.gui.FluidSimulationSettingsGui;
import io.github.panda885.fluija.math.Vector2f;

import java.util.List;
import java.util.Random;

public class CreationState implements State {
    private final FluidSimulationSettings simulationSettings = new FluidSimulationSettings();
    private final FluidParticlePopulator particlePopulator = new FluidParticlePopulator(new Random().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE));

    private int width = 1280;
    private int height = 720;
    private float smoothingRadius = 20f;

    public CreationState() {
        this.particlePopulator.populate(width, height, 3249);
    }

    @Override
    public void init(Fluija fluija) {
        fluija.getRenderer().getGuiRenderer().add(new FluidParticlePopulatorGui(fluija));
        fluija.getRenderer().getGuiRenderer().add(new FluidSimulationSettingsGui(fluija));
    }

    @Override
    public void render(Fluija fluija) {
        fluija.getRenderer().getFluidCreatorRenderer().render(this);
    }

    public void create(Fluija fluija) {
        fluija.newState(new SimulationState(new Fluid(width, height, smoothingRadius, getParticles(), simulationSettings)));
    }

    public void repopulate(int size) {
        particlePopulator.populate(width, height, size);
    }

    public void repopulate() {
        repopulate(particlePopulator.getParticles().size());
    }

    public FluidSimulationSettings getSimulationSettings() {
        return simulationSettings;
    }

    public FluidParticlePopulator getParticlePopulator() {
        return particlePopulator;
    }

    public List<Vector2f> getParticles() {
        return particlePopulator.getParticles();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        repopulate();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        repopulate();
    }

    public float getSmoothingRadius() {
        return smoothingRadius;
    }

    public void setSmoothingRadius(float smoothingRadius) {
        this.smoothingRadius = smoothingRadius;
    }
}

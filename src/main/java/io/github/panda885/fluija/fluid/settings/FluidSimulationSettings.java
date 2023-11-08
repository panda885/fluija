package io.github.panda885.fluija.fluid.settings;

public class FluidSimulationSettings {
    private float mass = 15f;
    private float pressureMultiplier = 500f;
    private float targetDensity = 0.001f;


    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getPressureMultiplier() {
        return pressureMultiplier;
    }

    public void setPressureMultiplier(float pressureMultiplier) {
        this.pressureMultiplier = pressureMultiplier;
    }

    public float getTargetDensity() {
        return targetDensity;
    }

    public void setTargetDensity(float targetDensity) {
        this.targetDensity = targetDensity;
    }
}

package io.github.panda885.fluija.fluid.settings;

public class FluidSimulationSettings {
    private float mass = 30f;
    private float pressureMultiplier = 8000f;
    private float targetDensity = 0.285f;
    private float gravity = 400f;
    private float viscosityStrength = 1f;


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

    public float getGravity() {
        return gravity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public float getViscosityStrength() {
        return viscosityStrength;
    }

    public void setViscosityStrength(float viscosityStrength) {
        this.viscosityStrength = viscosityStrength;
    }
}

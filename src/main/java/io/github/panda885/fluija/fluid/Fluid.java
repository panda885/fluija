package io.github.panda885.fluija.fluid;

import io.github.panda885.fluija.fluid.settings.FluidSimulationSettings;
import io.github.panda885.fluija.math.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Fluid {
    // TODO: Use seed provided by FluidCreator
    private final Random random = new Random(185156415874412L);

    private final FluidParticleLookup lookup;

    private final int width;
    private final int height;
    private final float halfWidth;
    private final float halfHeight;

    private final float smoothingRadius;
    private final float smoothingVolume;
    private final float smoothingScale;

    private final List<Vector2f> particles;
    private final List<Vector2f> predictions;
    private final List<Float> densities;
    private final List<Vector2f> velocities;

    private final FluidSimulationSettings settings;

    public Fluid(int width, int height, float smoothingRadius, List<Vector2f> particles, FluidSimulationSettings settings) {
        this.width = width;
        this.height = height;
        this.halfWidth = width / 2f;
        this.halfHeight = height / 2f;
        this.smoothingRadius = smoothingRadius;
        this.smoothingVolume = (float) ((Math.PI * Math.pow(smoothingRadius, 4)) / 6);
        this.smoothingScale = (float) (12 / (Math.PI * Math.pow(smoothingRadius, 4)));
        this.lookup = new FluidParticleLookup(smoothingRadius, particles.size(), width, height);
        this.particles = particles;
        this.predictions = new ArrayList<>(particles);
        this.densities = new ArrayList<>(particles.size());
        this.velocities = new ArrayList<>(particles.size());
        this.settings = settings;

        for (int i = 0; i < particles.size(); i++) {
            this.densities.add(0f);
            this.velocities.add(new Vector2f());
        }

        this.lookup.recalculate(this.predictions);
        calculateDensities();
    }

    public float smoothingFunction(float distance) {
        if (distance >= smoothingRadius) return 0;
        float value = smoothingRadius - distance;
        return value * value / smoothingVolume;
    }

    public float smoothingFunctionSlope(float distance) {
        if (distance >= smoothingRadius) return 0;
        return (distance - smoothingRadius) * smoothingScale;
    }

    public float calculateDensity(Vector2f position) {
        float density = 0.0f;
        for (int i : lookup.getNearby(position).toList()) {
            Vector2f particle = predictions.get(i);
            float distance = particle.distance(position);
            float influence = smoothingFunction(distance);
            density += influence * settings.getMass();
        }
        return density;
    }

    public void calculateDensities() {
        IntStream.range(0, particles.size()).parallel().forEach(i -> {
            Vector2f particle = predictions.get(i);
            densities.set(i, calculateDensity(particle));
        });
    }

    public float calculateSharedPressure(float densityA, float densityB) {
        return (convertDensityToPressure(densityA) + convertDensityToPressure(densityB)) / 2;
    }

    public Vector2f calculatePressureForce(int particleIndex) {
        Vector2f pressure = new Vector2f();
        Vector2f position = predictions.get(particleIndex);

        for (int i : lookup.getNearby(position).toList()) {
            if (i == particleIndex)
                continue;

            Vector2f particle = predictions.get(i);
            float density = densities.get(i);
            if (density == 0f)
                continue;
            float distance = particle.distance(position);

            Vector2f direction = distance == 0f ? Vector2f.random(random) : particle.minus(position).divide(distance);
            float slope = smoothingFunctionSlope(distance);
            float sharedPressure = calculateSharedPressure(density, densities.get(particleIndex));
            pressure = pressure.add(direction.multiply(sharedPressure).multiply(slope).multiply(settings.getMass()).divide(density));

        }
        return pressure;
    }

    public float convertDensityToPressure(float density) {
        return (density - settings.getTargetDensity()) * settings.getPressureMultiplier();
    }

    public void simulate(float deltaTime) {
        IntStream.range(0, particles.size()).parallel().forEach(i -> {
            this.predictions.set(i, this.particles.get(i).add(this.velocities.get(i).multiply(deltaTime)));
        });

        this.lookup.recalculate(this.predictions);
        calculateDensities();

        IntStream.range(0, particles.size()).parallel().forEach(i -> {
            Vector2f velocity = velocities.get(i);
            float density = densities.get(i);

            if (density != 0f) {
                Vector2f pressureForce = calculatePressureForce(i);
                Vector2f pressureAcceleration = pressureForce.divide(density);
                velocity = velocity.add(pressureAcceleration);
            }

//            REPEL:
            Vector2f particle = particles.get(i);
            if (Math.abs(particle.x) >= halfWidth) {
                velocity = new Vector2f(-Math.signum(particle.x) * 10f, velocity.y);
            } else if (Math.abs(particle.y) >= halfHeight) {
                velocity = new Vector2f(velocity.x, -Math.signum(particle.y) * 10f);
            }

//            BOUNCE:
//            if (Math.abs(particle.x) >= halfWidth) {
//                velocity = new Vector2f(-velocity.x, velocity.y);
//            } else if (Math.abs(particle.y) >= halfHeight) {
//                velocity = new Vector2f(velocity.x, -velocity.y);
//            }

//            STOP:
//            if (Math.abs(particle.x) >= halfWidth) {
//                velocity = new Vector2f(0f, velocity.y);
//            } else if (Math.abs(particle.y) >= halfHeight) {
//                velocity = new Vector2f(velocity.x, 0f);
//            }

            velocities.set(i, velocity);
            particles.set(i, particle.add(velocity.multiply(deltaTime)).clamp(new Vector2f(-halfWidth, -halfHeight), new Vector2f(halfWidth, halfHeight)));
        });
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getSmoothingRadius() {
        return smoothingRadius;
    }

    public List<Vector2f> getParticles() {
        return particles;
    }

    public List<Vector2f> getVelocities() {
        return velocities;
    }

    public FluidSimulationSettings getSettings() {
        return settings;
    }
}

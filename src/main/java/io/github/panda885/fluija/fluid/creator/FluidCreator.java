package io.github.panda885.fluija.fluid.creator;

import io.github.panda885.fluija.fluid.Fluid;
import io.github.panda885.fluija.math.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FluidCreator {
    private boolean random = false;
    private int seed;
    private int width;
    private int height;
    private float smoothingRadius = 20f;
    private List<Vector2f> particles;

    public FluidCreator(int width, int height, int seed) {
        this(width, height, 0, seed);
    }

    public FluidCreator(int width, int height, int particleCount, int seed) {
        this.width = width;
        this.height = height;
        this.seed = seed;

        setParticleCount(particleCount);
    }

    public Fluid create() {
        return new Fluid(width, height, particles, smoothingRadius);
    }

    public void setParticleCount(int particleCount) {
        this.particles = new ArrayList<>(particleCount);

        if (random) {
            Random random = new Random(seed);

            final float halfWidth = width / 2f;
            final float halfHeight = height / 2f;

            for (int i = 0; i < particleCount; i++) {
                float x = random.nextFloat(-halfWidth, halfWidth);
                float y = random.nextFloat(-halfHeight, halfHeight);
                this.particles.add(new Vector2f(x, y));
            }
        } else {
            int distance = 10;

            int size = (int) Math.ceil(Math.sqrt(particleCount));
            float half = size / 2f;
            for (int i = 0; i < particleCount; i++) {
                int x = (i % size);
                int y = (i / size);
                this.particles.add(new Vector2f((x - half) * distance, (y - half) * distance));
            }
        }
    }

    public List<Vector2f> getParticles() {
        return particles;
    }

    public void setSeed(int seed) {
        this.seed = seed;
        setParticleCount(this.particles.size());
    }

    public void setWidth(int width) {
        this.width = width;
        setParticleCount(this.particles.size());
    }

    public void setHeight(int height) {
        this.height = height;
        setParticleCount(this.particles.size());
    }

    public void setRandom(boolean random) {
        this.random = random;
        setParticleCount(this.particles.size());
    }

    public void setSmoothingRadius(float smoothingRadius) {
        this.smoothingRadius = smoothingRadius;
    }

    public int getSeed() {
        return seed;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isRandom() {
        return random;
    }

    public float getSmoothingRadius() {
        return smoothingRadius;
    }
}

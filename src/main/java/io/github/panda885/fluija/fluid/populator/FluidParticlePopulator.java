package io.github.panda885.fluija.fluid.populator;

import io.github.panda885.fluija.math.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FluidParticlePopulator {
    private boolean random = false;
    private int seed;
    private List<Vector2f> particles = new ArrayList<>();

    public FluidParticlePopulator(int seed) {
        this.seed = seed;
    }

    public void populate(int width, int height, int count) {
        this.particles = new ArrayList<>(count);

        if (random) {
            Random random = new Random(seed);
            for (int i = 0; i < count; i++) {
                float x = random.nextFloat(-width / 2f, width / 2f);
                float y = random.nextFloat(-height / 2f, height / 2f);
                this.particles.add(new Vector2f(x, y));
            }
        } else {
            int distance = 10;

            int size = (int) Math.ceil(Math.sqrt(count));
            float half = size / 2f;
            for (int i = 0; i < count; i++) {
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
    }

    public void setRandom(boolean random) {
        this.random = random;
    }

    public int getSeed() {
        return seed;
    }

    public boolean isRandom() {
        return random;
    }
}

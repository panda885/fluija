package io.github.panda885.fluija.state;

import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.fluid.creator.FluidCreator;

import java.util.Random;

public class CreationState implements State {
    private final FluidCreator fluidCreator = new FluidCreator(1280, 720, 3249, new Random().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE));

    @Override
    public void render(Fluija fluija) {
        fluija.getRenderer().getFluidCreatorRenderer().render(fluidCreator);
    }

    public FluidCreator getFluidCreator() {
        return fluidCreator;
    }
}

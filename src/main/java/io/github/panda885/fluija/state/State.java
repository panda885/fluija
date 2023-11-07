package io.github.panda885.fluija.state;

import io.github.panda885.fluija.Fluija;

public interface State {

    default void init(Fluija fluija) {}
    default void render(Fluija fluija) {}

    default void update(Fluija fluija, float deltaTime) {}
}

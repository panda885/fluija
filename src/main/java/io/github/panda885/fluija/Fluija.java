package io.github.panda885.fluija;

import io.github.panda885.fluija.fluid.settings.FluidSimulationSettings;
import io.github.panda885.fluija.render.Renderer;
import io.github.panda885.fluija.render.Window;
import io.github.panda885.fluija.resource.ResourceLoadingException;
import io.github.panda885.fluija.resource.Resources;
import io.github.panda885.fluija.state.CreationState;
import io.github.panda885.fluija.state.SimulationState;
import io.github.panda885.fluija.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import java.util.Optional;

public class Fluija {

    private Resources resources;
    private Window window;
    private Renderer renderer;

    private @NotNull State state = new CreationState();
    private @Nullable State newState = state;

    public void run() {
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW");

        this.window = Window.create(this, 1280, 720, "Fluija");
        this.window.makeCurrent();

        this.renderer = new Renderer(this);
        this.renderer.init();

        try {
            this.resources = Resources.load();
        } catch (ResourceLoadingException e) {
            e.printStackTrace();
            return;
        }

        this.window.show();
        this.window.loop();
    }

    public void render() {
        this.renderer.render();
    }

    public void update(float deltaTime) {
        // Switch to a new state if requested
        if (newState != null) {
            state = newState;
            newState = null;

            state.init(this);
        }

        this.state.update(this, deltaTime);
    }

    public void newState(@Nullable State state) {
        this.newState = state;
    }

    public @NotNull State getState() {
        return state;
    }

    public @NotNull Optional<CreationState> getCreationState() {
        if (state instanceof CreationState creationState) return Optional.of(creationState);
        return Optional.empty();
    }

    public @NotNull Optional<SimulationState> getSimulationState() {
        if (state instanceof SimulationState simulationState) return Optional.of(simulationState);
        return Optional.empty();
    }

    public @NotNull Optional<FluidSimulationSettings> getSimulationSettings() {
        return getCreationState()
                .map(CreationState::getSimulationSettings)
                .or(() -> getSimulationState()
                        .map(state -> state.getFluid().getSettings()));
    }

    public Resources getResources() {
        return resources;
    }

    public Window getWindow() {
        return window;
    }

    public Renderer getRenderer() {
        return renderer;
    }
}

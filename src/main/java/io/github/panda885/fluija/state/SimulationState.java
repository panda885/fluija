package io.github.panda885.fluija.state;

import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.fluid.Fluid;
import io.github.panda885.fluija.math.Vector2f;
import io.github.panda885.fluija.math.Vector2i;
import io.github.panda885.fluija.math.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class SimulationState implements State {
    public static final float DRAG_DISTANCE = 100f;

    private final Fluid fluid;
    private Vector2f dragPosition = null;
    private Vector2f dragPreviousPosition = new Vector2f();

    public SimulationState(Fluid fluid) {
        this.fluid = fluid;
    }

    @Override
    public void update(Fluija fluija, float deltaTime) {
        this.fluid.simulate(deltaTime);

        long windowHandle = fluija.getWindow().getHandle();

        boolean isDragged = GLFW.glfwGetMouseButton(windowHandle, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS;
        if (isDragged && dragPosition == null) {
            this.dragPosition = getCursorPosition(fluija);
            this.dragPreviousPosition = this.dragPosition;
        } else if (!isDragged && dragPosition != null) {
            dragPosition = null;
        } else if (dragPosition != null) {
            this.dragPosition = getCursorPosition(fluija);

            Vector2f diff = this.dragPosition.minus(this.dragPreviousPosition);
            List<Vector2f> particles = this.fluid.getParticles();
            for (int index = 0; index < particles.size(); index++) {
                if (dragPosition.distance(particles.get(index)) <= DRAG_DISTANCE) {
                    this.fluid.getVelocities().set(index, this.fluid.getVelocities().get(index).add(diff));
                }
            }

            this.dragPreviousPosition = dragPosition;
        }
    }

    public Vector2f getCursorPosition(Fluija fluija) {
        Vector2i windowSize = fluija.getWindow().getSize();
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            DoubleBuffer xBuffer = memoryStack.mallocDouble(1);
            DoubleBuffer yBuffer = memoryStack.mallocDouble(1);

            glfwGetCursorPos(fluija.getWindow().getHandle(), xBuffer, yBuffer);

            int x = (int) (xBuffer.get() - windowSize.x / 2d);
            int y = (int) (windowSize.y / 2d - yBuffer.get());
            return new Vector2f(x, y);
        }
    }

    @Override
    public void render(Fluija fluija) {
        if (dragPosition != null) {
            fluija.getRenderer().getDrawableRenderer().circle(dragPosition, DRAG_DISTANCE, new Vector3f(.2f));
        }

        fluija.getRenderer().getFluidRenderer().render(fluid);
    }

    public Fluid getFluid() {
        return fluid;
    }
}

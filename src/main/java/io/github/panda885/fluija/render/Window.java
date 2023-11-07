package io.github.panda885.fluija.render;

import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.math.Matrix4f;
import io.github.panda885.fluija.math.Vector2i;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;
import java.time.Duration;
import java.time.Instant;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final Fluija fluija;

    private long handle;

    private Window(Fluija fluija, long handle) {
        this.fluija = fluija;
        this.handle = handle;
    }

    public static Window create(Fluija fluija, int width, int height, String title) {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);

        long handle = glfwCreateWindow(width, height, title, NULL, NULL);
        if (handle == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        return new Window(fluija, handle);
    }

    public Matrix4f createProjectionMatrix() {
        Matrix4f matrix = new Matrix4f();

        int width;
        int height;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer bWidth = stack.mallocInt(1);
            IntBuffer bHeight = stack.mallocInt(1);

            glfwGetWindowSize(handle, bWidth, bHeight);

            width = bWidth.get(0);
            height = bHeight.get(0);
        }

        matrix.set(0, 0, 2.0f / width);
        matrix.set(1, 1, 2.0f / height);
        matrix.set(2, 2, 1.0f);
        matrix.set(3, 3, 1.0f);
        return matrix;
    }

    public void makeCurrent() {
        glfwMakeContextCurrent(handle);
        glfwSwapInterval(1);
    }

    public void show() {
        glfwShowWindow(handle);
    }

    public void loop() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        Instant previous = Instant.now();
        Instant now;

        while (!glfwWindowShouldClose(handle)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            now = Instant.now();

            fluija.update(Duration.between(previous, now).toNanos() / 1000000000f);
            fluija.render();

            glfwSwapBuffers(handle);
            glfwPollEvents();

            previous = now;
        }
    }

    public void close() {
        glfwSetWindowShouldClose(handle, true);
    }

    public Vector2i getSize() {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            IntBuffer widthBuffer = memoryStack.mallocInt(1);
            IntBuffer heightBuffer = memoryStack.mallocInt(1);
            glfwGetWindowSize(handle, widthBuffer, heightBuffer);
            return new Vector2i(widthBuffer.get(), heightBuffer.get());
        }
    }

    public long getHandle() {
        return handle;
    }
}

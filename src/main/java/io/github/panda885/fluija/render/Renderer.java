package io.github.panda885.fluija.render;

import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.gui.FluidCreatorGui;
import io.github.panda885.fluija.math.Matrix4f;
import io.github.panda885.fluija.render.gui.GuiRenderer;
import io.github.panda885.fluija.render.fluid.FluidCreatorRenderer;
import io.github.panda885.fluija.render.fluid.FluidRenderer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL15;

public class Renderer {
    private final Fluija fluija;
    private final DrawableRenderer drawableRenderer;
    private final GuiRenderer guiRenderer;

    private final FluidRenderer fluidRenderer;
    private final FluidCreatorRenderer fluidCreatorRenderer;

    private Matrix4f projectionMatrix;
    private int vertexBufferObject;

    public Renderer(Fluija fluija) {
        this.fluija = fluija;
        this.drawableRenderer = new DrawableRenderer(fluija);
        this.guiRenderer = new GuiRenderer(fluija);
        this.fluidRenderer = new FluidRenderer(fluija);
        this.fluidCreatorRenderer = new FluidCreatorRenderer(fluija);
    }

    public void init() {
        GL.createCapabilities();

        GL15.glBlendFunc(GL15.GL_SRC_ALPHA, GL15.GL_ONE_MINUS_SRC_ALPHA);
        GL15.glEnable(GL15.GL_BLEND);

        this.vertexBufferObject = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vertexBufferObject);

        this.guiRenderer.init();
        this.guiRenderer.add(new FluidCreatorGui(this.fluija));
    }

    public void render() {
        this.projectionMatrix = this.fluija.getWindow().createProjectionMatrix();

        this.fluija.getState().render(this.fluija);
        this.guiRenderer.render();
    }

    public int getVertexBufferObject() {
        return vertexBufferObject;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public DrawableRenderer getDrawableRenderer() {
        return drawableRenderer;
    }

    public GuiRenderer getGuiRenderer() {
        return guiRenderer;
    }

    public FluidRenderer getFluidRenderer() {
        return fluidRenderer;
    }

    public FluidCreatorRenderer getFluidCreatorRenderer() {
        return fluidCreatorRenderer;
    }
}

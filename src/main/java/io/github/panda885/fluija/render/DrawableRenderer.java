package io.github.panda885.fluija.render;

import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.math.Vector2f;
import io.github.panda885.fluija.math.Vector3f;
import io.github.panda885.fluija.render.buffer.BufferBuilder;
import io.github.panda885.fluija.render.shader.Shader;
import org.lwjgl.opengl.GL11;

public class DrawableRenderer {
    private final Fluija fluija;

    public DrawableRenderer(Fluija fluija) {
        this.fluija = fluija;
    }

    private void prepareShader(Shader shader) {
        shader.use();
        shader.setUniformMatrix4f("projection", this.fluija.getRenderer().getProjectionMatrix());
    }

    public void drawRect(float x1, float y1, float x2, float y2) {
        BufferBuilder buffer = new BufferBuilder();
        buffer.vertex(x2, y2, 0.0f,  1.0f, 1.0f);
        buffer.vertex(x2, y1, 0.0f,  1.0f, 0.0f);
        buffer.vertex(x1, y1, 0.0f,  0.0f, 0.0f);
        buffer.vertex(x1, y2, 0.0f,  0.0f, 1.0f);
        buffer.draw(fluija.getRenderer().getVertexBufferObject(), GL11.GL_QUADS);
    }

    public void drawLine(float x1, float y1, float x2, float y2) {
        BufferBuilder buffer = new BufferBuilder();
        buffer.vertex(x1, y1, 0.0f,  0.0f, 0.0f);
        buffer.vertex(x2, y2, 0.0f,  1.0f, 1.0f);
        buffer.draw(fluija.getRenderer().getVertexBufferObject(), GL11.GL_LINES);
    }

    public void line(float x1, float y1, float x2, float y2, Shader shader) {
        prepareShader(shader);
        drawLine(x1, y1, x2, y2);
    }

    public void line(Vector2f first, Vector2f second, Shader shader) {
        line(first.x, first.y, second.x, second.y, shader);
    }

    public void lineColored(float x1, float y1, float x2, float y2, Vector3f color) {
        Shader shader = fluija.getResources().getColorShader();
        prepareShader(shader);
        shader.setUniform3f("color", color);
        drawLine(x1, y1, x2, y2);
    }

    public void rect(float x1, float y1, float x2, float y2, Shader shader) {
        prepareShader(shader);
        drawRect(x1, y1, x2, y2);
    }

    public void rect(Vector2f first, Vector2f second, Shader shader) {
        rect(first.x, first.y, second.x, second.y, shader);
    }

    public void rectColored(float x1, float y1, float x2, float y2, Vector3f color) {
        Shader shader = fluija.getResources().getColorShader();
        shader.use();
        shader.setUniform3f("color", color);

        rect(x1, y1, x2, y2, shader);
    }

    public void rectColored(Vector2f first, Vector2f second, Vector3f color) {
        rectColored(first.x, first.y, second.x, second.y, color);
    }

    public void rectTextured(float x1, float y1, float x2, float y2, int texture) {
        Shader shader = fluija.getResources().getTexShader();
        prepareShader(shader);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        drawRect(x1, y1, x2, y2);
    }

    public void rectTextured(Vector2f first, Vector2f second, int texture) {
        rectTextured(first.x, first.y, second.x, second.y, texture);
    }

    public void circle(Vector2f position, float radius, Vector3f color) {
        circle(position.x, position.y, radius, color);
    }

    public void circle(float x, float y, float radius, Vector3f color) {
        Shader shader = fluija.getResources().getCircleShader();
        shader.use();
        shader.setUniform3f("color", color);
        shader.setUniform1f("radius", radius);

        rect(x - radius, y - radius, x + radius, y + radius, shader);
    }
}

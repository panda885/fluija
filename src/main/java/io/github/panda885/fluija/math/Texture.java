package io.github.panda885.fluija.math;

import org.lwjgl.opengl.GL11;

import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Texture {
    private final float[] data;
    private final int width;
    private final int height;

    public Texture(int width, int height) {
        this.width = width;
        this.height = height;
        this.data = new float[width * height * 3];
    }

    private static Stream<Vector2i> positionStream(int width, int height) {
        return IntStream.range(0, width)
                .mapToObj(x -> IntStream.range(0, height)
                        .mapToObj(y -> new Vector2i(x, y)))
                .flatMap(it -> it);
    }

    public static Texture parallel(int width, int height, BiFunction<Integer, Integer, Vector3f> builder) {
        Texture texture = new Texture(width, height);
        positionStream(width, height)
                .parallel()
                .forEach(it -> texture.set(it.x, it.y, builder.apply(it.x, it.y)));
        return texture;
    }

    public static Texture build(int width, int height, BiFunction<Integer, Integer, Vector3f> builder) {
        Texture texture = new Texture(width, height);
        positionStream(width, height)
                .forEach(it -> texture.set(it.x, it.y, builder.apply(it.x, it.y)));
        return texture;
    }

    public void set(int x, int y, Vector3f color) {
        set(x, y, color.x, color.y, color.z);
    }

    public void set(int x, int y, float r, float g, float b) {
        if (x >= width || y >= width) {
            throw new IllegalArgumentException("position (" + x + ", " + y + ") is out of bounds");
        }

        int index = (x + y * width) * 3;
        this.data[index++] = r;
        this.data[index++] = g;
        this.data[index]   = b;
    }

    public Vector3f get(int x, int y) {
        if (x >= width || y >= width) {
            throw new IllegalArgumentException("position (" + x + ", " + y + ") is out of bounds");
        }

        int index = (x + y * width) * 3;
        return new Vector3f(this.data[index++], this.data[index++], this.data[index]);
    }

    public void generate(int target) {
        GL11.glTexImage2D(target, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB, GL11.GL_FLOAT, this.data);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

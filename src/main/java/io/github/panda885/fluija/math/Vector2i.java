package io.github.panda885.fluija.math;

public class Vector2i {
    public final int x;
    public final int y;

    public Vector2i() {
        this(0, 0);
    }

    public Vector2i(int g) {
        this(g, g);
    }

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vector2i{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

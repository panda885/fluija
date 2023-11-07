package io.github.panda885.fluija.math;

public class Vector4f {
    public final float x;
    public final float y;
    public final float z;
    public final float w;

    public Vector4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Vector4f(Vector2f v) {
        this(v.x, v.y);
    }

    public Vector4f(float x, float y) {
        this(x, y, 0.0f, 0.0f);
    }

    public Vector4f(Vector3f v) {
        this(v, 0.0f);
    }

    public Vector4f(Vector3f v, float w) {
        this(v.x, v.y, v.z, w);
    }

    public Vector4f(float x, float y, float z) {
        this(x, y, z, 0.0f);
    }

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    @Override
    public String toString() {
        return "Vector4f{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
}

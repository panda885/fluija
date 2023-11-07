package io.github.panda885.fluija.math;

public class Vector3f {
    public final float x;
    public final float y;
    public final float z;

    public Vector3f() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3f(float g) {
        this(g, g, g);
    }

    public Vector3f(Vector2f v) {
        this(v, 0.0f);
    }

    public Vector3f(Vector2f v, float z) {
        this(v.x, v.y, z);
    }

    public Vector3f(float x, float y) {
        this(x, y, 0.0f);
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3f lerp(Vector3f a, Vector3f b, float alpha) {
        return new Vector3f(
                MathUtils.lerp(a.x, b.x, alpha),
                MathUtils.lerp(a.y, b.y, alpha),
                MathUtils.lerp(a.z, b.z, alpha)
        );
    }

    public Vector3f minus(Vector3f other) {
        return new Vector3f(x - other.x, y - other.y, z - other.z);
    }

    public Vector3f multiply(float v){
        return new Vector3f(x * v, y * v, z * v);
    }

    @Override
    public String toString() {
        return "Vector3f{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}

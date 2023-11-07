package io.github.panda885.fluija.math;

public class Matrix4f {
    public final float[] data;

    public Matrix4f() {
        this(new float[16]);
    }

    public Matrix4f(Vector4f a, Vector4f b, Vector4f c, Vector4f d) {
        this(new float[] {
                a.x, a.y, a.z, a.w,
                b.x, b.y, b.z, b.w,
                c.x, c.y, c.z, c.w,
                d.x, d.y, d.z, d.w
        });
    }

    public Matrix4f(float[] data) {
        if (data.length != 16) {
            throw new IllegalArgumentException("Length of matrix data must be 16");
        }

        this.data = data;
    }

    public void set(int x, int y, float value) {
        data[x + y * 4] = value;
    }

    public float get(int x, int y) {
        return data[x + y * 4];
    }
}

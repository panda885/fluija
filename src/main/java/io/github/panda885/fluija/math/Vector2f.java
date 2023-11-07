package io.github.panda885.fluija.math;

import java.util.Objects;
import java.util.Random;

public class Vector2f {
    public final float x;
    public final float y;

    public Vector2f() {
        this(0.0f, 0.0f);
    }

    public Vector2f(float g) {
        this(g, g);
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2f random() {
        return random(new Random());
    }

    public static Vector2f random(Random random) {
        return new Vector2f(random.nextFloat(-1f, 1f), random.nextFloat(-1f, 1f)).normalize();
    }

    public Vector2f add(float v) {
        return new Vector2f(x + v, y + v);
    }

    public Vector2f add(Vector2f other) {
        return new Vector2f(x + other.x, y + other.y);
    }

    public Vector2f minus(Vector2f other) {
        return new Vector2f(x - other.x, y - other.y);
    }

    public Vector2f multiply(float v) {
        return new Vector2f(x * v, y * v);
    }

    public Vector2f divide(float v) {
        return new Vector2f(x / v, y / v);
    }

    public Vector2f clamp(Vector2f min, Vector2f max) {
        return new Vector2f(Math.min(max.x, Math.max(min.x, x)), Math.min(max.y, Math.max(min.y, y)));
    }

    public Vector2f normalize() {
        float magnitude = magnitude();
        return new Vector2f(x / magnitude, y / magnitude);
    }

    public float magnitude() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public float distance(Vector2f other) {
        return minus(other).magnitude();
    }

    @Override
    public String toString() {
        return "Vector2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

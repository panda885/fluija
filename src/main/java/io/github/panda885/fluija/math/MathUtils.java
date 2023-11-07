package io.github.panda885.fluija.math;

public class MathUtils {

    public static float lerp(float a, float b, float alpha) {
        return a * (1f - alpha) + (b * alpha);
    }
}

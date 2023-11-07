package io.github.panda885.fluija.util;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ArrayUtils {

    public static float[] toPrimitive(@NotNull List<Float> list) {
        float[] primitive = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            primitive[i] = list.get(i);
        }
        return primitive;
    }
}

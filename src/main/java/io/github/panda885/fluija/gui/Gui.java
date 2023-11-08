package io.github.panda885.fluija.gui;

import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import imgui.type.ImInt;
import io.github.panda885.fluija.math.Vector2f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface Gui {

    @NotNull String getName();

    default @Nullable Vector2f getDefaultPosition() {
        return null;
    }

    default float getDefaultHeight() {
        return 0f;
    }

    default float getDefaultWidth() {
        return 0f;
    }

    void update();

    default boolean shouldDestroy() {
        return false;
    }

    static void bindCheckbox(@NotNull String label, @NotNull Supplier<Boolean> supplier, @NotNull Consumer<Boolean> consumer) {
        boolean original = supplier.get();
        ImBoolean value = new ImBoolean(original);
        ImGui.checkbox(label, value);
        if (value.get() != original) {
            consumer.accept(value.get());
        }
    }

    static void bindInputInt(@NotNull String label, @NotNull Supplier<Integer> supplier, @NotNull Consumer<Integer> consumer) {
        int original = supplier.get();
        ImInt value = new ImInt(original);
        ImGui.inputInt(label, value);
        if (value.get() != original) {
            consumer.accept(value.get());
        }
    }

    static void bindInputFloat(@NotNull String label, @NotNull Supplier<Float> supplier, @NotNull Consumer<Float> consumer) {
        float original = supplier.get();
        ImFloat value = new ImFloat(original);
        ImGui.inputFloat(label, value);
        if (value.get() != original) {
            consumer.accept(value.get());
        }
    }

    static void bindSliderFloat(@NotNull String label, float min, float max, @NotNull Supplier<Float> supplier, @NotNull Consumer<Float> consumer) {
        float original = supplier.get();
        float[] value = new float[] { original };
        ImGui.sliderFloat(label, value, min, max);
        if (value[0] != original) {
            consumer.accept(value[0]);
        }
    }
}

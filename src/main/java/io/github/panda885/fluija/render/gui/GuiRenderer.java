package io.github.panda885.fluija.render.gui;

import imgui.ImGui;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import io.github.panda885.fluija.Fluija;
import io.github.panda885.fluija.gui.Gui;
import io.github.panda885.fluija.math.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class GuiRenderer {
    private final Fluija fluija;

    private final List<Gui> guis = new ArrayList<>();

    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private boolean firstRender = true;

    public GuiRenderer(Fluija fluija) {
        this.fluija = fluija;
    }

    public void init() {
        // Create new imgui context
        ImGui.createContext();

        // Don't save imgui data
        ImGui.getIO().setIniFilename(null);

        // Init imgui impl for glfw and gl3
        imGuiGlfw.init(this.fluija.getWindow().getHandle(), true);
        imGuiGl3.init();
    }

    public void render() {
        imGuiGlfw.newFrame();
        ImGui.newFrame();

        final Vector2i windowSize = fluija.getWindow().getSize();

        for (int i = 0; i < guis.size(); i++) {
            Gui gui = guis.get(i);

            // Destroy the gui if requested
            if (gui.shouldDestroy()) {
                this.guis.remove(i--);
                continue;
            }

            // Start drawing the gui
            ImGui.begin(gui.getName());

            // Change the size of the window on first render
            if (firstRender) {
                ImGui.setWindowSize(gui.getDefaultWidth(), gui.getDefaultHeight());
                if (gui.getDefaultPosition() != null) {
                    float x = gui.getDefaultPosition().x;
                    float y = gui.getDefaultPosition().y;
                    if (x < 0f) x = windowSize.x + x;
                    if (y < 0f) y = windowSize.y + y;
                    ImGui.setWindowPos(x, y);
                }
            }

            // Update the gui
            gui.update();

            ImGui.end();
        }

        if (firstRender) {
            firstRender = false;
        }

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

    public void add(Gui gui) {
        this.guis.add(gui);
    }
}

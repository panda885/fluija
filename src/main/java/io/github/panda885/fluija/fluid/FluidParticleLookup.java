package io.github.panda885.fluija.fluid;

import io.github.panda885.fluija.math.Vector2f;
import io.github.panda885.fluija.math.Vector2i;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class FluidParticleLookup {
    private List<List<Integer>> lookup = new ArrayList<>();
    private final float fluidHalfWidth;
    private final float fluidHalfHeight;
    private final int width;
    private final int height;
    private final float size;

    public FluidParticleLookup(float size, int fluidWidth, int fluidHeight) {
        this.size = size;
        this.width = (int) Math.ceil(fluidWidth / size);
        this.height = (int) Math.ceil(fluidHeight / size);
        this.fluidHalfWidth = fluidWidth / 2f;
        this.fluidHalfHeight = fluidHeight / 2f;
    }

    public void recalculate(List<Vector2f> positions) {
        this.lookup = new ArrayList<>();
        for (int i = 0; i < width * height; i++) {
            this.lookup.add(new ArrayList<>());
        }
        for (int i = 0; i < positions.size(); i++) {
            Vector2f position = positions.get(i);
            int cell = getIndexFor(position);
            if (cell < 0 || cell >= this.lookup.size())
                continue;
            lookup.get(cell).add(i);
        }
    }

    public Vector2i getCellFor(Vector2f position) {
        int x = (int) ((this.fluidHalfWidth + position.x) / size);
        int y = (int) ((this.fluidHalfHeight + position.y) / size);
        if (x == width) x -= 1;
        if (y == height) y -= 1;
        return new Vector2i(x, y);
    }

    public int getIndexFor(Vector2f position) {
        Vector2i cell = getCellFor(position);
        return cell.x + cell.y * width;
    }

    public List<Integer> getIndexes(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return List.of();
        }

        int i = x + y * width;
        return lookup.get(i);
    }

    public Stream<Integer> getNearby(Vector2f position) {
        Vector2i cell = getCellFor(position);
        int x = cell.x;
        int y = cell.y;

        return Stream.of(
                getIndexes(x, y),
                getIndexes(x + 1, y),
                getIndexes(x + 1, y + 1),
                getIndexes(x, y + 1),
                getIndexes(x - 1, y),
                getIndexes(x - 1, y - 1),
                getIndexes(x, y - 1),
                getIndexes(x - 1, y + 1),
                getIndexes(x + 1, y - 1)
        ).flatMap(Collection::stream);
    }
}

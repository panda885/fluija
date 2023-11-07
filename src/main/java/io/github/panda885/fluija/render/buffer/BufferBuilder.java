package io.github.panda885.fluija.render.buffer;

import io.github.panda885.fluija.math.Vector2f;
import io.github.panda885.fluija.math.Vector3f;
import io.github.panda885.fluija.util.ArrayUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.util.ArrayList;
import java.util.List;

public class BufferBuilder {
    private final List<Float> data = new ArrayList<>();

    public void vertex(Vector3f pos, Vector2f uv) {
        vertex(pos.x, pos.y, pos.z, uv.x, uv.y);
    }

    public void vertex(float x, float y, float z, float u, float v) {
        this.data.add(x);
        this.data.add(y);
        this.data.add(z);
        this.data.add(u);
        this.data.add(v);
    }

    public float[] toArray() {
        return ArrayUtils.toPrimitive(data);
    }

    public int end() {
        int handle = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, handle);
        end(handle);
        return handle;
    }

    public void end(int handle) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, handle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, toArray(), GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 20, 0);
        GL20.glEnableVertexAttribArray(0);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 20, 12);
        GL20.glEnableVertexAttribArray(1);
    }

    public int draw(int mode) {
        int handle = end();
        GL11.glDrawArrays(mode, 0, data.size() / 5);
        return handle;
    }

    public void draw(int handle, int mode) {
        end(handle);
        GL11.glDrawArrays(mode, 0, data.size() / 5);
    }
}

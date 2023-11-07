package io.github.panda885.fluija.render.shader;

import io.github.panda885.fluija.math.Matrix4f;
import io.github.panda885.fluija.math.Vector3f;
import org.lwjgl.opengl.GL20;

import java.util.function.Consumer;

public class Shader {
    private final int handle;

    public Shader(int handle) {
        this.handle = handle;
    }

    public static Shader compile(String vertexCode, String fragmentCode) {
        int vertexHandle = createShader(GL20.GL_VERTEX_SHADER, vertexCode);
        int fragmentHandle = createShader(GL20.GL_FRAGMENT_SHADER, fragmentCode);

        int programHandle = GL20.glCreateProgram();
        GL20.glAttachShader(programHandle, vertexHandle);
        GL20.glAttachShader(programHandle, fragmentHandle);
        GL20.glLinkProgram(programHandle);

        return new Shader(programHandle);
    }

    private static int createShader(int type, String code) {
        int handle = GL20.glCreateShader(type);
        GL20.glShaderSource(handle, code);
        GL20.glCompileShader(handle);
        return handle;
    }

    private void getUniformLocation(String name, Consumer<Integer> consumer) {
        int location = GL20.glGetUniformLocation(handle, name);
        if (location < 0) return;
        consumer.accept(location);
    }

    public void setUniform1f(String name, float value) {
        getUniformLocation(name, location -> GL20.glUniform1f(location, value));
    }

    public void setUniform3f(String name, Vector3f value) {
        getUniformLocation(name, location -> GL20.glUniform3f(location, value.x, value.y, value.z));
    }

    public void setUniformMatrix4f(String name, Matrix4f value) {
        getUniformLocation(name, location -> GL20.glUniformMatrix4fv(location, false, value.data));
    }

    public void use() {
        GL20.glUseProgram(handle);
    }
}

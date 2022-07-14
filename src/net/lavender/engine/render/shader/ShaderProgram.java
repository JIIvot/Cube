package net.lavender.engine.render.shader;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class ShaderProgram {
    private final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    private final int programID;
    private final int vertexID;
    private final int fragmentID;

    public ShaderProgram(String vertexShaderPath, String fragmentShaderPath) {
        vertexID = loadShader(vertexShaderPath, ShaderType.VERTEX);
        fragmentID = loadShader(fragmentShaderPath, ShaderType.FRAGMENT);
        programID = GL20.glCreateProgram();

        GL20.glAttachShader(programID, vertexID);
        GL20.glAttachShader(programID, fragmentID);

        bindAttributes();

        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);

        getAllUniformLocations();
    }

    public void start() {
        GL20.glUseProgram(programID);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void cleanup() {
        stop();

        GL20.glDetachShader(programID, vertexID);
        GL20.glDetachShader(programID, fragmentID);

        GL20.glDeleteShader(vertexID);
        GL20.glDeleteShader(fragmentID);

        GL20.glDeleteProgram(programID);
    }

    protected abstract void bindAttributes();

    protected abstract void getAllUniformLocations();

    protected void bindAttribute(int attribute, String name) {
        GL20.glBindAttribLocation(programID, attribute, name);
    }

    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f value) {
        GL20.glUniform3f(location, value.x, value.y, value.z);
    }

    protected void loadBoolean(int location, boolean value) {
        GL20.glUniform1f(location, value ? 1 : 0);
    }

    protected void loadMatrix(int location, Matrix4f value) {
        value.store(matrixBuffer);
        matrixBuffer.flip();
        GL20.glUniformMatrix4(location, false, matrixBuffer);
    }

    private static int loadShader(String path, ShaderType shaderType) {
        String source;

        try {
            source = new String(Files.readAllBytes(Path.of(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int shaderID = GL20.glCreateShader(shaderType.type);
        GL20.glShaderSource(shaderID, source);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println(GL20.glGetShaderInfoLog(shaderID, 500));
        }

        return shaderID;
    }

    enum ShaderType {
        VERTEX(GL20.GL_VERTEX_SHADER),
        FRAGMENT(GL20.GL_FRAGMENT_SHADER);

        public final int type;

        ShaderType(int type) {
            this.type = type;
        }
    }
}

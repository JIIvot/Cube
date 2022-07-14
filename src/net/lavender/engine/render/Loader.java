package net.lavender.engine.render;

import net.lavender.engine.render.model.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Loader {
    private final List<Integer> vertexArrays = new ArrayList<>();
    private final List<Integer> vertexBuffers = new ArrayList<>();
    private final List<Integer> textures = new ArrayList<>();

    public RawModel loadToVao(float[] positions, int[] indices, float[] textureCoordinates) {
        int vaoID = createVao();
        bindIndicesBuffer(indices);

        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoordinates);

        unbindVao();

        return new RawModel(vaoID, indices.length);
    }

    public int loadTexture(String path) {
        Texture texture;
        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream("res/" + path + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int textureID = texture.getTextureID();
        textures.add(textureID);

        return textureID;
    }

    private int createVao() {
        int vaoID = GL30.glGenVertexArrays();
        vertexArrays.add(vaoID);
        GL30.glBindVertexArray(vaoID);

        return vaoID;
    }

    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vertexBuffers.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);

        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        return BufferUtils.createIntBuffer(data.length).put(data).flip();
    }

    private void storeDataInAttributeList(int attributeNumber, int attributeSize, float[] data) {
        int vboID = GL15.glGenBuffers();
        vertexBuffers.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);

        FloatBuffer buffer = storeDataInFloatBuffer(data);

        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, attributeSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        return BufferUtils.createFloatBuffer(data.length).put(data).flip();
    }

    public void cleanup() {
        vertexArrays.forEach(GL30::glDeleteVertexArrays);
        vertexBuffers.forEach(GL15::glDeleteBuffers);
        textures.forEach(GL11::glDeleteTextures);
    }

    private void unbindVao() {
        GL30.glBindVertexArray(0);
    }
}

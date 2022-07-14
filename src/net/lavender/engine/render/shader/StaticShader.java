package net.lavender.engine.render.shader;

import net.lavender.engine.entity.Camera;
import net.lavender.engine.tool.LavenderMath;
import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram {
    private static final String VERTEX_SHADER_PATH = "shaders/vertex.glsl";
    private static final String FRAGMENT_SHADER_PATH = "shaders/fragment.glsl";

    private int transformationMatrixLocation;
    private int projectionMatrixLocation;
    private int viewMatrixLocation;

    public StaticShader() {
        super(VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH);
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "aPosition");
        bindAttribute(1, "aTextureCoords");
    }

    @Override
    protected void getAllUniformLocations() {
        transformationMatrixLocation = getUniformLocation("uTransformationMatrix");
        projectionMatrixLocation = getUniformLocation("uProjectionMatrix");
        viewMatrixLocation = getUniformLocation("uViewMatrix");
    }

    public void loadTransformationMatrix(Matrix4f value) {
        loadMatrix(transformationMatrixLocation, value);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = LavenderMath.createViewMatrix(camera);
        loadMatrix(viewMatrixLocation, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f value) {
        loadMatrix(projectionMatrixLocation, value);
    }
}

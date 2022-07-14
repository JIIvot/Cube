package net.lavender;

import net.lavender.engine.entity.Camera;
import net.lavender.engine.entity.Entity;
import net.lavender.engine.render.DisplayManager;
import net.lavender.engine.render.Loader;
import net.lavender.engine.render.model.RawModel;
import net.lavender.engine.render.Renderer;
import net.lavender.engine.render.model.TexturedModel;
import net.lavender.engine.render.shader.StaticShader;
import net.lavender.engine.render.texture.ModelTexture;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class Main {
    public static void main(String[] args) {
        DisplayManager.createDisplay();

        Loader loader = new Loader();

        float[] vertices = {
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, 0.5f, 0,

                -0.5f, 0.5f, 1,
                -0.5f, -0.5f, 1,
                0.5f, -0.5f, 1,
                0.5f, 0.5f, 1,

                0.5f, 0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, -0.5f, 1,
                0.5f, 0.5f, 1,

                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                -0.5f, -0.5f, 1,
                -0.5f, 0.5f, 1,

                -0.5f, 0.5f, 1,
                -0.5f, 0.5f, 0,
                0.5f, 0.5f, 0,
                0.5f, 0.5f, 1,

                -0.5f, -0.5f, 1,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, -0.5f, 1
        };

        float[] textureCoordinates = {
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        int[] indices = {
                0, 1, 3,
                3, 1, 2,
                4, 5, 7,
                7, 5, 6,
                8, 9, 11,
                11, 9, 10,
                12, 13, 15,
                15, 13, 14,
                16, 17, 19,
                19, 17, 18,
                20, 21, 23,
                23, 21, 22

        };

        RawModel model = loader.loadToVao(vertices, indices, textureCoordinates);
        ModelTexture texture = new ModelTexture(loader.loadTexture("image"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -5), 0, 0, 0, 1);
        Camera camera = new Camera();

        StaticShader staticShader = new StaticShader();
        Renderer renderer = new Renderer(staticShader);

        while (!Display.isCloseRequested()) {
            entity.increaseRotation(1, 1, 0);
            camera.move();

            renderer.prepare();

            staticShader.start();
            staticShader.loadViewMatrix(camera);
            renderer.render(entity, staticShader);
            staticShader.stop();

            DisplayManager.updateDisplay();
        }

        staticShader.cleanup();
        loader.cleanup();
        DisplayManager.closeDisplay();
    }
}

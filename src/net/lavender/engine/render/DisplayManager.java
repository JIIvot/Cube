package net.lavender.engine.render;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class DisplayManager {
    private static final int WIDTH = 960;
    private static final int HEIGHT = 540;

    private static final String TITLE = "lavender";

    private static final int FPS_LIMIT = 60;

    public static void createDisplay() {
        ContextAttribs contextAttribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), contextAttribs);
            Display.setTitle(TITLE);
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);

        System.out.println("OpenGL: " + GL11.glGetString(GL11.GL_VERSION));
    }

    public static void updateDisplay() {
        Display.sync(FPS_LIMIT);
        Display.update();
    }

    public static void closeDisplay() {
        Display.destroy();
    }
}

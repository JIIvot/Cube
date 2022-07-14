package net.lavender.engine.render.model;

import net.lavender.engine.render.texture.ModelTexture;

public class TexturedModel {
    private final RawModel rawModel;
    private final ModelTexture texture;

    public TexturedModel(RawModel rawModel, ModelTexture texture) {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }
}

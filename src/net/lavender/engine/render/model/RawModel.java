package net.lavender.engine.render.model;

public class RawModel {
    private final int vaoID;
    private final int vertexAmount;

    public RawModel(int vaoID, int vertexAmount) {
        this.vaoID = vaoID;
        this.vertexAmount = vertexAmount;
    }

    public int getVertexAmount() {
        return vertexAmount;
    }

    public int getVaoID() {
        return vaoID;
    }
}

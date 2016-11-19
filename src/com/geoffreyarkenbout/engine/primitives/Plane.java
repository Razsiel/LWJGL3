package com.geoffreyarkenbout.engine.primitives;

import com.geoffreyarkenbout.engine.GameObject;
import com.geoffreyarkenbout.engine.graphics.Mesh;
import com.geoffreyarkenbout.engine.graphics.Texture;

public class Plane extends GameObject {
    public Plane(float size, Texture texture) {
        super();
        size /= 2;
        float[] positions = new float[]{
                // V0
                -size, 0, -size,
                // V1
                -size, 0, size,
                // V2
                size, 0, size,
                // V3
                size, 0, -size,

        };
        float[] texCoords = new float[]{
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
        };
        int[] indices = new int[]{
                //Front
                0, 1, 3,
                3, 1, 2
        };
        Mesh mesh = new Mesh(positions, null, texCoords, indices, texture);
        setMesh(mesh);
    }
}

package com.geoffreyarkenbout.engine.graphics;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Mesh {
    private final int vaoId;
    private final int ind_vboId;
    private final int pos_vboId;
    private final int col_vboId;
    private final int vertexCount;

    public Mesh(float[] positions, float[] colours, int[] indices) {
        vertexCount = indices.length;

        // Create the VAO
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Create the position VBO and bind to it
        pos_vboId = glGenBuffers();
        FloatBuffer posBuffer = BufferUtils.createFloatBuffer(positions.length);
        posBuffer.put(positions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, pos_vboId);
        glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // Create the color VBO and bind to it
        col_vboId = glGenBuffers();
        FloatBuffer colourBuffer = BufferUtils.createFloatBuffer(colours.length);
        colourBuffer.put(colours).flip();
        glBindBuffer(GL_ARRAY_BUFFER, col_vboId);
        glBufferData(GL_ARRAY_BUFFER, colourBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);

        // Create the indices VBO and bind to it
        ind_vboId = glGenBuffers();
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ind_vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        // Unbind buffers (pos and indices)
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // Unbind the VAO
        glBindVertexArray(0);
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void cleanup(){
        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDeleteBuffers(pos_vboId);
        glDeleteBuffers(col_vboId);
        glDeleteBuffers(ind_vboId);

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}

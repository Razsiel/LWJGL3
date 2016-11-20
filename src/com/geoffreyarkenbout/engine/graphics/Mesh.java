package com.geoffreyarkenbout.engine.graphics;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {

    private static final Vector3f DEFAULT_COLOUR = new Vector3f(1.0f, 1.0f, 1.0f);

    private final int vaoId;
    private final int vertexCount;
    private List<Integer> vboIds;

    private Texture texture;
    private Vector3f colour;

    public Mesh(float[] positions, float[] texCoords, float[] normals, int[] indices) {
        colour = DEFAULT_COLOUR;
        vertexCount = indices.length;
        vboIds = new ArrayList<>();

        // Create the VAO
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Position VBO
        int vboId = glGenBuffers();
        vboIds.add(vboId);
        FloatBuffer posBuffer = BufferUtils.createFloatBuffer(positions.length);
        posBuffer.put(positions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // Texture coordinates VBO
        vboId = glGenBuffers();
        vboIds.add(vboId);
        FloatBuffer texCoordsBuffer = BufferUtils.createFloatBuffer(texCoords.length);
        texCoordsBuffer.put(texCoords).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, texCoordsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        // Normals VBO
        vboId = glGenBuffers();
        vboIds.add(vboId);
        FloatBuffer normalsBuffer = BufferUtils.createFloatBuffer(normals.length);
        normalsBuffer.put(normals).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

        // Indices VBO
        int ind_vboId = glGenBuffers();
        vboIds.add(ind_vboId);
        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ind_vboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        // Unbind buffers (pos and indices)
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        // Unbind the VAO
        glBindVertexArray(0);
    }

    public boolean isTextured(){
        return texture != null;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void render() {
        if (texture != null) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture.getId());
        }

        // Bind to the VAO
        glBindVertexArray(getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        // Draw the vertices
        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void cleanup(){
        glDisableVertexAttribArray(0);

        // Delete the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int vboId : vboIds) {
            glDeleteBuffers(vboId);
        }

        if (texture != null) {
            // Delete the texture
            texture.cleanup();
        }

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
}

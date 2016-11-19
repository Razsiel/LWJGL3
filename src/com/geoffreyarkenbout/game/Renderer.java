package com.geoffreyarkenbout.game;

import com.geoffreyarkenbout.engine.GameObject;
import com.geoffreyarkenbout.engine.Utils;
import com.geoffreyarkenbout.engine.Window;
import com.geoffreyarkenbout.engine.graphics.Mesh;
import com.geoffreyarkenbout.engine.graphics.ShaderProgram;
import com.geoffreyarkenbout.engine.graphics.Transformation;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;
    private final Transformation transformation;

    private ShaderProgram shaderProgram;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init(Window window) throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shaderProgram.link();

        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("worldMatrix");
        shaderProgram.createUniform("texture_sampler");

        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void clear(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(Window window, GameObject[] gameObjects) {
        clear();

        if ( window.isResized() ) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();

        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);

        shaderProgram.setUniform("texture_sampler", 0);

        // Render each gameObject
        for (GameObject gameObject : gameObjects) {
            Matrix4f worldMatrix = transformation.getWorldMatrix(gameObject.getPosition(), gameObject.getRotation(), gameObject.getScale());
            shaderProgram.setUniform("worldMatrix", worldMatrix);
            gameObject.getMesh().render();
        }

        shaderProgram.unbind();
    }

    public void cleanup(){
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }
}

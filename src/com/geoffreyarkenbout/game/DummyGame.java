package com.geoffreyarkenbout.game;

import com.geoffreyarkenbout.engine.GameObject;
import com.geoffreyarkenbout.engine.IGameLogic;
import com.geoffreyarkenbout.engine.MouseInput;
import com.geoffreyarkenbout.engine.Window;
import com.geoffreyarkenbout.engine.graphics.Camera;
import com.geoffreyarkenbout.engine.graphics.Mesh;
import com.geoffreyarkenbout.engine.graphics.OBJLoader;
import com.geoffreyarkenbout.engine.graphics.Texture;
import com.geoffreyarkenbout.engine.primitives.Plane;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class DummyGame implements IGameLogic {

    private final Renderer renderer;
    private final Camera camera;
    private GameObject[] gameObjects;
    private Vector3f cameraInc;

    private static final float MOUSE_SENSITIVITY = 0.3f;
    private static final float CAMERA_POS_STEP = 0.05f;

    public DummyGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        Texture texture = new Texture("/textures/grassblock.png");

        Mesh mesh = OBJLoader.loadMesh("/models/bunny.obj");
        //mesh.setTexture(texture);

        GameObject gameObject = new GameObject(mesh);
        gameObject.setScale(0.25f);
        gameObject.setPosition(0, 0, -2);

        gameObjects = new GameObject[]{ gameObject };
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            cameraInc.y = 1;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        // Update camera position
        camera.translate(cameraInc.x * CAMERA_POS_STEP,
                cameraInc.y * CAMERA_POS_STEP,
                cameraInc.z * CAMERA_POS_STEP);

        // Update camera based on mouse
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.rotate(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, gameObjects);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getMesh() != null) {
                gameObject.getMesh().cleanup();
            }
        }
    }
}

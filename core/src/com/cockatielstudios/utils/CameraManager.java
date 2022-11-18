package com.cockatielstudios.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.cockatielstudios.gameObjects.entities.Player;
import com.cockatielstudios.screens.GameScreen;

import static com.cockatielstudios.Constants.*;

public class CameraManager {
    private OrthographicCamera camera;
    private Player player;
    private float movedDistance;

    public CameraManager(OrthographicCamera camera, Player player) {
        this.camera = camera;
        this.player = player;
        this.movedDistance = this.camera.position.x + (WORLD_WIDTH / PPM) / 6;
    }

    public void update() {
        this.moveCamera();
        this.checkPlayerOnScreen();
        this.camera.update();
    }

    private void moveCamera() {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x += CAMERA_SPEED;
        }
        if (this.player.getPosition().x > this.movedDistance) {
            this.camera.position.x = this.player.getPosition().x - (WORLD_WIDTH / PPM) / 6;
            this.movedDistance = this.player.getPosition().x;
        }
    }

    private void checkPlayerOnScreen() {

    }
}

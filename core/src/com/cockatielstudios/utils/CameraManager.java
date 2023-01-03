package com.cockatielstudios.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.cockatielstudios.gameObjects.entities.Player;
import static com.cockatielstudios.Constants.WORLD_WIDTH;
import static com.cockatielstudios.Constants.PPM;
import static com.cockatielstudios.Constants.CAMERA_SPEED;

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
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.camera.position.y -= CAMERA_SPEED;
        }
        if (this.player.getPosition().x > this.movedDistance) {
            this.camera.position.x = this.player.getPosition().x - (WORLD_WIDTH / PPM) / 6;
            this.movedDistance = this.player.getPosition().x;
        }
    }

    private void checkPlayerOnScreen() {
        float screenCorner = this.camera.position.x - (WORLD_WIDTH / PPM) / 2;
        if (this.player.getPosition().x <= screenCorner && this.player.getBody().getLinearVelocity().x <= 0f) {
            this.player.getBody().setLinearVelocity(0f, this.player.getBody().getLinearVelocity().y);
            this.player.setMovableToLeft(false);
        } else {
            this.player.setMovableToLeft(true);
        }
    }
}

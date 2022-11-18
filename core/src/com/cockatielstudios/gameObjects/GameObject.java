package com.cockatielstudios.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.utils.CollisionListener;
import static com.cockatielstudios.Constants.*;

public abstract class GameObject {
    private GameScreen screen;
    private Vector2 position;
    private float width;
    private float height;

    public Body body;

    public GameObject(GameScreen screen, Vector2 position, float width, float height) {
        this.screen = screen;
        this.position = new Vector2(position.x / PPM, position.y / PPM);
        this.width = width / PPM;
        this.height = height / PPM;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public World getWorld() {
        return this.screen.world;
    }

    public Vector2 getPosition() {
        return position;
    }

    public CollisionListener getCollisions() {
        return screen.collisionListener;
    }

    public void setCornerPosition(Vector2 position) {
        this.position = new Vector2(position.x  - (this.getWidth() / 2), position.y - (this.getHeight() / 2));
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public abstract void render(SpriteBatch spriteBatch);

    public abstract void update(float delta);

    public abstract void dispose();
}

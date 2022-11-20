package com.cockatielstudios.gameObjects.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.utils.State;
import com.cockatielstudios.gameObjects.GameObject;
import com.cockatielstudios.screens.GameScreen;

public abstract class Entity extends GameObject {
    private State state;

    public Entity(GameScreen screen, Vector2 position, float width, float height) {
        super(screen, position, width, height);
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public abstract void render(SpriteBatch spriteBatch);

    @Override
    public abstract void update(float delta);

    public abstract void movement();

    public abstract void animate();
}

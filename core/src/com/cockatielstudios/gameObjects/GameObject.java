package com.cockatielstudios.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.cockatielstudios.screens.GameScreen;

public abstract class GameObject {
    private GameScreen screen;
    private Vector2 position;
    private float width;
    private float height;

    public Body body;

    public GameObject(GameScreen screen, Vector2 position, float width, float height) {
        this.screen = screen;
        this.position = position;
        this.width = width;
        this.height = height;

        this.createBody(this.position);
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

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public abstract void render(SpriteBatch spriteBatch);

    public abstract void update(float delta);

    public abstract void dispose();

    protected void createBody(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.getWidth() / 2, this.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        //fixtureDef.density = 1f;

        this.body = this.getWorld().createBody(bodyDef);
        this.body.createFixture(fixtureDef);

        polygonShape.dispose();
    }
}

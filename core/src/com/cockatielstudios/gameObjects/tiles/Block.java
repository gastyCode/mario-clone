package com.cockatielstudios.gameObjects.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cockatielstudios.gameObjects.GameObject;
import com.cockatielstudios.screens.GameScreen;

public class Block extends GameObject {
    public Block(GameScreen screen, Vector2 position, float width, float height) {
        super(screen, position, width, height);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {

    }

    @Override
    protected void createBody(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.getWidth() / 2, this.getHeight() / 2);

        this.body = this.getWorld().createBody(bodyDef);
        this.body.createFixture(polygonShape, 0f);

        polygonShape.dispose();
    }
}

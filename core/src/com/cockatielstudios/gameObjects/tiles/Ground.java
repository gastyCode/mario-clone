package com.cockatielstudios.gameObjects.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cockatielstudios.gameObjects.GameObject;
import com.cockatielstudios.screens.GameScreen;

public class Ground extends GameObject {
    public Ground(GameScreen screen, Vector2 position, float width, float height) {
        super(screen, position, width, height);
        this.createBody(this.getPosition());
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

    private void createBody(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.getWidth() / 2, this.getHeight() / 2, new Vector2(getWidth() / 2, getHeight() / 2), 0f);

        this.body = this.getWorld().createBody(bodyDef);
        this.body.createFixture(polygonShape, 0f).setUserData("ground");

        polygonShape.dispose();
    }
}

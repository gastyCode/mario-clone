package com.cockatielstudios.gameObjects.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.utils.ObjectName;
import com.cockatielstudios.utils.State;

public class Goomba extends Entity {
    public Goomba(GameScreen screen, Vector2 position, float width, float height) {
        super(screen, position, width, height, State.DEFAULT);
        this.createBody(this.getPosition());
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void movement() {

    }

    @Override
    public void animate() {

    }

    private void createBody(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.getWidth() / 2f, this.getHeight() / 2f);

        // Create Goomba body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0f;
        this.getBody().createFixture(fixtureDef).setUserData(ObjectName.GOOMBA);

        this.setBody(this.getWorld().createBody(bodyDef));

        polygonShape.dispose();
    }
}

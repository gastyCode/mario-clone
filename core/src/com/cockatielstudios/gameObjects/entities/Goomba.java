package com.cockatielstudios.gameObjects.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cockatielstudios.Assets;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.utils.State;
import static com.cockatielstudios.Constants.*;

public class Goomba extends Entity {
    private float speed;
    private Texture test;
    private boolean isDisposed;
    private int id;

    public Goomba(GameScreen screen, Vector2 position, float width, float height, int id) {
        super(screen, position, width, height, State.DEFAULT);
        this.createBody(this.getPosition());
        this.speed = -GOOMBA_SPEED;
        this.isDisposed = false;
        this.id = id;

        this.test = Assets.manager.get(Assets.goomba);
    }

    public int getID() {
        return id;
    }

    public boolean getDisposed() {
        return this.isDisposed;
    }

    @Override
    public void dispose() {
        if (!this.isDisposed && !this.getWorld().isLocked()) {
            this.isDisposed = true;
            this.getWorld().destroyBody(this.getBody());
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.test, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
    }

    @Override
    public void update(float delta) {
        this.movement();
        this.setCornerPosition(this.getBody().getPosition());
    }

    @Override
    public void movement() {
        if (Math.abs(this.getBody().getLinearVelocity().x) < GOOMBA_MAX_FORCE) {
            this.getBody().applyLinearImpulse(new Vector2(this.speed, 0f), this.getBody().getWorldCenter(), true);
        }
    }

    @Override
    public void animate() {

    }

    public void switchDirection() {
        this.speed = -this.speed;
    }

    private void createBody(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.getWidth() / 2f, this.getHeight() / 2f);

        this.setBody(this.getWorld().createBody(bodyDef));

        // Create Goomba body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0f;
        this.getBody().createFixture(fixtureDef).setUserData(this);

        polygonShape.dispose();
    }
}

package com.cockatielstudios.gameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.cockatielstudios.Assets;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.utils.Facing;
import static com.cockatielstudios.Constants.*;

public class Fireball extends GameObject{
    private Texture texture;
    private float speed;
    private int id;
    private boolean isDisposed;

    public Fireball(GameScreen screen, Vector2 position, float width, float height, Facing facing, int id) {
        super(screen, position, width, height);
        this.texture = Assets.manager.get(Assets.fireball);
        this.id = id;
        this.isDisposed = false;

        Vector2 spawnPosition = this.getScreen().getPlayerPosition();
        if (facing == Facing.RIGHT) {
            this.speed = FIREBALL_SPEED;
            spawnPosition = new Vector2(spawnPosition.x + (width / PPM), spawnPosition.y * 1.5f);
        } else {
            this.speed = -FIREBALL_SPEED;
            spawnPosition = new Vector2(spawnPosition.x - (width / PPM), spawnPosition.y * 1.5f);
        }
        this.createBody(spawnPosition);
        this.bounce();
    }

    public int getID() {
        return id;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.texture, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
    }

    @Override
    public void update(float delta) {
        this.setCornerPosition(this.getBody().getPosition());
    }

    @Override
    public void dispose() {
        if (!this.isDisposed) {
            this.isDisposed = true;
            if (!this.getWorld().isLocked()) {
                this.getWorld().destroyBody(this.getBody());
            }
        }
    }

    public void bounce() {
        this.getBody().setLinearVelocity(0f, 0f);
        this.getBody().applyLinearImpulse(new Vector2(this.speed, 2f), this.getBody().getWorldCenter(), true);
    }

    private void createBody(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(this.getWidth() / 4);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0f;

        this.setBody(this.getWorld().createBody(bodyDef));
        this.getBody().createFixture(fixtureDef).setUserData(this);

        circleShape.dispose();
    }
}

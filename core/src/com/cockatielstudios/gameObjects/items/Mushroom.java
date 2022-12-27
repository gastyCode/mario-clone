package com.cockatielstudios.gameObjects.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.utils.State;
import static com.cockatielstudios.Constants.*;

public class Mushroom extends Item{
    private final TextureRegion texture;
    private float speed;
    private boolean isDisposed;

    public Mushroom(GameScreen screen, Vector2 position, float width, float height, int id) {
        super(screen, position, width, height, State.BIG, id);
        this.createBody(this.getPosition(), this);
        this.texture = new TextureRegion(this.getTextureRegion("mushroom"));
        this.speed = MUSHROOM_SPEED;
        this.isDisposed = false;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.texture, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
    }

    @Override
    public void update(float delta) {
        this.movement();
        this.setPosition(this.getBody().getPosition());
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

    public void movement() {
        if (Math.abs(this.getBody().getLinearVelocity().x) < MUSHROOM_MAX_FORCE) {
            this.getBody().applyLinearImpulse(new Vector2(this.speed, 0f), this.getBody().getWorldCenter(), true);
        }
    }

    public void switchDirection() {
        this.speed = -this.speed;
    }
}

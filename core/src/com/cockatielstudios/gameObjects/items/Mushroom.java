package com.cockatielstudios.gameObjects.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.utils.State;
import static com.cockatielstudios.Constants.*;

public class Mushroom extends Item{
    private final TextureRegion texture;
    private float speed;

    public Mushroom(GameScreen screen, Vector2 position, float width, float height, int id) {
        super(screen, position, width, height, id);
        this.createBody(this.getPosition(), this);
        this.setStateChange(State.BIG);
        this.texture = new TextureRegion(this.getTextureRegion("mushroom"));
        this.speed = MUSHROOM_SPEED;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.texture, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
    }

    @Override
    public void update(float delta) {
        this.movement();

        this.setPosition(this.body.getPosition());
    }

    @Override
    public void dispose() {
        this.texture.getTexture().dispose();
    }

    public void movement() {
        if (Math.abs(this.body.getLinearVelocity().x) < MUSHROOM_MAX_FORCE) {
            this.body.applyLinearImpulse(new Vector2(this.speed, 0f), this.body.getWorldCenter(), true);
        }
    }

    public void switchDirection() {
        this.speed = -this.speed;
    }

    public void onCollision() {
        this.body.setActive(false);
        this.dispose();
    }
}

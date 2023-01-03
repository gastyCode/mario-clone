package com.cockatielstudios.gameObjects.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.screens.GameScreen;
import static com.cockatielstudios.Constants.COIN_LIFESPAN;

public class Coin extends Item {
    private final TextureRegion texture;
    private float time;
    private boolean isDisposed;

    public Coin(GameScreen screen, Vector2 position, float width, float height) {
        super(screen, position, width, height, 0);
        this.createBody(this.getPosition(), this);
        this.texture = new TextureRegion(this.getTextureRegion("coin1"));
        this.time = 0f;
        this.isDisposed = false;

        this.getBody().applyLinearImpulse(new Vector2(0f, 1f), this.getBody().getWorldCenter(), true);
    }

    public boolean getDisposed() {
        return this.isDisposed;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.texture, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
    }

    @Override
    public void update(float delta) {
        this.time += delta;
        if (this.time > COIN_LIFESPAN) {
            this.dispose();
        }
        this.setPosition(this.getBody().getPosition());
    }

    @Override
    public void dispose() {
        if (!this.getWorld().isLocked()) {
            this.getWorld().destroyBody(this.getBody());
            this.isDisposed = true;
        }
    }
}

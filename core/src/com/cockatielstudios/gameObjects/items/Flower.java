package com.cockatielstudios.gameObjects.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.screens.GameScreen;

public class Flower extends Item {
    private final TextureRegion texture;
    private boolean isDisposed;

    public Flower(GameScreen screen, Vector2 position, float width, float height, int id) {
        super(screen, position, width, height, id);
        this.createBody(this.getPosition(), this);
        this.texture = new TextureRegion(this.getTextureRegion("flower"));
        this.isDisposed = false;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.texture, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
    }

    @Override
    public void update(float delta) {

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
}

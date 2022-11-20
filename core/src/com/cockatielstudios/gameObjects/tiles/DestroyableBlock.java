package com.cockatielstudios.gameObjects.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cockatielstudios.gameObjects.GameObject;
import com.cockatielstudios.screens.GameScreen;

public abstract class DestroyableBlock extends GameObject {
    private int id;
    private Texture destroyedTile;

    public DestroyableBlock(GameScreen screen, Vector2 position, float width, float height, int id) {
        super(screen, position, width, height);
        this.id = id;
        this.destroyedTile = null;
    }

    public int getID() {
        return id;
    }

    public void setDestroyedTile(Texture destroyedTile) {
        this.destroyedTile = destroyedTile;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if (this.destroyedTile != null) {
            spriteBatch.draw(this.destroyedTile, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
        }
    }

    @Override
    public abstract void update(float delta);

    @Override
    public abstract void dispose();

    public abstract void onCollision();

    protected <T> void createBody(Vector2 position, T object) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.getWidth() / 2, this.getHeight() / 2, new Vector2(getWidth() / 2, getHeight() / 2), 0f);

        this.body = this.getWorld().createBody(bodyDef);
        this.body.createFixture(polygonShape, 0f).setUserData(object);

        polygonShape.dispose();
    }
}

package com.cockatielstudios.gameObjects.items;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cockatielstudios.Assets;
import com.cockatielstudios.gameObjects.GameObject;
import com.cockatielstudios.gameObjects.entities.Player;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.utils.State;

public abstract class Item extends GameObject {
    private TextureAtlas textureAtlas;
    private State stateChange;
    private int id;

    public Item(GameScreen screen, Vector2 position, float width, float height, State stateChange, int id) {
        super(screen, position, width, height);
        this.textureAtlas = Assets.manager.get(Assets.items);
        this.stateChange = stateChange;
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public TextureRegion getTextureRegion(String region) {
        return this.textureAtlas.findRegion(region);
    }

    public State getStateChange() {
        return stateChange;
    }

    protected <T> void createBody(Vector2 position, T object) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.getWidth() / 8, this.getHeight() / 8 - 0.01f, new Vector2(getWidth() / 2, getHeight() / 8), 0f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 0f;

        this.setBody(this.getWorld().createBody(bodyDef));
        this.getBody().createFixture(fixtureDef).setUserData(object);

        polygonShape.dispose();
    }
}

package com.cockatielstudios.gameObjects.tiles;

import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.Assets;
import com.cockatielstudios.screens.GameScreen;

public class Block extends DestroyableBlock {
    public Block(GameScreen screen, Vector2 position, float width, float height, int id) {
        super(screen, position, width, height, id);
        this.createBody(this.getPosition(), this);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void onCollision() {
        this.getBody().setActive(false);
        this.setDestroyedTile(Assets.manager.get(Assets.emptyTile));
    }
}

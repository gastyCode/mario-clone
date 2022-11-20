package com.cockatielstudios.gameObjects.tiles;

import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.Assets;
import com.cockatielstudios.screens.GameScreen;

public class MysteryBlock extends DestroyableBlock{
    private boolean isActive;

    public MysteryBlock(GameScreen screen, Vector2 position, float width, float height, int id) {
        super(screen, position, width, height, id);
        this.createBody(this.getPosition(), this);
        this.isActive = true;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void onCollision() {
        if (this.isActive) {
            this.isActive = false;
            this.setDestroyedTile(Assets.manager.get(Assets.emptyBlock));
        }
    }
}

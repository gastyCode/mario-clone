package com.cockatielstudios.gameObjects.tiles;

import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.Assets;
import com.cockatielstudios.gameObjects.items.Flower;
import com.cockatielstudios.gameObjects.items.Mushroom;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.utils.ObjectsManager;
import static com.cockatielstudios.Constants.*;

import java.awt.font.TextHitInfo;

public class MysteryBlock extends DestroyableBlock{
    private boolean isActive;
    private int itemID;

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
            this.spawnItem();
        }
    }

    private void spawnItem() {
        Vector2 position = new Vector2(this.getPosition().x * PPM, this.getPosition().y * PPM + ITEM_HEIGHT);
        switch (this.getScreen().getPlayerState()) {
            default:
                break;
            case SMALL:
                this.getObjectsManager().addMushroom(new Mushroom(this.getScreen(), position, ITEM_WIDTH, ITEM_HEIGHT, this.getObjectsManager().getAvailableID()));
                break;
            case BIG:
                this.getObjectsManager().addFlower(new Flower(this.getScreen(), position, ITEM_WIDTH, ITEM_HEIGHT, this.getObjectsManager().getAvailableID()));
                break;
        }
    }

    private ObjectsManager getObjectsManager() {
        return this.getScreen().getObjectsManager();
    }
}

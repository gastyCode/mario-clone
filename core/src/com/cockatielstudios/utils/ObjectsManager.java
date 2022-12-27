package com.cockatielstudios.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.gameObjects.GameObject;
import com.cockatielstudios.gameObjects.items.Mushroom;
import com.cockatielstudios.gameObjects.tiles.Block;
import com.cockatielstudios.gameObjects.tiles.MysteryBlock;
import com.cockatielstudios.screens.GameScreen;

import java.util.ArrayList;

public class ObjectsManager {
    private GameScreen screen;

    private ArrayList<Block> blocks;
    private ArrayList<MysteryBlock> mysteryBlocks;
    private ArrayList<Mushroom> mushrooms;

    private int availableID;

    public ObjectsManager(GameScreen screen) {
        this.screen = screen;
        this.availableID = 0;

        this.blocks = new ArrayList<Block>();
        this.mysteryBlocks = new ArrayList<MysteryBlock>();

        this.mushrooms = new ArrayList<Mushroom>();
    }

    public int getAvailableID() {
        this.availableID++;
        return this.availableID;
    }

    private CollisionListener getCollisions() {
        return this.screen.getCollisionListener();
    }

    public void update() {
        this.checkObjects();
    }

    public void render(SpriteBatch spriteBatch) {
        for (Block block : this.blocks) {
            block.render(spriteBatch);
        }

        for (MysteryBlock mysteryBlock : this.mysteryBlocks) {
            mysteryBlock.render(spriteBatch);
        }

        for (Mushroom mushroom : this.mushrooms) {
            mushroom.render(spriteBatch);
            mushroom.update(0f);
        }
    }

    private void checkObjects() {
        if (this.screen.getPlayerState() != State.SMALL) {
            for (Block block : this.blocks) {
                if (block.getID() == this.getCollisions().getCollidedBlockID()) {
                    block.onCollision();
                }
            }
        }

        for (MysteryBlock mysteryBlock : this.mysteryBlocks) {
            if (mysteryBlock.getID() == this.getCollisions().getCollidedMysteryBlockID()) {
                mysteryBlock.onCollision();
            }
        }

        // Remove mushroom
        int index = -1;
        for (int i = 0; i < this.mushrooms.size(); i++) {
            if (this.mushrooms.get(i).getID() == this.getCollisions().getCollidedMushroomID()) {
                this.mushrooms.get(i).dispose();
                index = i;
            }
        }
        if (index >= 0) {
            this.mushrooms.remove(index);
        }
    }

    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    public void addMysteryBlock(MysteryBlock mysteryBlock) {
        this.mysteryBlocks.add(mysteryBlock);
    }

    public void addMushroom(Mushroom mushroom) {
        this.mushrooms.add(mushroom);
    }
}

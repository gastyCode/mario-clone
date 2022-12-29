package com.cockatielstudios.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.gameObjects.entities.Goomba;
import com.cockatielstudios.gameObjects.items.Coin;
import com.cockatielstudios.gameObjects.items.Flower;
import com.cockatielstudios.gameObjects.items.Mushroom;
import com.cockatielstudios.gameObjects.tiles.Block;
import com.cockatielstudios.gameObjects.tiles.MysteryBlock;
import com.cockatielstudios.screens.GameScreen;
import static com.cockatielstudios.Constants.*;

import java.util.ArrayList;

public class ObjectsManager {
    private GameScreen screen;

    private ArrayList<Block> blocks;
    private ArrayList<MysteryBlock> mysteryBlocks;
    private ArrayList<Mushroom> mushrooms;
    private ArrayList<Flower> flowers;
    private ArrayList<Goomba> goombas;
    private ArrayList<Coin> coins;

    private int availableID;
    private Goomba goomba;

    public ObjectsManager(GameScreen screen) {
        this.screen = screen;
        this.availableID = 0;

        this.blocks = new ArrayList<Block>();
        this.mysteryBlocks = new ArrayList<MysteryBlock>();

        this.mushrooms = new ArrayList<Mushroom>();
        this.flowers = new ArrayList<Flower>();
        this.goombas = new ArrayList<Goomba>();
        this.coins = new ArrayList<Coin>();

        this.goomba = new Goomba(this.screen, new Vector2(32, 32), 16, 16);
    }

    public int getAvailableID() {
        this.availableID++;
        return this.availableID;
    }

    private CollisionListener getCollisions() {
        return this.screen.getCollisionListener();
    }

    public void update() {
        this.removeObjects();
        this.checkObjects();
    }

    public void render(SpriteBatch spriteBatch, float delta) {
        for (Block block : this.blocks) {
            block.render(spriteBatch);
        }

        for (MysteryBlock mysteryBlock : this.mysteryBlocks) {
            mysteryBlock.render(spriteBatch);
        }

        for (Mushroom mushroom : this.mushrooms) {
            mushroom.render(spriteBatch);
            mushroom.update(delta);
        }

        for (Flower flower : this.flowers) {
            flower.render(spriteBatch);
        }

        for (Coin coin : this.coins) {
            coin.render(spriteBatch);
            coin.update(delta);
        }

        this.goomba.render(spriteBatch);
        this.goomba.update(delta);
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
    }

    public void removeObjects() {
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

        // Remove flower
        index = -1;
        for (int i = 0; i < this.flowers.size(); i++) {
            if (this.flowers.get(i).getID() == this.getCollisions().getCollidedFlowerID()) {
                this.flowers.get(i).dispose();
                index = i;
            }
        }
        if (index >= 0) {
            this.flowers.remove(index);
        }

        for (int i = 0; i < this.coins.size(); i++) {
            if (this.coins.get(i).getDisposed()) {
                index = i;
                this.screen.getHud().addScore(COIN_SCORE);
            }
        }
        if (index >= 0) {
            this.coins.remove(index);
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

    public void addFlower(Flower flower) {
        this.flowers.add(flower);
    }

    public void addCoin(Coin coin) {
        this.coins.add(coin);
    }
}

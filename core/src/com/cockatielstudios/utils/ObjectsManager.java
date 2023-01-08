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
import static com.cockatielstudios.Constants.PPM;
import static com.cockatielstudios.Constants.COIN_SCORE;
import static com.cockatielstudios.Constants.ENEMY_SPAWN_DISTANCE;
import static com.cockatielstudios.Constants.ENEMY_WIDTH;
import static com.cockatielstudios.Constants.ENEMY_HEIGHT;

import java.util.ArrayList;

/**
 * Trieda, potrebná na manažovanie objektov.
 */
public class ObjectsManager {
    private GameScreen screen;

    private ArrayList<Vector2> goombasData;

    private ArrayList<Block> blocks;
    private ArrayList<MysteryBlock> mysteryBlocks;
    private ArrayList<Mushroom> mushrooms;
    private ArrayList<Flower> flowers;
    private ArrayList<Goomba> goombas;
    private ArrayList<Coin> coins;

    private int availableID;

    /**
     * Konštruktor, ktorý nastavuje atribúty manažera a všetky potrebné kontajnere pre všetky objekty, ktoré bude
     * manažovať.
     *
     * @param screen Inštancia tiredy GameScreen, ktorá vykresľuje samotnú hru.
     */
    public ObjectsManager(GameScreen screen) {
        this.screen = screen;
        this.availableID = 0;

        this.goombasData = new ArrayList<Vector2>();

        this.blocks = new ArrayList<Block>();
        this.mysteryBlocks = new ArrayList<MysteryBlock>();

        this.mushrooms = new ArrayList<Mushroom>();
        this.flowers = new ArrayList<Flower>();
        this.goombas = new ArrayList<Goomba>();
        this.coins = new ArrayList<Coin>();
    }

    public int getAvailableID() {
        this.availableID++;
        return this.availableID;
    }

    private CollisionListener getCollisions() {
        return this.screen.getCollisionListener();
    }

    /**
     * Vymazáva objekty, ktoré už nie sú potrebné, vytvára nepriateľov a kontroluje kolízie medzi objektami.
     */
    public void update() {
        this.removeObjects();
        this.spawnEnemies();
        this.checkObjects();
    }

    /**
     * Vykreslí a aktualizuje každý objekt v kontajneroch, ktoré manažuje.
     *
     * @param spriteBatch Pomocník pri vykresľovaní textúr.
     * @param delta Čas v sekundách od posledného rámca.
     */
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

        for (Goomba goomba : this.goombas) {
            goomba.render(spriteBatch);
            goomba.update(delta);
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
    }

    private void removeObjects() {
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

        // Remove coin
        index = -1;
        for (int i = 0; i < this.coins.size(); i++) {
            if (this.coins.get(i).getDisposed()) {
                index = i;
                this.screen.getHud().addScore(COIN_SCORE);
            }
        }
        if (index >= 0) {
            this.coins.remove(index);
        }

        // Remove goomba
        index = -1;
        for (int i = 0; i < this.goombas.size(); i++) {
            if (this.goombas.get(i).getID() == this.getCollisions().getCollidedGoombaID()) {
                this.goombas.get(i).dispose();
                index = i;
            }
        }
        if (index >= 0) {
            this.goombas.remove(index);
        }
    }

    private void spawnEnemies() {
        for (int i = 0; i < this.goombasData.size(); i++) {
            Vector2 position = this.goombasData.get(i);
            if (position.x - (this.screen.getPlayerPosition().x * PPM) < ENEMY_SPAWN_DISTANCE) {
                this.goombas.add(new Goomba(this.screen, position, ENEMY_WIDTH, ENEMY_HEIGHT, this.getAvailableID()));
                this.goombasData.remove(i);
            }
        }
    }

    /**
     * Pridá blok do kontajneru blokov.
     *
     * @param block Blok na pridanie.
     */
    public void addBlock(Block block) {
        this.blocks.add(block);
    }

    /**
     * Pridá záhadný blok do kontajneru záhadných blokov.
     *
     * @param mysteryBlock Záhadný blok na pridanie.
     */
    public void addMysteryBlock(MysteryBlock mysteryBlock) {
        this.mysteryBlocks.add(mysteryBlock);
    }

    /**
     * Pridá hub do kontajneru húb.
     *
     * @param mushroom Huba na pridanie.
     */
    public void addMushroom(Mushroom mushroom) {
        this.mushrooms.add(mushroom);
    }

    /**
     * Pridá kvet do kontajneru kvetov.
     *
     * @param flower Kvet na pridanie.
     */
    public void addFlower(Flower flower) {
        this.flowers.add(flower);
    }

    /**
     * Pridá mincu do kontajneru mincí.
     *
     * @param coin Minca na pridanie.
     */
    public void addCoin(Coin coin) {
        this.coins.add(coin);
    }

    /**
     * Pridá pozíciu nepriateľa do kontajneru dát nepriateľov.
     *
     * @param position Pozícia na pridanie.
     */
    public void addGoombaData(Vector2 position) {
        this.goombasData.add(position);
    }
}

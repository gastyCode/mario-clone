package com.cockatielstudios.gameObjects.tiles;

import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.Assets;
import com.cockatielstudios.screens.GameScreen;

/**
 * Trieda, ktorá dedí z triedy DestroyableBlock, potrebná na vytvorenie zničiteľného bloku.
 */
public class Block extends DestroyableBlock {
    /**
     * Konštruktor, ktorý nastavuje základné atributy bloku.
     *
     * @param screen Inštancia tiredy GameScreen, ktorá vykresľuje samotnú hru.
     * @param position Pozícia bloku.
     * @param width Šírka bloku.
     * @param height Výška bloku.
     * @param id Identifikačné číslo bloku.
     */
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

    /**
     * Pri kolizií s hlavou hráča sa telo bloku stane neviditeľné a textúra bloku sa zmení na prázdnu.
     */
    @Override
    public void onCollision() {
        this.getBody().setActive(false);
        this.setDestroyedTile(Assets.MANAGER.get(Assets.EMPTY_TILE));
    }
}

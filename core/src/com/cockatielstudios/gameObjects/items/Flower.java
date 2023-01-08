package com.cockatielstudios.gameObjects.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.screens.GameScreen;

/**
 * Trieda, ktorá dedí z triedy Item, potrebná na vytvorenie predmetu, ktorý zvýši hráčov stav.
 */
public class Flower extends Item {
    private final TextureRegion texture;
    private boolean isDisposed;

    /**
     * Konštruktor, ktorý nastavuje všetký potrebné atribúty kvetu.
     *
     * @param screen Inštancia tiredy GameScreen, ktorá vykresľuje samotnú hru.
     * @param position Pozícia kvetu.
     * @param width Šírka kvetu.
     * @param height Výška kvetu.
     */
    public Flower(GameScreen screen, Vector2 position, float width, float height, int id) {
        super(screen, position, width, height, id);
        this.createBody(this.getPosition(), this);
        this.texture = new TextureRegion(this.getTextureRegion("flower"));
        this.isDisposed = false;
    }

    /**
     * Vykresľuje textúru kvetu.
     *
     * @param spriteBatch Pomocník pri vykresľovaní textúr.
     */
    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.texture, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
    }

    @Override
    public void update(float delta) {

    }

    /**
     * Ak je svet odomknutý a telo ešte nebolo zničené, dochádza k zničeniu tela.
     */
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

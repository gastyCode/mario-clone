package com.cockatielstudios.gameObjects.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.screens.GameScreen;
import static com.cockatielstudios.Constants.COIN_LIFESPAN;

/**
 * Trieda, ktorá dedí z triedy Item, potrebná na vytvorenie predmetu, ktorý pridáva skóre hráčovi.
 */
public class Coin extends Item {
    private final TextureRegion texture;
    private float time;
    private boolean isDisposed;

    /**
     * Konštruktor, ktorý nastavuje všetký potrebné atribúty mince.
     *
     * @param screen Inštancia tiredy GameScreen, ktorá vykresľuje samotnú hru.
     * @param position Pozícia mince.
     * @param width Šírka mince.
     * @param height Výška mince.
     */
    public Coin(GameScreen screen, Vector2 position, float width, float height) {
        super(screen, position, width, height, 0);
        this.createBody(this.getPosition(), this);
        this.texture = new TextureRegion(this.getTextureRegion("coin1"));
        this.time = 0f;
        this.isDisposed = false;

        this.getBody().applyLinearImpulse(new Vector2(0f, 1f), this.getBody().getWorldCenter(), true);
    }

    public boolean getDisposed() {
        return this.isDisposed;
    }

    /**
     * Vykresľuje textúru mince.
     *
     * @param spriteBatch Pomocník pri vykresľovaní textúr.
     */
    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.texture, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
    }

    /**
     * Dochádza k zarovnaní tela s textúrov. Pri prekročení času životnosti sa minca zničí.
     *
     * @param delta Čas v sekundách od posledného rámca.
     */
    @Override
    public void update(float delta) {
        this.time += delta;
        if (this.time > COIN_LIFESPAN) {
            this.dispose();
        }
        this.setPosition(this.getBody().getPosition());
    }

    /**
     * Ak je svet odomknutý, dochádza k zničeniu tela.
     */
    @Override
    public void dispose() {
        if (!this.getWorld().isLocked()) {
            this.getWorld().destroyBody(this.getBody());
            this.isDisposed = true;
        }
    }
}

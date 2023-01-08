package com.cockatielstudios.gameObjects.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.screens.GameScreen;
import static com.cockatielstudios.Constants.MUSHROOM_SPEED;
import static com.cockatielstudios.Constants.MUSHROOM_MAX_FORCE;

/**
 * Trieda, ktorá dedí z triedy Item, potrebná na vytvorenie predmetu, ktorý zvýši hráčov stav.
 */
public class Mushroom extends Item {
    private final TextureRegion texture;
    private float speed;
    private boolean isDisposed;

    /**
     * Konštruktor, ktorý nastavuje všetký potrebné atribúty huby.
     *
     * @param screen Inštancia tiredy GameScreen, ktorá vykresľuje samotnú hru.
     * @param position Pozícia huby.
     * @param width Šírka huby.
     * @param height Výška huby.
     */
    public Mushroom(GameScreen screen, Vector2 position, float width, float height, int id) {
        super(screen, position, width, height, id);
        this.createBody(this.getPosition(), this);
        this.texture = new TextureRegion(this.getTextureRegion("mushroom"));
        this.speed = MUSHROOM_SPEED;
        this.isDisposed = false;
    }

    /**
     * Vykresľuje textúru huby.
     *
     * @param spriteBatch Pomocník pri vykresľovaní textúr.
     */
    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.texture, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
    }

    /**
     * Dochádza k pohybu a zarovnaniu tela s textúrov.
     *
     * @param delta Čas v sekundách od posledného rámca.
     */
    @Override
    public void update(float delta) {
        this.movement();
        this.setPosition(this.getBody().getPosition());
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

    private void movement() {
        if (Math.abs(this.getBody().getLinearVelocity().x) < MUSHROOM_MAX_FORCE) {
            this.getBody().applyLinearImpulse(new Vector2(this.speed, 0f), this.getBody().getWorldCenter(), true);
        }
    }

    /**
     * Zmení smer pohybu huby.
     */
    public void switchDirection() {
        this.speed = -this.speed;
    }
}

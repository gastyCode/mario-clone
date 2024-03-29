package com.cockatielstudios.gameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.utils.CollisionListener;
import static com.cockatielstudios.Constants.PPM;

/**
 * Abstraktná trieda potrebná na zjednodušenie vytvárania herných objektov.
 *
 * Nápad vytvorenie tejto triedy bol vďaka video tutoriálu: https://youtu.be/6QKhSctuMcs
 */
public abstract class GameObject {
    private GameScreen screen;
    private Vector2 position;
    private float width;
    private float height;
    private Body body;

    /**
     * Konštruktor, ktorý nastavuje základné atributy herného objektu.
     *
     * @param screen Inštancia tiredy GameScreen, ktorá vykresľuje samotnú hru.
     * @param position Pozícia herného objektu.
     * @param width Šírka herného objektu.
     * @param height Výška herného objektu.
     */
    public GameObject(GameScreen screen, Vector2 position, float width, float height) {
        this.screen = screen;
        this.position = new Vector2(position.x / PPM, position.y / PPM);
        this.width = width / PPM;
        this.height = height / PPM;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public GameScreen getScreen() {
        return this.screen;
    }

    public Body getBody() {
        return this.body;
    }

    public World getWorld() {
        return this.getScreen().getWorld();
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public CollisionListener getCollisions() {
        return this.screen.getCollisionListener();
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setCornerPosition(Vector2 position) {
        this.position = new Vector2(position.x  - (this.getWidth() / 2), position.y - (this.getHeight() / 2));
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    /**
     * Vykresľuje textúru herného objektu.
     *
     * @param spriteBatch Pomocník pri vykresľovaní textúr.
     */
    public abstract void render(SpriteBatch spriteBatch);

    /**
     * Aktualizuje herný objekt.
     *
     * @param delta Čas v sekundách od posledného rámca.
     */
    public abstract void update(float delta);

    /**
     * Zničí herný objekt.
     */
    public abstract void dispose();
}

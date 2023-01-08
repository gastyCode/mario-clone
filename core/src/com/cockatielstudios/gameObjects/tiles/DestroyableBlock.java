package com.cockatielstudios.gameObjects.tiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cockatielstudios.gameObjects.GameObject;
import com.cockatielstudios.screens.GameScreen;

/**
 * Abstraktná trieda, ktorá dedí z triedy GameObject, potrebná na vytvorenie bloku s ktorým dokáže komunikovať hráč.
 */
public abstract class DestroyableBlock extends GameObject {
    private int id;
    private Texture destroyedTile;

    /**
     * Konštruktor, ktorý nastavuje základné atributy bloku.
     *
     * @param screen Inštancia tiredy GameScreen, ktorá vykresľuje samotnú hru.
     * @param position Pozícia bloku.
     * @param width Šírka bloku.
     * @param height Výška bloku.
     * @param id Identifikačné číslo bloku.
     */
    public DestroyableBlock(GameScreen screen, Vector2 position, float width, float height, int id) {
        super(screen, position, width, height);
        this.id = id;
        this.destroyedTile = null;
    }

    public int getID() {
        return this.id;
    }

    public void setDestroyedTile(Texture destroyedTile) {
        this.destroyedTile = destroyedTile;
    }

    /**
     * Vykresľuje textúru bloku.
     *
     * @param spriteBatch Pomocník pri vykresľovaní textúr.
     */
    @Override
    public void render(SpriteBatch spriteBatch) {
        if (this.destroyedTile != null) {
            spriteBatch.draw(this.destroyedTile, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
        }
    }

    /**
     * Aktualizuje blok.
     *
     * @param delta Čas v sekundách od posledného rámca.
     */
    @Override
    public abstract void update(float delta);

    /**
     * Zničí blok.
     */
    @Override
    public abstract void dispose();

    /**
     * Akcia vykonaná pri kolízií.
     */
    public abstract void onCollision();

    /**
     * Vytvorí fyzické telo podľa zadaného objektu.
     *
     * Táto metóda je inšpirovaná tutoriálom na webe:
     * https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_hello.html
     *
     * @param position Pozícia predmetu.
     * @param object Trieda objektu.
     */
    public <T> void createBody(Vector2 position, T object) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.getWidth() / 2, this.getHeight() / 2, new Vector2(getWidth() / 2, getHeight() / 2), 0f);

        this.setBody(this.getWorld().createBody(bodyDef));
        this.getBody().createFixture(polygonShape, 0f).setUserData(object);

        polygonShape.dispose();
    }
}

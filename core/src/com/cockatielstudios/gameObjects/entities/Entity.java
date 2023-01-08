package com.cockatielstudios.gameObjects.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.cockatielstudios.utils.State;
import com.cockatielstudios.gameObjects.GameObject;
import com.cockatielstudios.screens.GameScreen;


/**
 * Abstraktná trieda, ktorá dedí z triedy GameObject, potrebná pre zjednodušenia vytvárania dynamických objektov.
 */
public abstract class Entity extends GameObject {
    private State state;

    /**
     * Konštruktor, ktorý nastavuje základné atributy dynamického objektu.
     *
     * @param screen Inštancia tiredy GameScreen, ktorá vykresľuje samotnú hru.
     * @param position Pozícia subjektu.
     * @param width Šírka subjektu.
     * @param height Výška subjektu.
     * @param state Stav subjektu.
     */
    public Entity(GameScreen screen, Vector2 position, float width, float height, State state) {
        super(screen, position, width, height);
        this.state = state;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    /**
     * Vykresľuje textúru subjektu.
     *
     * @param spriteBatch Pomocník pri vykresľovaní textúr.
     */
    @Override
    public abstract void render(SpriteBatch spriteBatch);

    /**
     * Aktualizuje subjekt.
     *
     * @param delta Čas v sekundách od posledného rámca.
     */
    @Override
    public abstract void update(float delta);

    /**
     * Slúži na pohyb subjektu.
     */
    public abstract void movement();

    /**
     * Slúži na animáciu subjektu.
     */
    public abstract void animate();
}

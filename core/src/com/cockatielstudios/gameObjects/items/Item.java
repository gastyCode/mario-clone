package com.cockatielstudios.gameObjects.items;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.cockatielstudios.Assets;
import com.cockatielstudios.gameObjects.GameObject;
import com.cockatielstudios.screens.GameScreen;

/**
 * Abstraktná trieda, ktorá dedí z triedy GameObject, potrebná pre tvorbu predmetov, ktoré menia stav hráča.
 */
public abstract class Item extends GameObject {
    private TextureAtlas textureAtlas;
    private int id;

    /**
     * Konštruktor, ktorý nastavuje všetky základné atribúty predmetu.
     *
     * @param screen Inštancia tiredy GameScreen, ktorá vykresľuje samotnú hru.
     * @param position Pozícia predmetu.
     * @param width Šírka predmetu.
     * @param height Výška predmetu.
     * @param id Identifikačné číslo predmetu.
     */
    public Item(GameScreen screen, Vector2 position, float width, float height, int id) {
        super(screen, position, width, height);
        this.textureAtlas = Assets.MANAGER.get(Assets.ITEMS);
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public TextureRegion getTextureRegion(String region) {
        return this.textureAtlas.findRegion(region);
    }

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
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(this.getWidth() / 2);
        circleShape.setPosition(new Vector2(this.getWidth() / 2, this.getHeight() / 2));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0f;

        this.setBody(this.getWorld().createBody(bodyDef));
        this.getBody().createFixture(fixtureDef).setUserData(object);

        circleShape.dispose();
    }
}

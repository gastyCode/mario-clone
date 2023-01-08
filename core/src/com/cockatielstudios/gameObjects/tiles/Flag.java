package com.cockatielstudios.gameObjects.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cockatielstudios.gameObjects.GameObject;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.utils.ObjectName;

/**
 * Trieda, ktorá dedí z triedy GameObject, potrebná na vytvorenie vlajky pre signalizáciu konca levelu.
 */
public class Flag extends GameObject {
    /**
     * Konštruktor, ktorý nastavuje atributy vlajky.
     *
     * @param screen Inštancia tiredy GameScreen, ktorá vykresľuje samotnú hru.
     * @param position Pozícia vlajky.
     * @param width Šírka vlajky.
     * @param height Výška vlajky.
     */
    public Flag(GameScreen screen, Vector2 position, float width, float height) {
        super(screen, position, width, height);
        this.createBody(this.getPosition());
    }

    @Override
    public void render(SpriteBatch spriteBatch) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void dispose() {

    }

    // Táto metóda je inšpirovaná tutoriálom na webe:
    // https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_hello.html
    private void createBody(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.getWidth() / 2, this.getHeight() / 2, new Vector2(getWidth() / 2, getHeight() / 2), 0f);

        this.setBody(this.getWorld().createBody(bodyDef));
        this.getBody().createFixture(polygonShape, 0f).setUserData(ObjectName.FLAG);

        polygonShape.dispose();
    }
}

package com.cockatielstudios.gameObjects.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.utils.Animator;
import com.cockatielstudios.utils.State;
import static com.cockatielstudios.Constants.GOOMBA_SPEED;
import static com.cockatielstudios.Constants.GOOMBA_MAX_FORCE;

/**
 * Trieda nepriateľa, ktorá dedí z triedy Entity, slúži ako prekážka pre hráča.
 */
public class Goomba extends Entity {
    private TextureRegion texture;
    private Animation<TextureRegion> animation;
    private float animationTime;

    private float speed;
    private boolean isDisposed;
    private int id;


    /**
     * Konštruktor, ktorý nastavuje všetky potrebné atribúty nepriateľa.
     *
     * @param screen Inštancia tiredy GameScreen, ktorá vykresľuje samotnú hru.
     * @param position Pozícia nepriateľa.
     * @param width Šírka nepriateľa.
     * @param height Výška nepriateľa.
     * @param id Identifikačné číslo nepriateľa.
     */
    public Goomba(GameScreen screen, Vector2 position, float width, float height, int id) {
        super(screen, position, width, height, State.DEFAULT);
        this.createBody(this.getPosition());
        this.speed = -GOOMBA_SPEED;
        this.isDisposed = false;
        this.id = id;

        this.animation = this.getAnimator().getGoombaWalk();
        this.texture = this.animation.getKeyFrame(0f);
        this.animationTime = 0f;
    }

    public int getID() {
        return this.id;
    }

    public boolean getDisposed() {
        return this.isDisposed;
    }

    public Animator getAnimator() {
        return this.getScreen().getAnimator();
    }

    /**
     * Ak je svet odomknutý a telo nie je zničené, dochádza k jeho zničeniu.
     */
    @Override
    public void dispose() {
        if (!this.isDisposed && !this.getWorld().isLocked()) {
            this.isDisposed = true;
            this.getWorld().destroyBody(this.getBody());
        }
    }

    /**
     * Vykresľuje zvolený rámec animácie.
     *
     * @param spriteBatch Pomocník pri vykresľovaní textúr.
     */
    @Override
    public void render(SpriteBatch spriteBatch) {
        this.texture = this.animation.getKeyFrame(this.animationTime, true);
        spriteBatch.draw(this.texture, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
    }

    /**
     * Dochádza k pohybu nepriateľa, zvoleniu správnej animácie a zarovnaní tela s textúrov.
     *
     * @param delta Čas v sekundách od posledného rámca
     */
    @Override
    public void update(float delta) {
        this.movement();
        this.animationTime += delta;
        this.animate();

        this.setCornerPosition(this.getBody().getPosition());
    }

    /**
     * Aplikuje silu pre pohyb tela do príslušného smeru.
     */
    @Override
    public void movement() {
        if (Math.abs(this.getBody().getLinearVelocity().x) < GOOMBA_MAX_FORCE) {
            this.getBody().applyLinearImpulse(new Vector2(this.speed, 0f), this.getBody().getWorldCenter(), true);
        }
    }

    /**
     * Kontrola príslušných atribútov nepriateľa, podľa ktorých sa následne vyberá potrebná animácia.
     */
    @Override
    public void animate() {
        if (this.isDisposed) {
            this.animation = this.getAnimator().getGoombaDeath();
        } else {
            this.animation = this.getAnimator().getGoombaWalk();
        }
    }

    /**
     * Zmení smer pohybu nepriateľa.
     */
    public void switchDirection() {
        this.speed = -this.speed;
    }

    // Táto metóda je inšpirovaná tutoriálom na webe:
    // https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_hello.html
    private void createBody(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(this.getWidth() / 2);

        this.setBody(this.getWorld().createBody(bodyDef));

        // Create Goomba body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0f;
        this.getBody().createFixture(fixtureDef).setUserData(this);

        circleShape.dispose();
    }
}

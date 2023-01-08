package com.cockatielstudios.gameObjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.cockatielstudios.gameObjects.Fireball;
import com.cockatielstudios.utils.Animator;
import com.cockatielstudios.utils.Facing;
import com.cockatielstudios.utils.ObjectName;
import com.cockatielstudios.utils.State;
import com.cockatielstudios.screens.GameScreen;
import static com.cockatielstudios.Constants.PLAYER_SPEED;
import static com.cockatielstudios.Constants.PLAYER_MAX_FORCE;
import static com.cockatielstudios.Constants.PLAYER_JUMP_FORCE;
import static com.cockatielstudios.Constants.PLAYER_DENSITY;
import static com.cockatielstudios.Constants.ITEM_WIDTH;
import static com.cockatielstudios.Constants.ITEM_HEIGHT;
import static com.cockatielstudios.Constants.PLAYER_RECREATE_TIME;
import static com.cockatielstudios.Constants.PLAYER_BLINK_TIME;
import java.util.ArrayList;

/**
 * Trieda hráča, ktorá dedí z triedy Entity, slúži ako vstup pre užívateľa.
 */
public class Player extends Entity {
    private TextureRegion texture;
    private Animation<TextureRegion> animation;
    private float animationTime;

    private ArrayList<Fireball> fireballs;
    private int fireballID;

    private Facing facing;

    private float timer;
    private float blinkTime;

    private boolean canJump;
    private boolean bodyExists;
    private boolean bodyDestroyed;
    private boolean isMovableToLeft;
    private boolean isBlinking;

    /**
     * Konštruktor, ktorý nastavuje všetky potrebné atribúty hráča.
     *
     * @param screen Inštancia tiredy GameScreen, ktorá vykresľuje samotnú hru.
     * @param position Pozícia hráča.
     * @param width Šírka hráča.
     * @param height Výška hráča.
     */
    public Player(GameScreen screen, Vector2 position, float width, float height) {
        super(screen, position, width, height, State.SMALL);
        this.createBody(this.getPosition());
        this.fireballs = new ArrayList<Fireball>();
        this.fireballID = 0;
        this.facing = Facing.RIGHT;
        this.timer = 0f;
        this.blinkTime = PLAYER_BLINK_TIME;
        this.canJump = false;
        this.bodyExists = true;
        this.bodyDestroyed = false;
        this.isMovableToLeft = true;
        this.isBlinking = false;

        this.animation = this.getAnimator().getSmallWalkRight();
        this.texture = this.animation.getKeyFrame(0f);
        this.animationTime = 0f;
    }

    public Animator getAnimator() {
        return this.getScreen().getAnimator();
    }

    public void setMovableToLeft(boolean movableToLeft) {
        this.isMovableToLeft = movableToLeft;
    }

    /**
     * Vykresľuje zvolený rámec animácie pokiaľ hráč žije.
     *
     * @param spriteBatch Pomocník pri vykresľovaní textúr.
     */
    @Override
    public void render(SpriteBatch spriteBatch) {
        if (this.getState() != State.DEATH) {
            this.texture = this.animation.getKeyFrame(this.animationTime, true);
            if (!this.isBlinking) {
                spriteBatch.draw(this.texture, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
            }
        }

        for (Fireball fireball : this.fireballs) {
            fireball.render(spriteBatch);
        }
    }

    /**
     * Dochádza k pohybu hráča, zvoleniu správnej animácie, kontrole skákania, aktualizácií projektilov, zarovanie tela
     * s textúrov a nakoniec aj pretvoreniu tela pri zvýšení života.
     *
     * @param delta Čas v sekundách od posledného rámca.
     */
    @Override
    public void update(float delta) {
        this.recreateBody(delta);
        this.movement();
        this.animationTime += delta;
        this.animate();
        this.canJump = this.getCollisions().isPlayerGrounded();

        for (Fireball fireball : this.fireballs) {
            fireball.update(delta);
        }
        this.checkFireballs();

        this.setCornerPosition(this.getBody().getPosition());
    }

    /**
     * Ak je svet odomknutý a telo nie je zničené, dochádza k jeho zničeniu.
     */
    @Override
    public void dispose() {
        if (!this.getWorld().isLocked() && !this.bodyDestroyed) {
            this.bodyDestroyed = true;
            this.getWorld().destroyBody(this.getBody());
        }
    }

    /**
     * Pri stlačení jedného z tlačidiel ovládania sa aplikuje sila pre pohyb k príslušnému smeru. V prípade klávesy
     * "medzerník" a kontrole stavu hráča dochádza k hodu projektilu.
     */
    @Override
    public void movement() {
        if (Gdx.input.isKeyPressed(Input.Keys.D) && this.getBody().getLinearVelocity().x <= PLAYER_MAX_FORCE) {
            this.facing = Facing.RIGHT;
            this.getBody().applyLinearImpulse(new Vector2(PLAYER_SPEED, 0), this.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && this.getBody().getLinearVelocity().x >= -PLAYER_MAX_FORCE && this.isMovableToLeft) {
            this.facing = Facing.LEFT;
            this.getBody().applyLinearImpulse(new Vector2(-PLAYER_SPEED, 0), this.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) && this.canJump) {
            this.getBody().applyLinearImpulse(new Vector2(0f, PLAYER_JUMP_FORCE), this.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (this.getState() == State.FLOWER) {
                this.throwFireball();
            }
        }
    }

    /**
     * Kontrola príslušných atribútov hráča, podľa ktorých sa následne vyberá potrebná animácia.
     */
    @Override
    public void animate() {
        switch (this.getState()) {
            case SMALL:
                if (this.getBody().getLinearVelocity().y > 0f || this.getBody().getLinearVelocity().y < 0f) {
                    if (this.facing == Facing.LEFT) {
                        this.animation = this.getAnimator().getSmallJumpLeft();
                    } else {
                        this.animation = this.getAnimator().getSmallJumpRight();
                    }
                } else if (this.getBody().getLinearVelocity().x > 0f || this.getBody().getLinearVelocity().x < 0f) {
                    if (this.facing == Facing.LEFT) {
                        this.animation = this.getAnimator().getSmallWalkLeft();
                    } else {
                        this.animation = this.getAnimator().getSmallWalkRight();
                    }
                } else {
                    if (this.facing == Facing.LEFT) {
                        this.animation = this.getAnimator().getSmallIdleLeft();
                    } else {
                        this.animation = this.getAnimator().getSmallIdleRight();
                    }
                }
                break;
            case BIG:
                if (this.getBody().getLinearVelocity().y > 0f || this.getBody().getLinearVelocity().y < 0f) {
                    if (this.facing == Facing.LEFT) {
                        this.animation = this.getAnimator().getBigJumpLeft();
                    } else {
                        this.animation = this.getAnimator().getBigJumpRight();
                    }
                } else if (this.getBody().getLinearVelocity().x > 0f || this.getBody().getLinearVelocity().x < 0f) {
                    if (this.facing == Facing.LEFT) {
                        this.animation = this.getAnimator().getBigWalkLeft();
                    } else {
                        this.animation = this.getAnimator().getBigWalkRight();
                    }
                } else {
                    if (this.facing == Facing.LEFT) {
                        this.animation = this.getAnimator().getBigIdleLeft();
                    } else {
                        this.animation = this.getAnimator().getBigIdleRight();
                    }
                }
                break;
            case FLOWER:
                if (this.getBody().getLinearVelocity().y > 0f || this.getBody().getLinearVelocity().y < 0f) {
                    if (this.facing == Facing.LEFT) {
                        this.animation = this.getAnimator().getFlowerJumpLeft();
                    } else {
                        this.animation = this.getAnimator().getFlowerJumpRight();
                    }
                } else if (this.getBody().getLinearVelocity().x > 0f || this.getBody().getLinearVelocity().x < 0f) {
                    if (this.facing == Facing.LEFT) {
                        this.animation = this.getAnimator().getFlowerWalkLeft();
                    } else {
                        this.animation = this.getAnimator().getFlowerWalkRight();
                    }
                } else {
                    if (this.facing == Facing.LEFT) {
                        this.animation = this.getAnimator().getFlowerIdleLeft();
                    } else {
                        this.animation = this.getAnimator().getFlowerIdleRight();
                    }
                }
                break;
        }
    }

    private void checkFireballs() {
        int index = -1;
        for (int i = 0; i < this.fireballs.size(); i++) {
            if (this.fireballs.get(i).getID() == this.getCollisions().getCollidedFireballID()) {
                this.fireballs.get(i).dispose();
                index = i;
            }
        }
        if (index >= 0) {
            this.fireballs.remove(index);
        }
    }

    private void createBody(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.getWidth() / 3, this.getHeight() / 2f - 0.01f, new Vector2(0, 0), 0f);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(this.getWidth() / 3);
        circleShape.setPosition(new Vector2(0f, -0.1f));

        this.setBody(this.getWorld().createBody(bodyDef));

        FixtureDef fixtureDef = new FixtureDef();

        // Create player body
        fixtureDef.shape = circleShape;
        fixtureDef.density = PLAYER_DENSITY;
        fixtureDef.friction = 1.2f;
        this.getBody().createFixture(fixtureDef).setUserData(this);

        // Create player bottom sensor
        polygonShape.setAsBox(this.getWidth() / 4f, 0.02f, new Vector2(0f, -this.getHeight() / 1.8f), 0f);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        this.getBody().createFixture(fixtureDef).setUserData(ObjectName.PLAYER_BOTTOM);

        if (this.getState() != State.SMALL) {
            // Create player extended body
            polygonShape.setAsBox(this.getWidth() / 4f, 0.1f, new Vector2(0f, this.getHeight() / 6f), 0f);
            fixtureDef.shape = polygonShape;
            fixtureDef.isSensor = false;
            this.getBody().createFixture(fixtureDef).setUserData(this);

            // Create player top sensor
            polygonShape.setAsBox(this.getWidth() / 12f, 0.01f, new Vector2(0f, this.getHeight() / 1.8f), 0f);
        } else {
            // Create player top sensor
            polygonShape.setAsBox(this.getWidth() / 12f, 0.01f, new Vector2(0f, this.getHeight() / 30f), 0f);
        }
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        this.getBody().createFixture(fixtureDef).setUserData(ObjectName.PLAYER_TOP);

        polygonShape.dispose();
        circleShape.dispose();
    }

    private void throwFireball() {
        if (this.facing == Facing.RIGHT) {
            this.fireballs.add(new Fireball(this.getScreen(), this.getPosition(), ITEM_WIDTH, ITEM_HEIGHT, Facing.RIGHT, this.getAvailableFireballID()));
        } else {
            this.fireballs.add(new Fireball(this.getScreen(), this.getPosition(), ITEM_WIDTH, ITEM_HEIGHT, Facing.LEFT, this.getAvailableFireballID()));
        }
    }

    private int getAvailableFireballID() {
        this.fireballID++;
        return this.fireballID;
    }

    /**
     * Ak existuje telo hráča, zmeň stav o stupeň nižšie a vymaž telo alebo zmeň stav na smrť.
     */
    public void takeDamage() {
        if (this.bodyExists) {
            switch (this.getState()) {
                case FLOWER:
                    this.bodyExists = false;
                    this.setState(State.BIG);
                    break;
                case BIG:
                    this.bodyExists = false;
                    this.setState(State.SMALL);
                    break;
                case SMALL:
                    this.setState(State.DEATH);
                    break;
            }
        }
    }

    /**
     * Ak existuje telo hráča, zmeň stav o stupeň vyššie a vymaž telo hráča.
     */
    public void powerUp() {
        if (this.bodyExists) {
            switch (this.getState()) {
                case SMALL:
                    this.setState(State.BIG);
                    break;
                case BIG:
                    this.setState(State.FLOWER);
                    break;
            }
            this.bodyExists = false;
        }
    }

    // Táto metóda je inšpirovaná tutoriálom na webe:
    // https://box2d.org/documentation/md__d_1__git_hub_box2d_docs_hello.html
    private void recreateBody(float delta) {
        if (!this.getWorld().isLocked() && !this.bodyExists && !this.bodyDestroyed) {
            this.getWorld().destroyBody(this.getBody());
            this.createBody(this.getPosition());
            this.getBody().setAwake(false);
            this.bodyDestroyed = true;
        } else if (!this.bodyExists && this.bodyDestroyed) {
            this.timer += delta;
            if (this.timer >= this.blinkTime) {
                this.blinkTime += PLAYER_BLINK_TIME;
                this.isBlinking = !this.isBlinking;
            }
            if (this.timer >= PLAYER_RECREATE_TIME) {
                this.isBlinking = false;
                this.timer = 0f;
                this.blinkTime = PLAYER_BLINK_TIME;
                this.bodyExists = true;
                this.bodyDestroyed = false;
                this.getBody().setAwake(true);
            }
        }
    }
}

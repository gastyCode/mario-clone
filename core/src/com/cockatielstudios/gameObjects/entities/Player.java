package com.cockatielstudios.gameObjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.cockatielstudios.gameObjects.Fireball;
import com.cockatielstudios.utils.Animator;
import com.cockatielstudios.utils.Facing;
import com.cockatielstudios.utils.ObjectName;
import com.cockatielstudios.utils.State;
import com.cockatielstudios.screens.GameScreen;

import static com.cockatielstudios.Constants.*;

import java.util.ArrayList;

public class Player extends Entity{
    private TextureRegion texture;
    private Animation<TextureRegion> animation;
    private float animationTime;

    private ArrayList<Fireball> fireballs;

    private int fireballID;

    private Facing facing;

    private boolean canJump;
    private boolean bodyExists;
    private boolean isMovableToLeft;

    public Player(GameScreen screen, Vector2 position, float width, float height) {
        super(screen, position, width, height, State.DEFAULT);
        this.createBody(this.getPosition());
        this.setState(State.SMALL);
        this.fireballs = new ArrayList<Fireball>();
        this.fireballID = 0;
        this.facing = Facing.RIGHT;
        this.canJump = false;
        this.bodyExists = true;
        this.isMovableToLeft = true;

        this.animation = this.getAnimator().getSmallWalkRight();
        this.texture = this.animation.getKeyFrame(0f);
        this.animationTime = 0f;
    }

    public Animator getAnimator() {
        return this.getScreen().getAnimator();
    }

    public void setMovableToLeft(boolean movableToLeft) {
        isMovableToLeft = movableToLeft;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        this.texture = this.animation.getKeyFrame(this.animationTime, true);
        spriteBatch.draw(this.texture, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
        for (Fireball fireball : this.fireballs) {
            fireball.render(spriteBatch);
        }
    }

    @Override
    public void update(float delta) {
//        this.recreateBody();
        this.movement();
        this.animationTime += delta;
        this.animate();
        this.canJump = this.getCollisions().isPlayerGrounded();
        this.checkStateChange(this.getCollisions().getStateChange());

        if (this.getCollisions().isPlayerFellOut()) {
            this.dispose();
        }

        for (Fireball fireball : this.fireballs) {
            fireball.update(delta);
        }
        this.checkFireballs();

        this.setCornerPosition(this.getBody().getPosition());
    }

    @Override
    public void dispose() {

    }

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
        if (Gdx.input.isKeyPressed(Input.Keys.W) && this.canJump) {
            this.getBody().applyLinearImpulse(new Vector2(0f, PLAYER_JUMP_FORCE), this.getBody().getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (this.getState() == State.FLOWER) {
                this.throwFireball();
            }
        }
    }

    @Override
    public void animate() {
        switch (this.getState()) {
            case SMALL:
                if (this.getBody().getLinearVelocity().y > 0f) {
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
                if (this.getBody().getLinearVelocity().y > 0f) {
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
                if (this.getBody().getLinearVelocity().y > 0f) {
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

    public void checkFireballs() {
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

        // Create player body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = PLAYER_DENSITY;
        fixtureDef.friction = 1.2f;
        this.getBody().createFixture(fixtureDef).setUserData(this);

        polygonShape.setAsBox(this.getWidth() / 4, 0.1f, new Vector2(0f, this.getHeight() / 6), 0f);
        fixtureDef.shape = polygonShape;
        this.getBody().createFixture(fixtureDef);

        // Create player bottom sensor
        polygonShape.setAsBox(this.getWidth() / 10, 0.01f, new Vector2(0f, -this.getHeight() / 2), 0f);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        this.getBody().createFixture(fixtureDef).setUserData(ObjectName.PLAYER_BOTTOM);

        // Create player top sensor
        polygonShape.setAsBox(this.getWidth() / 10, 0.02f, new Vector2(0f, this.getHeight() / 1.8f), 0f);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        this.getBody().createFixture(fixtureDef).setUserData(ObjectName.PLAYER_TOP);

        polygonShape.dispose();
        circleShape.dispose();
    }

    public void throwFireball() {
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

    public void takeDamage() {
        if (this.bodyExists) {
            switch (this.getState()) {
                case FLOWER:
                    this.setState(State.BIG);
                    break;
                case BIG:
                    this.setState(State.SMALL);
                    break;
                case SMALL:
                    this.dispose();
                    break;
            }
            this.bodyExists = false;
        }
    }

    private void recreateBody() {
        if (!this.getWorld().isLocked() && !this.bodyExists) {
            this.bodyExists = true;
            this.getWorld().destroyBody(this.getBody());
            this.createBody(this.getPosition());
        }
    }

    private void checkStateChange(State stateToCheck) {
        if (this.getState() != stateToCheck && stateToCheck != null) {
            this.setState(stateToCheck);
        }
    }
}

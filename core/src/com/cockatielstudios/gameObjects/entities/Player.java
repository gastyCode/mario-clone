package com.cockatielstudios.gameObjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cockatielstudios.Assets;
import com.cockatielstudios.utils.State;
import com.cockatielstudios.screens.GameScreen;

import static com.cockatielstudios.Constants.*;

import java.util.ArrayList;

public class Player extends Entity{
    private TextureRegion playerIdle;
    private TextureRegion playerWalk;
    private TextureRegion playerJump;
    private TextureRegion bigPlayerIdle;
    private TextureRegion bigPlayerWalk;
    private TextureRegion bigPlayerJump;
    private ArrayList<Fireball> fireballs;

    private boolean canJump;

    private Texture test;

    public Player(GameScreen screen, Vector2 position, float width, float height) {
        super(screen, position, width, height);
        this.setState(State.SMALL);
        this.createBody(this.getPosition());

        this.canJump = false;

        this.test = Assets.manager.get(Assets.player);
    }

    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.test, this.getPosition().x, this.getPosition().y, this.getWidth(), this.getHeight());
    }

    @Override
    public void update(float delta) {
        this.movement();
        this.canJump = this.getCollisions().isPlayerGrounded();
        if (this.getCollisions().isPlayerFellOut()) {
            this.destroy();
        }

        this.setCornerPosition(this.body.getPosition());
    }

    @Override
    public void dispose() {
        this.test.dispose();
    }

    @Override
    public void movement() {
        if (Gdx.input.isKeyPressed(Input.Keys.D) && this.body.getLinearVelocity().x <= PLAYER_MAX_FORCE) {
            this.body.applyLinearImpulse(new Vector2(PLAYER_SPEED, 0), this.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && this.body.getLinearVelocity().x >= -PLAYER_MAX_FORCE) {
            this.body.applyLinearImpulse(new Vector2(-PLAYER_SPEED, 0), this.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && this.canJump) {
            this.body.applyLinearImpulse(new Vector2(0f, PLAYER_JUMP_FORCE), this.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {

        }
    }

    @Override
    public void animate() {

    }

    private void createBody(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = position.x;
        bodyDef.position.y = position.y;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(this.getWidth() / 2, this.getHeight() / 2, new Vector2(0, 0.02f), 0f);

        this.body = this.getWorld().createBody(bodyDef);

        // Create player body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = PLAYER_DENSITY;
        fixtureDef.friction = 1.2f;
        this.body.createFixture(fixtureDef).setUserData("player");

        // Create player bottom sensor
        polygonShape.setAsBox(this.getWidth() / 4, this.getHeight() / 12, new Vector2(0, -this.getHeight() / 2), 0f);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        this.body.createFixture(fixtureDef).setUserData("playerBottom");

        // Create player top sensor
        polygonShape.setAsBox(this.getWidth() / 4, this.getHeight() / 12, new Vector2(0, this.getHeight() / 1.8f), 0f);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;
        this.body.createFixture(fixtureDef).setUserData("playerTop");

        polygonShape.dispose();
    }

    public void throwFireball() {

    }

    public void growUp() {
        setState(State.BIG);
    }

    public void takeDamage() {

    }

    public void destroy() {
        this.dispose();
    }

    public void giveDamage(Entity entity) {

    }
}

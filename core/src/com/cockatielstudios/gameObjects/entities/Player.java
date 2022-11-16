package com.cockatielstudios.gameObjects.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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

    public Player(GameScreen screen, Vector2 position, float width, float height) {
        super(screen, position, width, height);
        this.setState(State.SMALL);
        this.setSpeed(PLAYER_SPEED);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Texture player = Assets.manager.get(Assets.player);
        spriteBatch.draw(player, this.getPosition().x, this.getPosition().y);
        player.dispose();
    }

    @Override
    public void update(float delta) {
        this.movement();

        this.setPosition(this.body.getPosition());
    }

    @Override
    public void dispose() {

    }

    @Override
    public void movement() {
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.body.applyLinearImpulse(new Vector2(1f, 0), this.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.body.applyLinearImpulse(new Vector2(-1f, 0), this.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.body.applyLinearImpulse(new Vector2(0f, 3f), this.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {

        }
    }

    @Override
    public void animate() {

    }

    public void jump() {

    }

    public void throwFireball() {

    }

    public void growUp() {
        setState(State.BIG);
    }

    public void takeDamage() {

    }

    public void giveDamage(Entity entity) {

    }
}

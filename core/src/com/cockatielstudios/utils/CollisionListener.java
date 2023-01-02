package com.cockatielstudios.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.cockatielstudios.gameObjects.Fireball;
import com.cockatielstudios.gameObjects.entities.Goomba;
import com.cockatielstudios.gameObjects.entities.Player;
import com.cockatielstudios.gameObjects.items.Flower;
import com.cockatielstudios.gameObjects.items.Mushroom;
import com.cockatielstudios.gameObjects.tiles.Block;
import com.cockatielstudios.gameObjects.tiles.MysteryBlock;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.screens.Hud;
import com.sun.org.apache.xalan.internal.xsltc.dom.NthIterator;

import static com.cockatielstudios.Constants.*;

public class CollisionListener implements ContactListener {
    private GameScreen screen;
    private Hud hud;

    private boolean playerGrounded;
    private boolean playerFellOut;
    private int collidedBlockID;
    private int collidedMysteryBlockID;
    private int collidedMushroomID;
    private int collidedFlowerID;
    private int collidedGoombaID;
    private int collidedFireballID;
    private State stateChange;

    public CollisionListener(GameScreen screen, Hud hud) {
        this.screen = screen;
        this.hud = hud;
    }

    public boolean isPlayerGrounded() {
        return playerGrounded;
    }

    public int getCollidedMysteryBlockID() {
        return collidedMysteryBlockID;
    }

    public int getCollidedBlockID() {
        return collidedBlockID;
    }

    public int getCollidedMushroomID() {
        return collidedMushroomID;
    }

    public int getCollidedFlowerID() {
        return collidedFlowerID;
    }

    public int getCollidedGoombaID() {
        return collidedGoombaID;
    }

    public int getCollidedFireballID() {
        return collidedFireballID;
    }

    public boolean isPlayerFellOut() {
        return playerFellOut;
    }

    public State getStateChange() {
        return stateChange;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == ObjectName.PLAYER_BOTTOM || fixtureB.getUserData() == ObjectName.PLAYER_BOTTOM) {
            this.playerGrounded = true;
        }

        if (fixtureA.getUserData() == ObjectName.PLAYER_TOP || fixtureB.getUserData() == ObjectName.PLAYER_TOP &&
                fixtureA.getUserData() instanceof Block || fixtureB.getUserData() instanceof Block) {

            if (this.screen.getPlayerState() != State.SMALL) {
                Block block;
                if (fixtureA.getUserData() instanceof Block) {
                    block = (Block) fixtureA.getUserData();
                } else {
                    block = (Block) fixtureB.getUserData();
                }
                this.collidedBlockID = block.getID();
                this.hud.addScore(BREAK_SCORE);
            }
        }

        if ((fixtureA.getUserData() == ObjectName.PLAYER_TOP || fixtureB.getUserData() == ObjectName.PLAYER_TOP) &&
                (fixtureA.getUserData() instanceof MysteryBlock || fixtureB.getUserData() instanceof MysteryBlock)) {

            MysteryBlock mysteryBlock;
            if (fixtureA.getUserData() instanceof MysteryBlock) {
                mysteryBlock = (MysteryBlock) fixtureA.getUserData();
            } else {
                mysteryBlock = (MysteryBlock) fixtureB.getUserData();
            }
            this.collidedMysteryBlockID = mysteryBlock.getID();
        }

        if ((fixtureA.getUserData() == ObjectName.PLAYER_BOTTOM || fixtureA.getUserData() == ObjectName.PLAYER_BOTTOM) &&
                (fixtureA.getUserData() == ObjectName.FALL_DETECTOR || fixtureA.getUserData() == ObjectName.FALL_DETECTOR)) {
            this.playerFellOut = true;
        }

        if ((fixtureA.getUserData() instanceof Mushroom || fixtureB.getUserData() instanceof Mushroom) &&
                (fixtureA.getUserData() == ObjectName.PIPE || fixtureB.getUserData() == ObjectName.PIPE)) {

            Mushroom mushroom;
            if (fixtureA.getUserData() instanceof Mushroom) {
                mushroom = (Mushroom) fixtureA.getUserData();
            } else {
                mushroom = (Mushroom) fixtureB.getUserData();
            }
            mushroom.switchDirection();
        }

        if ((fixtureA.getUserData() instanceof Goomba || fixtureB.getUserData() instanceof Goomba) &&
                (fixtureA.getUserData() == ObjectName.PIPE || fixtureB.getUserData() == ObjectName.PIPE)) {

            Goomba goomba;
            if (fixtureA.getUserData() instanceof Goomba) {
                goomba = (Goomba) fixtureA.getUserData();
            } else {
                goomba = (Goomba) fixtureB.getUserData();
            }
            goomba.switchDirection();
        }

        if ((fixtureA.getUserData() instanceof Mushroom || fixtureB.getUserData() instanceof Mushroom) &&
                (fixtureA.getUserData() instanceof Player || fixtureB.getUserData() instanceof Player)) {

            Mushroom mushroom;
            if (fixtureA.getUserData() instanceof Mushroom) {
                mushroom = (Mushroom) fixtureA.getUserData();
            } else {
                mushroom = (Mushroom) fixtureB.getUserData();
            }
            this.collidedMushroomID = mushroom.getID();
            this.stateChange = mushroom.getStateChange();
            this.hud.addScore(POWER_UP_SCORE);
        }

        if ((fixtureA.getUserData() instanceof Flower || fixtureB.getUserData() instanceof Flower) &&
                (fixtureA.getUserData() instanceof Player || fixtureB.getUserData() instanceof Player)) {

            Flower flower;
            if (fixtureA.getUserData() instanceof Flower) {
                flower = (Flower) fixtureA.getUserData();
            } else {
                flower = (Flower) fixtureB.getUserData();
            }
            this.collidedFlowerID = flower.getID();
            this.stateChange = flower.getStateChange();
            this.hud.addScore(POWER_UP_SCORE);
        }

        if ((fixtureA.getUserData() instanceof Goomba || fixtureB.getUserData() instanceof Goomba) &&
                (fixtureA.getUserData() instanceof Player || fixtureB.getUserData() instanceof Player)) {

            Goomba goomba;
            Player player;
            if (fixtureA.getUserData() instanceof Goomba) {
                goomba = (Goomba) fixtureA.getUserData();
                player = (Player) fixtureB.getUserData();
            } else {
                goomba = (Goomba) fixtureB.getUserData();
                player = (Player) fixtureA.getUserData();
            }
            if (!goomba.getDisposed()) {
                goomba.switchDirection();
                player.takeDamage();
            }
        }

        if ((fixtureA.getUserData() instanceof Goomba || fixtureB.getUserData() instanceof Goomba) &&
                (fixtureA.getUserData() == ObjectName.PLAYER_BOTTOM || fixtureB.getUserData() == ObjectName.PLAYER_BOTTOM)) {

            Goomba goomba;
            if (fixtureA.getUserData() instanceof Goomba) {
                goomba = (Goomba) fixtureA.getUserData();
            } else {
                goomba = (Goomba) fixtureB.getUserData();
            }
            this.collidedGoombaID = goomba.getID();
            this.hud.addScore(GOOMBA_SCORE);
        }

        if ((fixtureA.getUserData() instanceof Fireball || fixtureB.getUserData() instanceof Fireball) &&
                (fixtureA.getUserData() == ObjectName.GROUND || fixtureB.getUserData() == ObjectName.GROUND)) {
            Fireball fireball;
            if (fixtureA.getUserData() instanceof Fireball) {
                fireball = (Fireball) fixtureA.getUserData();
            } else {
                fireball = (Fireball) fixtureB.getUserData();
            }
            fireball.bounce();
        }

        if ((fixtureA.getUserData() instanceof Fireball || fixtureB.getUserData() instanceof Fireball) &&
                (fixtureA.getUserData() == ObjectName.PIPE || fixtureB.getUserData() == ObjectName.PIPE)) {
            Fireball fireball;
            if (fixtureA.getUserData() instanceof Fireball) {
                fireball = (Fireball) fixtureA.getUserData();
            } else {
                fireball = (Fireball) fixtureB.getUserData();
            }
            this.collidedFireballID = fireball.getID();
        }

        if ((fixtureA.getUserData() instanceof Fireball || fixtureB.getUserData() instanceof Fireball) &&
                (fixtureA.getUserData() instanceof Goomba || fixtureB.getUserData() instanceof Goomba)) {
            Fireball fireball;
            Goomba goomba;
            if (fixtureA.getUserData() instanceof Fireball) {
                fireball = (Fireball) fixtureA.getUserData();
                goomba = (Goomba) fixtureB.getUserData();
            } else {
                fireball = (Fireball) fixtureB.getUserData();
                goomba = (Goomba) fixtureA.getUserData();
            }
            this.collidedGoombaID = goomba.getID();
            this.collidedFireballID = fireball.getID();
            this.hud.addScore(GOOMBA_SCORE);
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == ObjectName.PLAYER_BOTTOM || fixtureB.getUserData() == ObjectName.PLAYER_BOTTOM) {
            this.playerGrounded = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

package com.cockatielstudios.utils;

import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.cockatielstudios.gameObjects.Fireball;
import com.cockatielstudios.gameObjects.entities.Goomba;
import com.cockatielstudios.gameObjects.entities.Player;
import com.cockatielstudios.gameObjects.items.Flower;
import com.cockatielstudios.gameObjects.items.Mushroom;
import com.cockatielstudios.gameObjects.tiles.Block;
import com.cockatielstudios.gameObjects.tiles.DestroyableBlock;
import com.cockatielstudios.gameObjects.tiles.MysteryBlock;
import com.cockatielstudios.screens.GameScreen;
import com.cockatielstudios.screens.Hud;
import static com.cockatielstudios.Constants.BREAK_SCORE;
import static com.cockatielstudios.Constants.GOOMBA_SCORE;
import static com.cockatielstudios.Constants.POWER_UP_SCORE;

public class CollisionListener implements ContactListener {
    private GameScreen screen;
    private Hud hud;

    private boolean playerGrounded;
    private int collidedBlockID;
    private int collidedMysteryBlockID;
    private int collidedMushroomID;
    private int collidedFlowerID;
    private int collidedGoombaID;
    private int collidedFireballID;

    public CollisionListener(GameScreen screen, Hud hud) {
        this.screen = screen;
        this.hud = hud;
    }

    public boolean isPlayerGrounded() {
        return this.playerGrounded;
    }

    public int getCollidedMysteryBlockID() {
        return this.collidedMysteryBlockID;
    }

    public int getCollidedBlockID() {
        return this.collidedBlockID;
    }

    public int getCollidedMushroomID() {
        return this.collidedMushroomID;
    }

    public int getCollidedFlowerID() {
        return this.collidedFlowerID;
    }

    public int getCollidedGoombaID() {
        return this.collidedGoombaID;
    }

    public int getCollidedFireballID() {
        return this.collidedFireballID;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() == ObjectName.PLAYER_BOTTOM || fixtureB.getUserData() == ObjectName.PLAYER_BOTTOM) {
            this.playerGrounded = true;
        }

        if ((fixtureA.getUserData() instanceof Fireball || fixtureB.getUserData() instanceof Fireball) &&
                (fixtureA.getUserData() == ObjectName.PIPE || fixtureB.getUserData() == ObjectName.PIPE)) {
            Fireball fireball;
            if (fixtureA.getUserData() instanceof Fireball) {
                fireball = (Fireball)fixtureA.getUserData();
            } else {
                fireball = (Fireball)fixtureB.getUserData();
            }
            this.collidedFireballID = fireball.getID();
        }

        if ((fixtureA.getUserData() instanceof Fireball || fixtureB.getUserData() instanceof Fireball) &&
                (fixtureA.getUserData() instanceof DestroyableBlock || fixtureB.getUserData() instanceof DestroyableBlock)) {
            Fireball fireball;
            if (fixtureA.getUserData() instanceof Fireball) {
                fireball = (Fireball)fixtureA.getUserData();
            } else {
                fireball = (Fireball)fixtureB.getUserData();
            }
            this.collidedFireballID = fireball.getID();
        }

        if ((fixtureA.getUserData() == ObjectName.PLAYER_TOP || fixtureB.getUserData() == ObjectName.PLAYER_TOP) &&
                (fixtureA.getUserData() instanceof Block || fixtureB.getUserData() instanceof Block)) {

            if (this.screen.getPlayerState() != State.SMALL) {
                Block block;
                if (fixtureA.getUserData() instanceof Block) {
                    block = (Block)fixtureA.getUserData();
                } else {
                    block = (Block)fixtureB.getUserData();
                }
                this.collidedBlockID = block.getID();
                this.hud.addScore(BREAK_SCORE);
            }
        }

        if ((fixtureA.getUserData() == ObjectName.PLAYER_TOP || fixtureB.getUserData() == ObjectName.PLAYER_TOP) &&
                (fixtureA.getUserData() instanceof MysteryBlock || fixtureB.getUserData() instanceof MysteryBlock)) {

            MysteryBlock mysteryBlock;
            if (fixtureA.getUserData() instanceof MysteryBlock) {
                mysteryBlock = (MysteryBlock)fixtureA.getUserData();
            } else {
                mysteryBlock = (MysteryBlock)fixtureB.getUserData();
            }
            this.collidedMysteryBlockID = mysteryBlock.getID();
        }

        if ((fixtureA.getUserData() instanceof Player || fixtureB.getUserData() instanceof Player) &&
                (fixtureA.getUserData() == ObjectName.FALL_DETECTOR || fixtureB.getUserData() == ObjectName.FALL_DETECTOR)) {

            Player player;
            if (fixtureA.getUserData() instanceof Player) {
                player = (Player)fixtureA.getUserData();
            } else {
                player = (Player)fixtureB.getUserData();
            }
            player.setState(State.DEATH);
        }

        if ((fixtureA.getUserData() instanceof Mushroom || fixtureB.getUserData() instanceof Mushroom) &&
                (fixtureA.getUserData() == ObjectName.PIPE || fixtureB.getUserData() == ObjectName.PIPE)) {

            Mushroom mushroom;
            if (fixtureA.getUserData() instanceof Mushroom) {
                mushroom = (Mushroom)fixtureA.getUserData();
            } else {
                mushroom = (Mushroom)fixtureB.getUserData();
            }
            mushroom.switchDirection();
        }

        if ((fixtureA.getUserData() instanceof Goomba || fixtureB.getUserData() instanceof Goomba) &&
                (fixtureA.getUserData() == ObjectName.PIPE || fixtureB.getUserData() == ObjectName.PIPE)) {

            Goomba goomba;
            if (fixtureA.getUserData() instanceof Goomba) {
                goomba = (Goomba)fixtureA.getUserData();
            } else {
                goomba = (Goomba)fixtureB.getUserData();
            }
            goomba.switchDirection();
        }

        if ((fixtureA.getUserData() instanceof Mushroom || fixtureB.getUserData() instanceof Mushroom) &&
                (fixtureA.getUserData() instanceof Player || fixtureB.getUserData() instanceof Player)) {

            Mushroom mushroom;
            Player player;
            if (fixtureA.getUserData() instanceof Mushroom) {
                mushroom = (Mushroom)fixtureA.getUserData();
                player = (Player)fixtureB.getUserData();
            } else {
                mushroom = (Mushroom)fixtureB.getUserData();
                player = (Player)fixtureA.getUserData();
            }
            this.collidedMushroomID = mushroom.getID();
            player.powerUp();
            this.hud.addScore(POWER_UP_SCORE);
        }

        if ((fixtureA.getUserData() instanceof Flower || fixtureB.getUserData() instanceof Flower) &&
                (fixtureA.getUserData() instanceof Player || fixtureB.getUserData() instanceof Player)) {

            Flower flower;
            Player player;
            if (fixtureA.getUserData() instanceof Flower) {
                flower = (Flower)fixtureA.getUserData();
                player = (Player)fixtureB.getUserData();
            } else {
                flower = (Flower)fixtureB.getUserData();
                player = (Player)fixtureA.getUserData();
            }
            this.collidedFlowerID = flower.getID();
            player.powerUp();
            this.hud.addScore(POWER_UP_SCORE);
        }

        if ((fixtureA.getUserData() instanceof Goomba || fixtureB.getUserData() instanceof Goomba) &&
                (fixtureA.getUserData() instanceof Player || fixtureB.getUserData() instanceof Player)) {

            Goomba goomba;
            Player player;
            if (fixtureA.getUserData() instanceof Goomba) {
                goomba = (Goomba)fixtureA.getUserData();
                player = (Player)fixtureB.getUserData();
            } else {
                goomba = (Goomba)fixtureB.getUserData();
                player = (Player)fixtureA.getUserData();
            }
            if (!goomba.getDisposed()) {
                player.takeDamage();
            }
        }

        if ((fixtureA.getUserData() instanceof Goomba || fixtureB.getUserData() instanceof Goomba) &&
                (fixtureA.getUserData() == ObjectName.PLAYER_BOTTOM || fixtureB.getUserData() == ObjectName.PLAYER_BOTTOM)) {

            Goomba goomba;
            if (fixtureA.getUserData() instanceof Goomba) {
                goomba = (Goomba)fixtureA.getUserData();
            } else {
                goomba = (Goomba)fixtureB.getUserData();
            }
            this.collidedGoombaID = goomba.getID();
            this.hud.addScore(GOOMBA_SCORE);
        }

        if ((fixtureA.getUserData() instanceof Fireball || fixtureB.getUserData() instanceof Fireball) &&
                (fixtureA.getUserData() == ObjectName.GROUND || fixtureB.getUserData() == ObjectName.GROUND)) {
            Fireball fireball;
            if (fixtureA.getUserData() instanceof Fireball) {
                fireball = (Fireball)fixtureA.getUserData();
            } else {
                fireball = (Fireball)fixtureB.getUserData();
            }
            fireball.bounce();
        }

        if ((fixtureA.getUserData() instanceof Fireball || fixtureB.getUserData() instanceof Fireball) &&
                (fixtureA.getUserData() instanceof Goomba || fixtureB.getUserData() instanceof Goomba)) {
            Fireball fireball;
            Goomba goomba;
            if (fixtureA.getUserData() instanceof Fireball) {
                fireball = (Fireball)fixtureA.getUserData();
                goomba = (Goomba)fixtureB.getUserData();
            } else {
                fireball = (Fireball)fixtureB.getUserData();
                goomba = (Goomba)fixtureA.getUserData();
            }
            this.collidedGoombaID = goomba.getID();
            this.collidedFireballID = fireball.getID();
            this.hud.addScore(GOOMBA_SCORE);
        }

        if ((fixtureA.getUserData() instanceof Player || fixtureB.getUserData() instanceof Player) &&
                (fixtureA.getUserData() == ObjectName.FLAG || fixtureB.getUserData() == ObjectName.FLAG)) {
            this.screen.setWin(true);
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

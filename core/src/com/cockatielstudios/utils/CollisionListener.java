package com.cockatielstudios.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.cockatielstudios.gameObjects.items.Item;
import com.cockatielstudios.gameObjects.items.Mushroom;
import com.cockatielstudios.gameObjects.tiles.Block;
import com.cockatielstudios.gameObjects.tiles.MysteryBlock;
import com.cockatielstudios.utils.ObjectName;

public class CollisionListener implements ContactListener {
    private boolean playerGrounded;
    private boolean playerFellOut;
    private int collidedBlockID;
    private int collidedMysteryBlockID;
    private int collidedMushroomID;
    private State stateChange;

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

            Block block = null;
            if (fixtureA.getUserData() instanceof Block) {
                block = (Block) fixtureA.getUserData();
            } else if (fixtureB.getUserData() instanceof Block) {
                block = (Block) fixtureB.getUserData();
            }
            assert block != null;
            this.collidedBlockID = block.getID();
        }

        if ((fixtureA.getUserData() == ObjectName.PLAYER_TOP || fixtureB.getUserData() == ObjectName.PLAYER_TOP) &&
                (fixtureA.getUserData() instanceof MysteryBlock || fixtureB.getUserData() instanceof MysteryBlock)) {

            MysteryBlock mysteryBlock = null;
            if (fixtureA.getUserData() instanceof MysteryBlock) {
                mysteryBlock = (MysteryBlock) fixtureA.getUserData();
            } else if (fixtureB.getUserData() instanceof MysteryBlock) {
                mysteryBlock = (MysteryBlock) fixtureB.getUserData();
            }
            assert mysteryBlock != null;
            this.collidedMysteryBlockID = mysteryBlock.getID();
        }
        if ((fixtureA.getUserData() == ObjectName.PLAYER_BOTTOM || fixtureA.getUserData() == ObjectName.PLAYER_BOTTOM) &&
                (fixtureA.getUserData() == ObjectName.FALL_DETECTOR || fixtureA.getUserData() == ObjectName.FALL_DETECTOR)) {
            this.playerFellOut = true;
        }

        if ((fixtureA.getUserData() instanceof Mushroom || fixtureB.getUserData() instanceof Mushroom) &&
                (fixtureA.getUserData() == ObjectName.PIPE || fixtureB.getUserData() == ObjectName.PIPE)) {

            Mushroom mushroom = null;
            if (fixtureA.getUserData() instanceof Mushroom) {
                mushroom = (Mushroom) fixtureA.getUserData();
            } else if (fixtureB.getUserData() instanceof Mushroom) {
                mushroom = (Mushroom) fixtureB.getUserData();
            }
            assert mushroom != null;
            mushroom.switchDirection();
        }

        if ((fixtureA.getUserData() instanceof Item || fixtureB.getUserData() instanceof Item) &&
                (fixtureA.getUserData() == ObjectName.PLAYER || fixtureB.getUserData() == ObjectName.PLAYER)) {

            Item item = null;
            if (fixtureA.getUserData() instanceof Mushroom) {
                item = (Item) fixtureA.getUserData();
            } else if (fixtureB.getUserData() instanceof Mushroom) {
                item = (Item) fixtureB.getUserData();
            }
            assert item != null;
            this.collidedMushroomID = item.getID();
            this.stateChange = item.getStateChange();
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

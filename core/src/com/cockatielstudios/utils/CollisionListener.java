package com.cockatielstudios.utils;

import com.badlogic.gdx.physics.box2d.*;
import com.cockatielstudios.gameObjects.tiles.Block;

public class CollisionListener implements ContactListener {
    private boolean playerGrounded;
    private boolean playerFellOut;
    private int collidedBlockID;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData().equals("playerBottom") || fixtureB.getUserData().equals("playerBottom")) {
            this.playerGrounded = true;
        }

        if (fixtureA.getUserData().equals("playerTop") || fixtureB.getUserData().equals("playerTop") &&
                fixtureA.getUserData() instanceof Block || fixtureB.getUserData() instanceof Block) {

            Block block = null;
            if (fixtureA.getUserData() instanceof Block) {
                block = (Block) fixtureA.getUserData();
            } else if (fixtureB.getUserData() instanceof Block) {
                block = (Block) fixtureB.getUserData();
            }
            assert block != null;
            this.collidedBlockID = block.getId();
        }

        if (fixtureA.getUserData().equals("playerBottom") || fixtureA.getUserData().equals("playerBottom") &&
                fixtureA.getUserData().equals("fall") || fixtureA.getUserData().equals("fall")) {
            this.playerFellOut = true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData().equals("playerBottom") || fixtureB.getUserData().equals("playerBottom")) {
            this.playerGrounded = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerGrounded() {
        return playerGrounded;
    }

    public int getCollidedBlockID() {
        return collidedBlockID;
    }

    public boolean isPlayerFellOut() {
        return playerFellOut;
    }
}

package com.cockatielstudios.utils;

import com.badlogic.gdx.physics.box2d.*;

public class CollisionListener implements ContactListener {
    private boolean isPlayerGrounded;

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData().equals("playerBottom") || fixtureB.getUserData().equals("playerBottom")) {
            isPlayerGrounded = true;
        }

        if ((fixtureA.getUserData().equals("playerTop") || fixtureA.getUserData().equals("playerTop")) &&
                (fixtureA.getUserData().equals("block") || fixtureA.getUserData().equals("block"))) {
            System.out.println("head");
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData().equals("playerBottom") || fixtureB.getUserData().equals("playerBottom")) {
            isPlayerGrounded = false;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public boolean isPlayerGrounded() {
        return isPlayerGrounded;
    }
}

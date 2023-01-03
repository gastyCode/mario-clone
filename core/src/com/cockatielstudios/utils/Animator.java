package com.cockatielstudios.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cockatielstudios.Assets;

public class Animator {
    // Player animation
    private Texture playerSheet;
    private static final int PLAYER_ROWS = 18;
    private static final int PLAYER_COLUMNS = 6;
    private Animation<TextureRegion> smallIdleRight;
    private Animation<TextureRegion> smallIdleLeft;
    private Animation<TextureRegion> smallWalkRight;
    private Animation<TextureRegion> smallWalkLeft;
    private Animation<TextureRegion> smallJumpRight;
    private Animation<TextureRegion> smallJumpLeft;
    private Animation<TextureRegion> bigIdleRight;
    private Animation<TextureRegion> bigIdleLeft;
    private Animation<TextureRegion> bigWalkRight;
    private Animation<TextureRegion> bigWalkLeft;
    private Animation<TextureRegion> bigJumpRight;
    private Animation<TextureRegion> bigJumpLeft;
    private Animation<TextureRegion> flowerIdleRight;
    private Animation<TextureRegion> flowerIdleLeft;
    private Animation<TextureRegion> flowerWalkRight;
    private Animation<TextureRegion> flowerWalkLeft;
    private Animation<TextureRegion> flowerJumpRight;
    private Animation<TextureRegion> flowerJumpLeft;

    // Goomba animation
    private Texture goombaSheet;
    private static final int GOOMBA_ROWS = 2;
    private static final int GOOMBA_COLUMNS = 2;
    private Animation<TextureRegion> goombaWalk;
    private Animation<TextureRegion> goombaDeath;

    public Animator() {
        this.playerSheet = Assets.MANAGER.get(Assets.PLAYER);
        TextureRegion[][] player = TextureRegion.split(this.playerSheet, this.playerSheet.getWidth() / PLAYER_COLUMNS, this.playerSheet.getHeight() / PLAYER_ROWS);
        this.smallIdleRight = this.createAnimation(0, 1, player);
        this.smallIdleLeft = this.createAnimation(1, 1, player);
        this.smallWalkRight = this.createAnimation(2, 3, player);
        this.smallWalkLeft = this.createAnimation(3, 3, player);
        this.smallJumpRight = this.createAnimation(4, 4, player);
        this.smallJumpLeft = this.createAnimation(5, 4, player);
        this.bigIdleRight = this.createAnimation(6, 1, player);
        this.bigIdleLeft = this.createAnimation(7, 1, player);
        this.bigWalkRight = this.createAnimation(8, 3, player);
        this.bigWalkLeft = this.createAnimation(9, 3, player);
        this.bigJumpRight = this.createAnimation(10, 6, player);
        this.bigJumpLeft = this.createAnimation(11, 6, player);
        this.flowerIdleRight = this.createAnimation(12, 1, player);
        this.flowerIdleLeft = this.createAnimation(13, 1, player);
        this.flowerWalkRight = this.createAnimation(14, 3, player);
        this.flowerWalkLeft = this.createAnimation(15, 3, player);
        this.flowerJumpRight = this.createAnimation(16, 6, player);
        this.flowerJumpLeft = this.createAnimation(17, 6, player);

        this.goombaSheet = Assets.MANAGER.get(Assets.GOOMBA);
        TextureRegion[][] goomba = TextureRegion.split(this.goombaSheet, this.goombaSheet.getWidth() / GOOMBA_COLUMNS, this.goombaSheet.getHeight() / GOOMBA_ROWS);
        this.goombaWalk = this.createAnimation(0, 2, goomba);
        this.goombaDeath = this.createAnimation(1, 1, goomba);
    }

    public Animation<TextureRegion> getSmallIdleRight() {
        return this.smallIdleRight;
    }

    public Animation<TextureRegion> getSmallIdleLeft() {
        return this.smallIdleLeft;
    }

    public Animation<TextureRegion> getSmallWalkRight() {
        return this.smallWalkRight;
    }

    public Animation<TextureRegion> getSmallWalkLeft() {
        return this.smallWalkLeft;
    }

    public Animation<TextureRegion> getSmallJumpRight() {
        return this.smallJumpRight;
    }

    public Animation<TextureRegion> getSmallJumpLeft() {
        return this.smallJumpLeft;
    }

    public Animation<TextureRegion> getBigIdleRight() {
        return this.bigIdleRight;
    }

    public Animation<TextureRegion> getBigIdleLeft() {
        return this.bigIdleLeft;
    }

    public Animation<TextureRegion> getBigWalkRight() {
        return this.bigWalkRight;
    }

    public Animation<TextureRegion> getBigWalkLeft() {
        return this.bigWalkLeft;
    }

    public Animation<TextureRegion> getBigJumpRight() {
        return this.bigJumpRight;
    }

    public Animation<TextureRegion> getBigJumpLeft() {
        return this.bigJumpLeft;
    }

    public Animation<TextureRegion> getFlowerIdleLeft() {
        return this.flowerIdleLeft;
    }

    public Animation<TextureRegion> getFlowerIdleRight() {
        return this.flowerIdleRight;
    }

    public Animation<TextureRegion> getFlowerJumpLeft() {
        return this.flowerJumpLeft;
    }

    public Animation<TextureRegion> getFlowerJumpRight() {
        return this.flowerJumpRight;
    }

    public Animation<TextureRegion> getFlowerWalkLeft() {
        return this.flowerWalkLeft;
    }

    public Animation<TextureRegion> getFlowerWalkRight() {
        return this.flowerWalkRight;
    }

    public Animation<TextureRegion> getGoombaWalk() {
        return this.goombaWalk;
    }

    public Animation<TextureRegion> getGoombaDeath() {
        return this.goombaDeath;
    }

    private Animation<TextureRegion> createAnimation(int row, int framesCount, TextureRegion[][] sheet) {
        TextureRegion[] animation = new TextureRegion[framesCount];
        TextureRegion[] frames = sheet[row];
        int index = 0;
        for (int i = 0; i < framesCount; i++) {
            animation[index++] = frames[i];
        }

        return new Animation<TextureRegion>(0.15f, animation);
    }
}

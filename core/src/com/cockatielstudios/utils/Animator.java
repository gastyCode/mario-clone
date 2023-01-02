package com.cockatielstudios.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cockatielstudios.Assets;

public class Animator {
    private Texture playerSheet;
    private static final int PLAYER_ROWS = 18, PLAYER_COLUMNS = 6;
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

    public Animator() {
        this.playerSheet = Assets.manager.get(Assets.player);

        TextureRegion[][] player = TextureRegion.split(this.playerSheet, this.playerSheet.getWidth() / PLAYER_COLUMNS, this.playerSheet.getHeight() / PLAYER_ROWS);
        this.smallIdleRight = createAnimation(0, 1, player);
        this.smallIdleLeft = createAnimation(1, 1, player);
        this.smallWalkRight = createAnimation(2, 3, player);
        this.smallWalkLeft = createAnimation(3, 3, player);
        this.smallJumpRight = createAnimation(4, 4, player);
        this.smallJumpLeft = createAnimation(5, 4, player);
        this.bigIdleRight = createAnimation(6, 1, player);
        this.bigIdleLeft = createAnimation(7, 1, player);
        this.bigWalkRight = createAnimation(8, 3, player);
        this.bigWalkLeft = createAnimation(9, 3, player);
        this.bigJumpRight = createAnimation(10, 6, player);
        this.bigJumpLeft = createAnimation(11, 6, player);
        this.flowerIdleRight = createAnimation(12, 1, player);
        this.flowerIdleLeft = createAnimation(13, 1, player);
        this.flowerWalkRight = createAnimation(14, 3, player);
        this.flowerWalkLeft = createAnimation(15, 3, player);
        this.flowerJumpRight = createAnimation(16, 6, player);
        this.flowerJumpLeft = createAnimation(17, 6, player);

    }

    public Animation<TextureRegion> getSmallIdleRight() {
        return smallIdleRight;
    }

    public Animation<TextureRegion> getSmallIdleLeft() {
        return smallIdleLeft;
    }

    public Animation<TextureRegion> getSmallWalkRight() {
        return smallWalkRight;
    }

    public Animation<TextureRegion> getSmallWalkLeft() {
        return smallWalkLeft;
    }

    public Animation<TextureRegion> getSmallJumpRight() {
        return smallJumpRight;
    }

    public Animation<TextureRegion> getSmallJumpLeft() {
        return smallJumpLeft;
    }

    public Animation<TextureRegion> getBigIdleRight() {
        return bigIdleRight;
    }

    public Animation<TextureRegion> getBigIdleLeft() {
        return bigIdleLeft;
    }

    public Animation<TextureRegion> getBigWalkRight() {
        return bigWalkRight;
    }

    public Animation<TextureRegion> getBigWalkLeft() {
        return bigWalkLeft;
    }

    public Animation<TextureRegion> getBigJumpRight() {
        return bigJumpRight;
    }

    public Animation<TextureRegion> getBigJumpLeft() {
        return bigJumpLeft;
    }

    public Animation<TextureRegion> getFlowerIdleLeft() {
        return flowerIdleLeft;
    }

    public Animation<TextureRegion> getFlowerIdleRight() {
        return flowerIdleRight;
    }

    public Animation<TextureRegion> getFlowerJumpLeft() {
        return flowerJumpLeft;
    }

    public Animation<TextureRegion> getFlowerJumpRight() {
        return flowerJumpRight;
    }

    public Animation<TextureRegion> getFlowerWalkLeft() {
        return flowerWalkLeft;
    }

    public Animation<TextureRegion> getFlowerWalkRight() {
        return flowerWalkRight;
    }

    private Animation<TextureRegion> createAnimation(int row, int framesCount, TextureRegion[][] sheet) {
        TextureRegion[] animation = new TextureRegion[framesCount];
        TextureRegion[] frames = sheet[row];
        int index = 0;
        for (int i = 0; i < framesCount; i++) {
            animation[index++] = frames[i];
        }

        return new Animation<TextureRegion>(0.1f, animation);
    }
}

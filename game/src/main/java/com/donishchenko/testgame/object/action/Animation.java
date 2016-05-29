package com.donishchenko.testgame.object.action;

import java.awt.image.BufferedImage;

public class Animation {

    private Action action;

    private int index;
    private int timer;
    private int rate;
    private BufferedImage currentFrame;
    private BufferedImage[] currentAnimation;
    private BufferedImage[] leftAnimation;
    private BufferedImage[] rightAnimation;

    public Animation(Action action) {
        this.action = action;
    }

    public void next() {
        if (++timer == rate) {
            currentFrame = currentAnimation[index++];
            if (index == currentAnimation.length) {
                index = 0;
                fireAnimationFinished();
            }
            timer = 0;
        }
    }

    public BufferedImage getCurrentFrame() {
        return currentFrame;
    }

    public void revert() {
        if (currentAnimation == rightAnimation) {
            currentAnimation = leftAnimation;
        } else {
            currentAnimation = rightAnimation;
        }
    }

    private void fireAnimationFinished() {
        action.animationFinished();
    }

    public void setAnimation(int rate, BufferedImage[] leftAnimation, BufferedImage[] rightAnimation) {
        this.rate = rate;
        this.leftAnimation = leftAnimation;
        this.rightAnimation = rightAnimation;

        this.currentAnimation = rightAnimation;
    }

    public void checkDirection(float dirX) {
        if (dirX > 0) {
            currentAnimation = rightAnimation;
        } else {
            currentAnimation = leftAnimation;
        }
    }


//    public Action action;
//    public int currentSprite = 0;
//    private int animationTimer;
//    public BufferedImage[] sprites;
//    private BufferedImage[] spritesRight;
//    private BufferedImage[] spritesLeft;
//    public int previousDirX;

//    public Animation(Action action, BufferedImage[] sprites,
//                     BufferedImage[] spritesRight, BufferedImage[] spritesLeft) {
//        this.action = action;
//        this.sprites = sprites;
//        this.spritesRight = spritesRight;
//        this.spritesLeft = spritesLeft;
//        this.previousDirX = 1;
//        this.currentSprite = 0;
//    }

//    public void next() {
//        if (++animationTimer >= action.object.gModel.getAnimationSpeed()) {
//            currentSprite++;
//            if (currentSprite == sprites.length) {
//                currentSprite = 0;
//                if (action.needFullAnimation) {
//                    action.animationFinished();
//                }
//            }
//            animationTimer = 0;
//        }
//    }

//    public BufferedImage get() {
//        try {
//            if (currentSprite == sprites.length) {
//                currentSprite = sprites.length - 1;
//            }
//            return sprites[currentSprite];
//        } catch (ArrayIndexOutOfBoundsException e) {
//            // TODO Animation: fix ArrayIndexOutOfBoundsException
//            System.out.println("Return 0 index");
//            return sprites[0];
//        }
//    }
//
//    public void swapDir(float unitDirX) {
//        previousDirX = unitDirX > 0 ? 1 : -1;
//        if (sprites == spritesRight) {
//            sprites = spritesLeft;
//        } else {
//            sprites = spritesRight;
//        }
//    }
//
//    public boolean watchWest() {
//        return previousDirX < 0;
//    }
//
//    public boolean watchEast() {
//        return previousDirX > 0;
//    }
}

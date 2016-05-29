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
        if (timer++ == rate) {
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
        this.timer = rate;
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
}

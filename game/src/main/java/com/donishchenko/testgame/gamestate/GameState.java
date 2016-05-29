package com.donishchenko.testgame.gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;

public abstract class GameState {

    protected GameStateManager gsm;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
    }

    public abstract void init();
    public abstract void processInput(KeyEvent event);
    public abstract void update();
    public abstract void render(Graphics2D g2);

}

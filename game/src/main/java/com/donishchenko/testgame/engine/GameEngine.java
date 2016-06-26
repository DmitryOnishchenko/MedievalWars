package com.donishchenko.testgame.engine;

import java.awt.event.KeyEvent;

public interface GameEngine {

    void init();
    void start();

    void processInput(KeyEvent keyEvent);
    void update();
    void render();

    /* Events */
    void onError(String errorMessage);

}

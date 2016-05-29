package com.donishchenko.testgame.engine;

public interface GameEngine {

    void init();
    void start();

    void processInput();
    void update();
    void render();

    /* Events */
    void onError(String errorMessage);

}

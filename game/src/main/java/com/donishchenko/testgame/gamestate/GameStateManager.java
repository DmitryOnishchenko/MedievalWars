package com.donishchenko.testgame.gamestate;

import com.donishchenko.testgame.gamestate.battle.BattleState;
import com.donishchenko.testgame.gamestate.test.TestGameState;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Stack;

@Component
public class GameStateManager {

    private Stack<GameState> states;

    public GameStateManager() {
        states = new Stack<>();
//        states.push(new TestGameState(this));
        states.push(new BattleState(this));
    }

    public void init() {
        states.peek().init();
    }

    public void processInput(KeyEvent event) {
        states.peek().processInput(event);
    }

    public void update() {
        states.peek().update();
    }

    public void render(Graphics2D graphics) {
        states.peek().render(graphics);
    }
}

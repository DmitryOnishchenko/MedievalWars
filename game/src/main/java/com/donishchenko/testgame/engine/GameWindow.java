package com.donishchenko.testgame.engine;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

import static com.donishchenko.testgame.config.GameConstants.*;

@Component("gameWindow")
public class GameWindow extends JFrame {

    public GameWindow() {
        setTitle(TITLE);
        setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setIgnoreRepaint(true);
        setIgnoreRepaint(true);

        setUndecorated(true);
//        setExtendedState(Frame.MAXIMIZED_BOTH);

        pack();
    }

}

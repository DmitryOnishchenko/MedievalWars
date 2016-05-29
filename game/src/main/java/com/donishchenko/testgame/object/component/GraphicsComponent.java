package com.donishchenko.testgame.object.component;

import java.awt.*;

public interface GraphicsComponent {

    void init();
    void update();
    void render(Graphics2D g2);

}

package com.donishchenko.testgame.object.component;

import com.donishchenko.testgame.object.GameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StaticObjectGraphicsComponent implements GraphicsComponent {

    private GameObject staticGameObject;

    // texture variables
    private BufferedImage texture;
    private int xCorrection;
    private int yCorrection;

    public StaticObjectGraphicsComponent(GameObject staticGameObject, BufferedImage texture) {
        this.staticGameObject = staticGameObject;
        this.texture = texture;
    }

    public StaticObjectGraphicsComponent(GameObject staticGameObject, BufferedImage texture, int xCorrection, int yCorrection) {
        this.staticGameObject = staticGameObject;
        this.texture = texture;
        this.xCorrection = xCorrection;
        this.yCorrection = yCorrection;
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(texture, staticGameObject.x() - xCorrection, staticGameObject.y() - yCorrection, null);
    }
}

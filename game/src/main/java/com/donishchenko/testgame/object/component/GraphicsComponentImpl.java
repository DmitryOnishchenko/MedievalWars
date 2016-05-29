package com.donishchenko.testgame.object.component;

import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.Side;
import com.donishchenko.testgame.resources.GraphicsModel;
import com.donishchenko.testgame.resources.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.donishchenko.testgame.gamestate.battle.BattleStateSettings.*;

public class GraphicsComponentImpl implements GraphicsComponent {

    private GameObject gameObject;

    // frame variables
    private int xCorrection;
    private int yCorrection;

    // debug
    private Color debugTargetColor;

    public GraphicsComponentImpl(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void init() {
        debugTargetColor = (gameObject.side == Side.LeftArmy ? Color.GREEN : Color.RED);
        GraphicsModel graphicsModel = gameObject.graphicsModel;
        xCorrection = (int) (graphicsModel.getWidthSprite() * ResourceLoader.SCALE / 2);
        yCorrection = (int) ((graphicsModel.getHeightSprite() + graphicsModel.getBaseLine()) * ResourceLoader.SCALE / 2);
    }

    @Override
    public void update() {
        gameObject.action.updateAnimation();
    }

    @Override
    public void render(Graphics2D g2) {
        if (!gameObject.visible) return;

        if (DEBUG_MODE && gameObject.isAlive()) {
            if (DEBUG_BOX) {
                g2.setPaint(debugTargetColor);
                g2.draw(gameObject.hitBox);
            }

            if (DEBUG_SEARCH) {
                // search box
                g2.setPaint(Color.WHITE);
                g2.draw(gameObject.searchCircle);
                // attack box
                g2.setPaint(Color.YELLOW);
                g2.draw(gameObject.attackBox);
            }

            if (DEBUG_TARGET) {
                GameObject target = gameObject.target;
                if (target != null) {
                    g2.setPaint(debugTargetColor);
                    g2.drawLine(gameObject.x(), gameObject.y(), target.x(), target.y());
                }
            }
        }

        BufferedImage image = gameObject.action.getCurrentFrame();
        g2.drawImage(image, gameObject.x() - xCorrection, gameObject.y() - yCorrection, null);
    }
}

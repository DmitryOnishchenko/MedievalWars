package com.donishchenko.testgame.gamestate.test;


import com.donishchenko.testgame.resources.GraphicsModel;
import com.donishchenko.testgame.resources.ResourceLoader;
import com.donishchenko.testgame.resources.Resources;
import com.donishchenko.testgame.config.GameConstants;
import com.donishchenko.testgame.gamestate.GameState;
import com.donishchenko.testgame.gamestate.GameStateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestGameState extends GameState {

    private BufferedImage backgroundLayer_0;
    private BufferedImage backgroundLayer_1;

    private int total = 6_000;
    private List<TestObject> testObjects;

    public TestGameState(GameStateManager gsm) {
        super(gsm);
        testObjects = new ArrayList<>();
    }

    @Override
    public void init() {
        Resources common = ResourceLoader.getResources("common");
        backgroundLayer_0 = common.get("backgroundLayer_0");
        backgroundLayer_1 = common.get("backgroundLayer_1");

        Resources resources1 = ResourceLoader.getResources("Human Soldier");
        Resources resources2 = ResourceLoader.getResources("Human Archer");
        Resources resources3 = ResourceLoader.getResources("Orc Soldier");
        Resources resources4 = ResourceLoader.getResources("Orc Archer");
        Resources resources5 = ResourceLoader.getResources("Orc Soldier Elite");

        GraphicsModel model1 = resources1.get("graphicsModel");
        GraphicsModel model2 = resources2.get("graphicsModel");
        GraphicsModel model3 = resources3.get("graphicsModel");
        GraphicsModel model4 = resources4.get("graphicsModel");
        GraphicsModel model5 = resources5.get("graphicsModel");

        List<BufferedImage> images = new ArrayList<>();
        images.addAll(Arrays.asList(model1.getFightSpritesRight()));
        images.addAll(Arrays.asList(model2.getFightSpritesRight()));
        images.addAll(Arrays.asList(model3.getFightSpritesRight()));
        images.addAll(Arrays.asList(model4.getFightSpritesRight()));
        images.addAll(Arrays.asList(model5.getFightSpritesRight()));

        Random r = new Random();
        for (int i = 0; i < total; i++) {
            testObjects.add(new TestObject(images.get(r.nextInt(40)), r.nextInt(GameConstants.DEFAULT_WIDTH), r.nextInt(GameConstants.DEFAULT_HEIGHT)));
        }
    }

    @Override
    public void processInput(KeyEvent event) {

    }

    @Override
    public void update() {
        for (TestObject obj : testObjects) {
            obj.updateAi();
            obj.updateAction();
            obj.updatePhysics();
            obj.updateGraphics();
        }
    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(backgroundLayer_0, 0, 0, null);

        for (TestObject obj : testObjects) {
            obj.render(g2);
        }

        g2.drawImage(backgroundLayer_1, 0, 0, null);

        /* Info */
        g2.setPaint(Color.WHITE);
        g2.drawString("TestState | Objects: " + total, 900, 36);
    }
}

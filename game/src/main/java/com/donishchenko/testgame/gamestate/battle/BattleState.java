package com.donishchenko.testgame.gamestate.battle;

import com.donishchenko.testgame.gamestate.GameState;
import com.donishchenko.testgame.gamestate.GameStateManager;
import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.Side;
import com.donishchenko.testgame.resources.ResourceLoader;
import com.donishchenko.testgame.resources.Resources;
import com.donishchenko.testgame.utils.FastRemoveArrayList;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.donishchenko.testgame.config.EngineConstants.MAX_FPS;
import static com.donishchenko.testgame.gamestate.battle.BattleStateSettings.*;

public class BattleState extends GameState {

    public static Grid grid = new Grid();

    private List<GameObject> renderObjects = new FastRemoveArrayList<>(10_000);

    /* Background layers */
    private BufferedImage backgroundLayer_0;
    private BufferedImage backgroundLayer_1;
    private List<BufferedImage> bloodList;

    public BattleState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        /* Common */
        Resources common = ResourceLoader.getResources("common");
        backgroundLayer_0 = common.get("backgroundLayer_0");
        backgroundLayer_1 = common.get("backgroundLayer_1");

        /* Effects */
        Resources effects = ResourceLoader.getResources("effects");
        bloodList = effects.get("bloodList");

        addGameObject(createDemoUnit("Human Soldier", Side.LeftArmy, 150, 500));
        addGameObject(createDemoUnit("Orc Soldier", Side.RightArmy, 300, 500));

        for (int i = 0; i < 100; i++) {
            addGameObject(createDemoUnit("Human Soldier", Side.LeftArmy, BattleStateSettings.leftSpawnPoint, getRandomPointY()));
            addGameObject(createDemoUnit("Orc Soldier", Side.RightArmy, BattleStateSettings.rightSpawnPoint, getRandomPointY()));
        }
    }

    public void addGameObject(GameObject gameObject) {
        grid.add(gameObject);
        synchronized (renderObjects) {
            renderObjects.add(gameObject);
        }
    }

    @Override
    public void processInput(KeyEvent event) {

    }

    @Override
    public void update() {
        if (PAUSE) {
            return;
        }

        if (DEMO_MODE) {
            //TODO run demo
            demoMode();
        }

        grid.update();
    }

    // TODO sortTrigger: don't forget this
    private int sortTrigger = MAX_FPS / 2 - 1;

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(backgroundLayer_0, 0, 0, null);

        if (DEBUG_GRID) {
            grid.debugRender(g2);
        }

        synchronized (renderObjects) {
            if (++sortTrigger == MAX_FPS / 2) {
                sortTrigger = 0;

                // copy yLevel for sort
                Iterator<GameObject> iterator = renderObjects.iterator();
                while (iterator.hasNext()) {
                    GameObject gameObject = iterator.next();
                    if (gameObject.delete) {
                        iterator.remove();
                    } else {
                        gameObject.yLevel = gameObject.pos.y;
                    }
                }

                Collections.sort(renderObjects);
            }
            for (GameObject obj : renderObjects) {
                obj.render(g2);
            }
        }

        g2.drawImage(backgroundLayer_1, 0, 0, null);

        /* Info */
        g2.setPaint(Color.WHITE);
        g2.drawString("BattleState | GameObjects: " + renderObjects.size(), 900, 36);
    }

    // TODO test: createDemoUnit
    public GameObject createDemoUnit(String name, Side side, float x, float y) {
        GameObject gameObject = new GameObject(name, side, x, y);
        gameObject.init();

        return gameObject;
    }

    // TODO test: demoMode
    /* Benchmark-Demo test */
    public void demoMode() {
        addGameObject(createDemoUnit("Human Soldier", Side.LeftArmy, leftSpawnPoint, getRandomPointY()));
        addGameObject(createDemoUnit("Orc Soldier", Side.RightArmy, rightSpawnPoint, getRandomPointY()));

//        if (++testSpawnTimer2 >= 8) {
//            addGameObject(createDemoUnit("Orc Soldier", Side.RightArmy, rightSpawnPoint, getRandomPointY()));
//        }
//
//        if (++testSpawnTimer2 >= 10) {
//            testSpawnTimer2 = 0;
//            addGameObject(createDemoUnit("Human Archer", Side.LeftArmy, leftSpawnPoint, getRandomPointY()));
//            addGameObject(createDemoUnit("Human Archer", Side.LeftArmy, leftSpawnPoint, getRandomPointY()));
//            addGameObject(createDemoUnit("Orc Soldier", Side.RightArmy, rightSpawnPoint, getRandomPointY()));
//            addGameObject(createDemoUnit("Orc Archer", Side.RightArmy, rightSpawnPoint, getRandomPointY()));
//        }
    }

    // TODO test: random y
    private Random random = new Random();
    public int getRandomPointY() {
        return (int) ((random.nextFloat() * (490 - 1)) + Grid.INDENT_TOP);
    }

}

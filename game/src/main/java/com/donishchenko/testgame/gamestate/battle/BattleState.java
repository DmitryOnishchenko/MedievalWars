package com.donishchenko.testgame.gamestate.battle;

import com.donishchenko.testgame.gamestate.GameState;
import com.donishchenko.testgame.gamestate.GameStateManager;
import com.donishchenko.testgame.object.GameObject;
import com.donishchenko.testgame.object.Side;
import com.donishchenko.testgame.object.Vector2F;
import com.donishchenko.testgame.object.component.StaticObjectGraphicsComponent;
import com.donishchenko.testgame.resources.ResourceLoader;
import com.donishchenko.testgame.resources.Resources;
import com.donishchenko.testgame.utils.FastRemoveArrayList;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static com.donishchenko.testgame.config.EngineConstants.MAX_FPS;
import static com.donishchenko.testgame.gamestate.battle.BattleStateSettings.*;
import static java.awt.event.KeyEvent.KEY_PRESSED;
import static java.awt.event.KeyEvent.KEY_RELEASED;

public class BattleState extends GameState {

    public static Grid grid = new Grid();

    private List<GameObject> renderObjects = new FastRemoveArrayList<>(10_000);

    /* Background layers */
    private BufferedImage backgroundLayer_0;
    private BufferedImage backgroundLayer_1;
    private List<BufferedImage> bloodList;

    GameObject player;
    private Rectangle2D.Float cameraBounds = new Rectangle2D.Float(300, 200, 600, 400);

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

        // test static object 1
        GameObject backgroundObject0 = new GameObject("Background_0", Side.Static, 0, 0);
        backgroundObject0.graphicsComponent = new StaticObjectGraphicsComponent(backgroundObject0, backgroundLayer_0);
        backgroundObject0.bounds = new Rectangle2D.Float(0, 0, backgroundLayer_0.getWidth(), backgroundLayer_0.getHeight());

        GameObject backgroundObject1 = new GameObject("Background_1", Side.Static, 0, 0);
        backgroundObject1.zLevel = 10_000;
        backgroundObject1.graphicsComponent = new StaticObjectGraphicsComponent(backgroundObject1, backgroundLayer_1);
        backgroundObject1.bounds = new Rectangle2D.Float(0, 0, backgroundLayer_1.getWidth(), backgroundLayer_1.getHeight());

        renderObjects.add(backgroundObject0);
        renderObjects.add(backgroundObject1);

        // test static object 2
        GameObject backgroundObject3 = new GameObject("Background_3", Side.Static, 1278, 0);
        backgroundObject3.graphicsComponent = new StaticObjectGraphicsComponent(backgroundObject3, backgroundLayer_0);
        backgroundObject3.bounds = new Rectangle2D.Float(1278, 0, backgroundLayer_0.getWidth(), backgroundLayer_0.getHeight());

        GameObject backgroundObject4 = new GameObject("Background_4", Side.Static, 1278, 0);
        backgroundObject4.zLevel = 10_000;
        backgroundObject4.graphicsComponent = new StaticObjectGraphicsComponent(backgroundObject4, backgroundLayer_1);
        backgroundObject4.bounds = new Rectangle2D.Float(1278, 0, backgroundLayer_1.getWidth(), backgroundLayer_1.getHeight());

        renderObjects.add(backgroundObject3);
        renderObjects.add(backgroundObject4);

        // test player
        player = createDemoUnit("Human Soldier", Side.LeftArmy, 0, 500);
        player.brain = null;

        addGameObject(player);
//        addGameObject(createDemoUnit("Human Soldier", Side.RightArmy, 800, 500));
//        addGameObject(createDemoUnit("Orc Soldier", Side.RightArmy, 1100, 500));

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
        if (event == null) return;

        // key codes
        /* right 39
           left  37
           up    38
           down  40 */

        char key = event.getKeyChar();
        int code = event.getKeyCode();
        int id = event.getID();

        if (code >= 37 && code <= 40) {
            // checkPosition all objects
            float k = 0;
            if (code == 39) {
                k = -1;
            } else if (code == 37) {
                k = 1;
            }

            float defaultSpeed = 20;
            float shiftX = (defaultSpeed * ResourceLoader.SCALE) * k;
            grid.move(shiftX, 0);

            synchronized (renderObjects) {
                for (GameObject gameObject : renderObjects) {
                    gameObject.pos.x            += shiftX;
                    gameObject.hitBox.x         += shiftX;
                    gameObject.attackBox.x      += shiftX;
                    gameObject.searchCircle.x   += shiftX;
                    gameObject.bounds.x         += shiftX;

//                    float shiftY                = (defaultSpeed * ResourceLoader.SCALE) * k;
//                    gameObject.pos.y            += shiftY;
//                    gameObject.hitBox.y         += shiftY;
//                    gameObject.attackBox.y      += shiftY;
//                    gameObject.searchCircle.y   += shiftY;
                }
            }
        }

        Vector2F dir = player.dir;
        if (key == 'd') {
            // checkPosition right
            if (id == KEY_PRESSED) dir.x = 1;
            else dir.x = 0;
        } else if (key == 'a') {
            if (id == KEY_PRESSED) dir.x = -1;
            else dir.x = 0;
        } else if (key == 'w') {
            if (id == KEY_PRESSED) dir.y = -1;
            else dir.y = 0;
        } else if (key == 's') {
            if (id == KEY_PRESSED) dir.y = 1;
            else dir.y = 0;
        }
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
//        g2.drawImage(backgroundLayer_0, 0, 0, null);

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
                // check visibility
//                if (cameraBounds.intersects(obj.bounds)) {
//                    obj.visible = true;
//                } else {
//                    obj.visible = false;
//                }

                obj.render(g2);
            }
        }

//        g2.drawImage(backgroundLayer_1, 0, 0, null);

        if (DEBUG_GRID) {
            grid.debugRender(g2);
        }

        // draw camera
//        g2.setPaint(Color.WHITE);
//        g2.setStroke(new BasicStroke(10));
//        g2.draw(cameraBounds);

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

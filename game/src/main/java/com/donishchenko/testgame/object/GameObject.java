package com.donishchenko.testgame.object;

import com.donishchenko.testgame.config.EngineConstants;
import com.donishchenko.testgame.gamestate.battle.Cell;
import com.donishchenko.testgame.object.action.Action;
import com.donishchenko.testgame.object.action.DieAction;
import com.donishchenko.testgame.object.ai.Brain;
import com.donishchenko.testgame.object.component.GraphicsComponent;
import com.donishchenko.testgame.object.component.GraphicsComponentImpl;
import com.donishchenko.testgame.resources.GraphicsModel;
import com.donishchenko.testgame.resources.PhysicsModel;
import com.donishchenko.testgame.resources.ResourceLoader;
import com.donishchenko.testgame.resources.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class GameObject implements Comparable<GameObject> {

    private static int ID = 1;

    /* Common fields */
    public final int id = ID++;
    public final String name;
    public Side side;

    public volatile boolean delete = false;

    public Type type;
    public int hp;
    public int damage;
    public int armor;
    public float speed;
    public Vector2F pos;
    public Vector2F dir;
    public volatile GameObject target;

    /* Box model */
    public Ellipse2D.Float hitBox = new Ellipse2D.Float();
    public Ellipse2D.Float attackBox = new Ellipse2D.Float();
    public Ellipse2D.Float searchCircle = new Ellipse2D.Float();
    public Rectangle2D.Float bounds;

    /* Models */
    public PhysicsModel physicsModel;
    public GraphicsModel graphicsModel;

    /* Components */
    public Cell cell;
    public boolean relocate;
//    public InputComponent inputComponent;
    public Brain brain;
    public Action action;
//    public PhysicsComponent physicsComponent;
    public GraphicsComponent graphicsComponent;

    /* Render variables */
    public boolean visible = true;
    public volatile int zLevel;
    public float yLevel;

    public GameObject(String name, Side side, float x, float y) {
        this.name = name;
        this.side = side;
        this.pos = new Vector2F(x, y);
    }

    public void init() {
        Resources resources = ResourceLoader.getResources(name);
        physicsModel = resources.get("physicsModel");
        graphicsModel = resources.get("graphicsModel");

        /* Init common variables */
        type    = physicsModel.getType();
        hp      = physicsModel.getMaxHp();
        damage  = physicsModel.getDamage();
        armor   = physicsModel.getArmor();
        speed   = physicsModel.getDefaultSpeed() / EngineConstants.TPS;
        // TODO test add random speed units
        speed   += (Math.random() * speed / 5);
        dir     = physicsModel.getMoveDir();

        /* Init render variables */
        zLevel  = physicsModel.getZLevel();
        yLevel  = pos.y;

        /* Init box model */
        float cornerX = pos.x + physicsModel.getHitBoxWidth();
        float cornerY = pos.y + physicsModel.getHitBoxHeight();
        hitBox.setFrameFromCenter(pos.x, pos.y, cornerX, cornerY);

        float searchRange = physicsModel.getSearchRange();
        searchCircle.setFrameFromCenter(pos.x, pos.y, pos.x + searchRange, pos.y + searchRange);

        float attackRange = physicsModel.getAttackRange();
        attackBox.setFrameFromCenter(pos.x, pos.y, pos.x + attackRange, pos.y + attackRange);

        // bounds
        int widthSprite = (int) (graphicsModel.getWidthSprite() * ResourceLoader.SCALE);
        int heightSprite = (int) (graphicsModel.getHeightSprite() * ResourceLoader.SCALE);
        int baseLine = (int) (graphicsModel.getBaseLine() * ResourceLoader.SCALE);

        cornerX = pos.x - widthSprite / 2;
        cornerY = pos.y + baseLine - heightSprite;
        bounds = new Rectangle2D.Float(cornerX, cornerY, widthSprite, heightSprite);

        /* Init components */
        brain = new Brain(this);
        brain.init();

        graphicsComponent = new GraphicsComponentImpl(this);
        graphicsComponent.init();
    }

    public void updateInput(KeyEvent event) {
//        if (inputComponent != null) inputComponent.update();
    }

    public void updateAi() {
        if (brain != null) brain.update();
    }

    public void updateAction() {
        if (action != null) action.execute();
    }

    public void updatePhysics() {
//        if (physicsComponent != null) physicsComponent.update();
    }

    public void updateGraphics() {
        if (graphicsComponent != null) graphicsComponent.update();
    }

    public void render(Graphics2D g2) {
        if (graphicsComponent != null) graphicsComponent.render(g2);
    }

    public int x() {
        return (int) pos.x;
    }

    public int y() {
        return (int) pos.y;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int damage) {
        if (!isAlive()) return;

        int totalDamage = damage - armor;
        if (totalDamage <= 0) {
            totalDamage = 1;
        }

        hp -= totalDamage;
        if (hp <= 0) {
            target = null;
            zLevel = 0;
            // TODO die action + draw blood effect
            action = new DieAction(this);
        }
    }

    /**
     * Compare method for rendering
     * @param obj
     * @return
     */
    @Override
    public int compareTo(GameObject obj) {
        int result = zLevel - obj.zLevel;
        if (result == 0) {
            result = Float.compare(yLevel, obj.yLevel);
        }
        return result;
    }

    public void switchState() {}

}

package com.donishchenko.testgame.resources;

import com.donishchenko.testgame.object.Race;
import com.donishchenko.testgame.object.Side;
import com.donishchenko.testgame.object.Type;
import com.donishchenko.testgame.object.Vector2F;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicsModel {

    @JsonProperty private String name;
    @JsonProperty private Type type;
    @JsonProperty private Side side;
    @JsonProperty private Race race;

    @JsonProperty private int maxHp;
    @JsonProperty private int armor;
    @JsonProperty private int damage;
    @JsonProperty private float defaultSpeed;
    @JsonProperty private float attackSpeed;
    @JsonProperty private float attackRange;
    @JsonProperty private float searchRange;

    @JsonProperty private int spawnPrice;
    @JsonProperty private int pricePerHead;

    @JsonProperty private int zLevel;
    @JsonProperty private Vector2F moveDir;
    @JsonProperty private float hitBoxWidth;
    @JsonProperty private float hitBoxHeight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public float getDefaultSpeed() {
        return defaultSpeed;
    }

    public void setDefaultSpeed(float defaultSpeed) {
        this.defaultSpeed = defaultSpeed * ResourceLoader.SCALE;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange * ResourceLoader.SCALE;
    }

    public float getSearchRange() {
        return searchRange;
    }

    public void setSearchRange(float searchRange) {
        this.searchRange = searchRange * ResourceLoader.SCALE;
    }

    public int getSpawnPrice() {
        return spawnPrice;
    }

    public void setSpawnPrice(int spawnPrice) {
        this.spawnPrice = spawnPrice;
    }

    public int getPricePerHead() {
        return pricePerHead;
    }

    public void setPricePerHead(int pricePerHead) {
        this.pricePerHead = pricePerHead;
    }

    public int getZLevel() {
        return zLevel;
    }

    public void setZLevel(int zLevel) {
        this.zLevel = zLevel;
    }

    public Vector2F getMoveDir() {
        return moveDir;
    }

    public void setMoveDir(Vector2F moveDir) {
        this.moveDir = moveDir;
    }

    public float getHitBoxWidth() {
        return hitBoxWidth;
    }

    public void setHitBoxWidth(float hitBoxWidth) {
        this.hitBoxWidth = hitBoxWidth * ResourceLoader.SCALE;
    }

    public float getHitBoxHeight() {
        return hitBoxHeight;
    }

    public void setHitBoxHeight(float hitBoxHeight) {
        this.hitBoxHeight = hitBoxHeight * ResourceLoader.SCALE;
    }

}

package com.untilldown.Model.EnemyClasses;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.untilldown.Model.Player;

public abstract class Enemy extends Actor {
    protected float hp;
    protected float damage;
    protected float speed;

    public abstract void update(float delta, Stage stage);

    public void shot(Stage stage) {}

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setRandomPosition() {
        // TODO: if have time
        float x = (1500 + (MathUtils.random() - (float) 1 / 2) * 1000);
        float y = (800 + (MathUtils.random() - (float) 1 / 2) * 500);

        setX(x);
        setY(y);
    }

    public void reduceHp(float amount) {
        hp -= amount;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void moveEnemy(float delta, Player player) {

        Vector2 direction = new Vector2(
            player.getX() - this.getX(),
            player.getY() - this.getY()
        ).nor();

        float speed = this.getSpeed() * delta;
        this.moveBy(direction.x * speed, direction.y * speed);
    }
}

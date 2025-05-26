package com.untilldown.Model.EnemyClasses;

import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Enemy extends Actor {
    protected float hp;

    abstract void update();
}

package com.untilldown.Model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.untilldown.Model.Enums.Hero;
import com.untilldown.Model.Enums.WeaponType;

public class Player extends Actor {
    private User user;
    private Hero hero;
    private TextureRegionDrawable sprite;
    private WeaponType weapon;

    public Player(User user, Hero hero, WeaponType weapon) {
        this.hero = hero;
        this.weapon = weapon;

        this.sprite = new TextureRegionDrawable(new TextureRegion(hero.getPortrairTexture()));
        setSize(sprite.getRegion().getRegionWidth(), sprite.getRegion().getRegionHeight());
    }

    public double getSpeed() {
        return hero.getSpeed();
    }

    public void move(float dx, float dy, float deltaTime) {
        float distanceX = (float) (dx * getSpeed() * deltaTime);
        float distanceY = (float) (dy * getSpeed() * deltaTime);
        moveBy(distanceX, distanceY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch, getX(), getY(), getWidth(), getHeight());
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }
}

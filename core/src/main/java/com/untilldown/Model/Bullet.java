package com.untilldown.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.untilldown.Model.Enums.WeaponType;

public class Bullet extends Actor {
    private Animation<TextureRegion> animation;
    private WeaponType weaponType;
    private Vector2 direction;
    private float speed = 250;
    private final float damageEffect;


    public Bullet(WeaponType weaponType, Vector2 direction, float damageEffect) {
        this.weaponType = weaponType;
        this.direction = direction;
        this.damageEffect = damageEffect;


        if (weaponType != null) {
            Array<TextureRegion> bulletTextureRegions = weaponType.getBulletTextureRegions();
            animation = new Animation<>(0.1f, bulletTextureRegions);
            animation.setPlayMode(Animation.PlayMode.LOOP);
            this.setSize(bulletTextureRegions.get(0).getRegionWidth() * damageEffect,
                bulletTextureRegions.get(0).getRegionHeight() * damageEffect);
        } else {
            animation = new Animation<>(1, new TextureRegion(new Texture("EyeMonsterProjecitle.png")));
            animation.setPlayMode(Animation.PlayMode.LOOP);
            this.setSize(12, 12);
            this.speed = 500;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float currentTime = App.getActiveGame().getTime();
        TextureRegion currentFrame;

        currentFrame = animation.getKeyFrame(currentTime);

        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    public void update(float delta) {
        moveBy(direction.x * delta * speed, direction.y * delta * speed);
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public float getDamage() {
        float damage = 1;
        if (weaponType != null) {
            damage = weaponType.getDamage();
        }
        damage *= damageEffect;

        return damage;
    }

    public Vector2 getDirection() {
        return direction;
    }
}

package com.untilldown.Model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.untilldown.Model.Enums.WeaponType;

public class Bullet extends Actor {
    Animation<TextureRegion> animation;
    WeaponType weaponType;
    Vector2 direction;

    public Bullet(WeaponType weaponType, Vector2 direction) {
        this.weaponType = weaponType;
        this.direction = direction;

        Array<TextureRegion> bulletTextureRegions = weaponType.getBulletTextureRegions();
        animation = new Animation<>(0.1f, bulletTextureRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        this.setSize(bulletTextureRegions.get(0).getRegionWidth(),
            bulletTextureRegions.get(0).getRegionHeight());
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
        moveBy(direction.x * delta * 40, direction.y * delta * 40);
    }
}

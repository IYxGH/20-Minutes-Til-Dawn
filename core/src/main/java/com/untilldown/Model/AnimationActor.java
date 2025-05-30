package com.untilldown.Model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.untilldown.Model.Enums.AnimationEffect;

public class AnimationActor extends Actor {
    private final Animation<TextureRegion> animation;
    private float stateTime = 0f;
    private final float width, height;
    private final Actor target;
    private final boolean following;

    public AnimationActor(AnimationEffect animationEffect, Actor actor, boolean following) {
        this.animation = new Animation<>(animationEffect.getDuration(), animationEffect.getTextureRegions());
        this.setPosition(actor.getX() , actor.getY());
        this.width = actor.getWidth();
        this.height = actor.getHeight();
        this.setSize(width, height);
        if (following)
            this.setSize(width * 1.2f, height * 1.2f);

        this.target = actor;
        this.following = following;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        if (following) {
            setPosition(target.getX() - 15, target.getY() - 15);
        }

        if (animation.isAnimationFinished(stateTime)) {
            this.remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = animation.getKeyFrame(stateTime, false);
        batch.draw(frame, getX(), getY());
    }
}

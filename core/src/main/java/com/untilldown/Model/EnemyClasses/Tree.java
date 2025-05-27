package com.untilldown.Model.EnemyClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.untilldown.Model.App;

public class Tree extends Enemy{
    private Animation<TextureRegion> animation;


    public Tree() {
        hp = 80000000;
        damage = 0.2f;
        Array<TextureRegion> textureRegions = new Array<>();
        for (int i = 0; i < 3; i++) {
            textureRegions.add(new TextureRegion(new Texture(Gdx.files.internal("Enemies/Tree/Tree (" + i + ").png"))));
        }
        setSize(textureRegions.get(0).getRegionWidth(), textureRegions.get(0).getRegionHeight());

        animation = new Animation<>(0.8f, textureRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP_RANDOM);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float currentTime = App.getActiveGame().getTime();
        TextureRegion currentFrame;

        currentFrame = animation.getKeyFrame(currentTime);

        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }
}

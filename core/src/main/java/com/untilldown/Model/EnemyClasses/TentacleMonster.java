package com.untilldown.Model.EnemyClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.untilldown.Model.App;
import com.untilldown.Model.Game;
import com.untilldown.Model.Player;

public class TentacleMonster extends Enemy{
    private final Animation<TextureRegion> animation;

    public TentacleMonster() {
        hp = 25;
        damage = 0.5f;
        speed = 70f;

        Array<TextureRegion> regions = new Array<>();
        for (int i = 0; i < 4; i++) {
            regions.add(new TextureRegion(new Texture(Gdx.files.internal("Enemies/TentacleMonster/TentacleIdle" + i +".png"))));
        }
        setSize(regions.get(1).getRegionWidth(), regions.get(1).getRegionHeight());

        animation = new Animation<>(0.2f, regions);
        animation.setPlayMode(Animation.PlayMode.LOOP);


    }

    @Override
    public void update(float delta, Stage stage) {
        Game game = App.getActiveGame();
        Player player = game.getPlayer();

        if (this.hp <= 0) {
            return;
        }

        moveEnemy(delta, player);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float currentTime = App.getActiveGame().getTime();
        TextureRegion currentFrame;

        currentFrame = animation.getKeyFrame(currentTime);

        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }
}

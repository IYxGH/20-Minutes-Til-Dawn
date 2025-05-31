package com.untilldown.Model.EnemyClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.untilldown.Model.App;
import com.untilldown.Model.Game;
import com.untilldown.Model.Player;

public class Elder extends Enemy {
    private final Animation<TextureRegion> animation;
    private float TimerToDash;

    public Elder() {
        hp = 600;
        damage = 1f;
        speed = 100000f;

        Array<TextureRegion> regions = new Array<>();
        for (int i = 0; i < 4; i++) {
            regions.add(new TextureRegion(new Texture(Gdx.files.internal("Enemies/Elder/BrainMonster_" + i +".png"))));
        }
        setSize(regions.get(1).getRegionWidth() * 2.5f, regions.get(1).getRegionHeight() * 2.5f);

        animation = new Animation<>(0.25f, regions);
        animation.setPlayMode(Animation.PlayMode.LOOP);

    }

    @Override
    public void update(float delta, Stage stage) {
        Game game = App.getActiveGame();
        Player player = game.getPlayer();

        if (this.hp <= 0) {
            return;
        }

        TimerToDash -= delta;
        if (TimerToDash < 0) {
            TimerToDash = 5;
            Vector2 direction = new Vector2(
                player.getX() - this.getX(),
                player.getY() - this.getY()
            );
            if (direction.len() > 500) direction.setLength(500);
            this.moveBy(direction.x, direction.y);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float currentTime = App.getActiveGame().getTime();
        TextureRegion currentFrame;

        currentFrame = animation.getKeyFrame(currentTime);

        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }
}

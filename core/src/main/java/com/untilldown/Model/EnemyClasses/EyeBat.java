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
import com.untilldown.Model.Bullet;
import com.untilldown.Model.Game;
import com.untilldown.Model.Player;

public class EyeBat extends Enemy {
    Animation<TextureRegion> animation;
    private float TimerAttack = 3f;

    public EyeBat() {
        hp = 50;
        damage = 0.1f;
        speed = 100f;
        Array<TextureRegion> textureRegions = new Array<>();
        for (int i = 0; i < 4; i++) {
            textureRegions.add(new TextureRegion(new Texture(Gdx.files.internal("Enemies/EyeBat/T_EyeBat_" + i + ".png"))));
        }
        setSize(textureRegions.get(0).getRegionWidth() * 0.7f, textureRegions.get(0).getRegionHeight() * 0.7f);

        animation = new Animation<>(0.1f, textureRegions);
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
        TimerAttack += delta;
        shot(stage);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float currentTime = App.getActiveGame().getTime();
        TextureRegion currentFrame;

        currentFrame = animation.getKeyFrame(currentTime);

        batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void shot(Stage stage) {
        if (TimerAttack < 3) return;
        TimerAttack = 0;

        Game game = App.getActiveGame();
        Player player = game.getPlayer();
        Vector2 direction = player.getPosition().sub(getX(), getY()).nor();
        Bullet bullet = new Bullet(null, direction, 0.25f);
        bullet.setPosition(getX(), getY());
        game.getEnemyBullets().add(bullet);
        stage.addActor(bullet);

    }
}

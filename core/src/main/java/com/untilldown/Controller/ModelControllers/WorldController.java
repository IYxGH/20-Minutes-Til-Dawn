package com.untilldown.Controller.ModelControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.untilldown.Model.*;
import com.untilldown.Model.EnemyClasses.Enemy;
import com.untilldown.Model.EnemyClasses.EyeBat;
import com.untilldown.Model.EnemyClasses.TentacleMonster;
import com.untilldown.Model.EnemyClasses.Tree;

public class WorldController {
    private Texture mapTexture;
    private MapActor mapActor;

    // spawning enemis
    private float timerTentacle = 0.0f;
    private float timerEyeBat = 0.0f;

    public void initWorld(Stage stage) {
        // Load your map texture
        mapTexture = new Texture(Gdx.files.internal("Map/map2.png"));

        // Create an actor for the map
        mapActor = new MapActor(mapTexture);

        // Add the map to the stage
        stage.addActor(mapActor);
        mapActor.setPosition(0, 0);

        initEnemies(stage);
    }

    public void update(float delta, Stage stage) {
        Game game = App.getActiveGame();
        Player player = game.getPlayer();

        timerTentacle -= delta;
        timerEyeBat -= delta;


        // check collisions & update enemies
        for (Enemy enemy : game.getEnemies()) {
            if (enemy.getBounds().overlaps(game.getPlayer().getBounds())) {
                if (player.getTimePastLastDamage() <= 0) {
                    player.reduceHp(enemy.getDamage());
                    player.setTimePastLastDamage(1);
                    enemy.reduceHp(1);
                }

            }

            enemy.update(delta);
        }

        spawnEnemies(stage);

        updateBullets(delta, game);
    }

    public void spawnEnemies(Stage stage) {
        Game game = App.getActiveGame();
        float time = game.getTime();

        // Tentacle
        if (timerTentacle <= 0.0f) {
            for (int i = 0; i < (int) (time / 30); i++) {
                TentacleMonster monster = new TentacleMonster();
                game.getEnemies().add(monster);
                monster.setRandomPosition();

                stage.addActor(monster);
            }
            timerTentacle = 3.0f;
        }

        // EyeBat
        if (timerEyeBat <= 0.0f && time > (game.getDuration() / 4)) {
            for (int i = 0; i < (4 * time - game.getDuration() + 30) / 30; i++) {
                EyeBat eyebat = new EyeBat();
                game.getEnemies().add(eyebat);
                eyebat.setRandomPosition();

                stage.addActor(eyebat);
            }
            timerEyeBat = 10.0f;
        }


        // Elder

    }

    public void initEnemies(Stage stage) {
        Game game = App.getActiveGame();
        float time = game.getTime();

        // Tree
        for (int i = 0; i < 30 + (Math.random() * 50); i++) {
            //TODO: make them less random if have time
            Tree tree = new Tree();
            float x = (float) (Math.random() * 2800 + 100);
            float y = (float) (Math.random() * 1500 + 100);
            tree.setPosition(x, y);
            stage.addActor(tree);
            game.getEnemies().add(tree);
        }






    }

    public void updateBullets(float delta, Game game) {
        for (Bullet bullet : game.getPlayerBullets()) {
            bullet.update(delta);
            //TODO: check collisions
        }
    }

    public Texture getMapTexture() {
        return mapTexture;
    }

    public MapActor getMapActor() {
        return mapActor;
    }
}

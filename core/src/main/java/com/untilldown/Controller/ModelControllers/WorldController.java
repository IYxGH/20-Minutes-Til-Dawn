package com.untilldown.Controller.ModelControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.untilldown.Model.*;
import com.untilldown.Model.EnemyClasses.Enemy;
import com.untilldown.Model.EnemyClasses.EyeBat;
import com.untilldown.Model.EnemyClasses.TentacleMonster;
import com.untilldown.Model.EnemyClasses.Tree;
import com.untilldown.View.GameView;

import java.util.ArrayList;

public class WorldController {
    private GameView gameView;
    private Texture mapTexture;
    private MapActor mapActor;

    // spawning enemies
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

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void update(float delta, Stage stage) {
        Game game = App.getActiveGame();
        Player player = game.getPlayer();



        timerTentacle -= delta;
        timerEyeBat -= delta;


        // check collisions & update enemies
        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (Enemy enemy : game.getEnemies()) {
            if (enemy.getBounds().overlaps(game.getPlayer().getBounds())) {
                if (player.getTimePastLastDamage() <= 0) {
                    player.reduceHp(enemy.getDamage());
                    player.setTimePastLastDamage(2);
                    enemy.reduceHp(1);
                }

            }

            enemy.update(delta);
            if (enemy.getHp() <= 0) {
                enemiesToRemove.add(enemy);
            }
        }

        for (Enemy enemy : enemiesToRemove) {
            spawnSeeds(stage, game, enemy);
            enemy.remove();
        }
        game.getEnemies().removeAll(enemiesToRemove);
        /*---------------------------------------------------------------*/

        checkSeeds(stage, game);

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
        ArrayList<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet : game.getPlayerBullets()) {
            bullet.update(delta);
            for (Enemy enemy : game.getEnemies()) {
                if (enemy.getBounds().overlaps(bullet.getBounds())) {
                    enemy.reduceHp(bullet.getDamage());
                    bullet.remove();
                    bulletsToRemove.add(bullet);
                    break;
                }
            }

            if (isOutOfBounds(bullet)) {
                bullet.remove();
                bulletsToRemove.add(bullet);
            }

        }

        game.getPlayerBullets().removeAll(bulletsToRemove);
    }

    public void checkSeeds(Stage stage, Game game) {
        ArrayList<Seed> seedsToRemove = new ArrayList<>();
        Player player = game.getPlayer();

        for (Seed seed : game.getSeeds()) {
            if (seed.getBounds().overlaps(player.getBounds())) {
                seedsToRemove.add(seed);
                seed.remove();
                player.addXp(3);
            }
        }
        game.getSeeds().removeAll(seedsToRemove);
    }

    public void spawnSeeds(Stage stage, Game game, Enemy enemy) {
        Seed seed = new Seed();
        seed.setPosition(enemy.getX(), enemy.getY());
        game.getSeeds().add(seed);
        stage.addActor(seed);
    }

    public boolean isOutOfBounds(Actor actor) {
        if (actor.getX() < Game.MAP_MIN_X || actor.getX() > Game.MAP_MAX_X ||
            actor.getY() < Game.MAP_MIN_Y || actor.getY() > Game.MAP_MAX_Y) {
            return true;
        }

        return false;
    }

    public Texture getMapTexture() {
        return mapTexture;
    }

    public MapActor getMapActor() {
        return mapActor;
    }
}

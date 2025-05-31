package com.untilldown.Controller.ModelControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.untilldown.Main;
import com.untilldown.Model.*;
import com.untilldown.Model.EnemyClasses.Elder;
import com.untilldown.Model.EnemyClasses.Enemy;
import com.untilldown.Model.EnemyClasses.Tree;
import com.untilldown.Model.Enums.Action;
import com.untilldown.View.GameView;

import java.util.ArrayList;

public class PlayerController {
    private Player player;
    private GameView gameView;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void initPlayer(Stage stage) {
        // Set initial position (you can adjust these values)
        player.setPosition(1500, 1500);
        stage.addActor(player);
    }

    public void update(float delta, Stage stage) {
        float dx = 0, dy = 0;

        GameControls gameControls = App.gameControls;

        if (Gdx.input.isKeyPressed(gameControls.getKey(Action.MOVE_UP))) dy += 1;
        if (Gdx.input.isKeyPressed(gameControls.getKey(Action.MOVE_DOWN))) dy -= 1;
        if (Gdx.input.isKeyPressed(gameControls.getKey(Action.MOVE_LEFT))) dx -= 1;
        if (Gdx.input.isKeyPressed(gameControls.getKey(Action.MOVE_RIGHT))) dx += 1;
        if (Gdx.input.isKeyJustPressed(gameControls.getKey(Action.TOGGLE_AIM))) player.toggleAutoAim();
        if (Gdx.input.isKeyJustPressed(gameControls.getKey(Action.ATTACK))) attack(stage);
        if (Gdx.input.isKeyJustPressed(gameControls.getKey(Action.RELOAD))) reload();
        if (Gdx.input.isKeyJustPressed(gameControls.getKey(Action.CHEAT_ADD_XP))) cheatAddXp();
        if (Gdx.input.isKeyJustPressed(gameControls.getKey(Action.CHEAT_ADD_LEVEL))) cheatAddLevel();
        if (Gdx.input.isKeyJustPressed(gameControls.getKey(Action.CHEAT_REDUCE_TIME))) cheatReduceTime(60);
        if (Gdx.input.isKeyPressed(gameControls.getKey(Action.CHEAT_KILL_ALL_ENEMIES))) cheatKillAllEnemies();

        // Normalize to avoid diagonal speed boost
        if (dx != 0 || dy != 0) {
            float len = (float) Math.sqrt(dx * dx + dy * dy);
            dx /= len;
            dy /= len;
        }

        player.move(dx, dy, delta);

        //timers
        player.reduceLastDamage(delta);
        player.reduceTimerBuffWeapon(delta);
        player.reduceTimerSpeedEffect(delta);

        checkXp();

        updateReloading(delta);
    }


    public void attack(Stage stage) {
        Game game = App.getActiveGame();
        if (player.getAmmoLeft() <= 0) return;

        if (player.isReloading()) return;

        float damageEffect = 1;
        if (player.getTimerBuffWeapon() > 0) {
            damageEffect = 1.25f;
        }

        if (getPlayer().isAutoAim()) {

        } else {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();

            SFXController.ShotSound();
            Vector3 worldCoords = stage.getCamera().unproject(new Vector3(mouseX, mouseY, 0));
            Vector2 mouseWorldPos = new Vector2(worldCoords.x, worldCoords.y);

            Vector2 playerPos = getPlayerPosition();
            Vector2 direction = mouseWorldPos.sub(playerPos).nor();
            int projectile = player.getProjectile();
            direction.rotateDeg((projectile / 2) * 5);

            for (int i = 0; i < projectile; i++) {
                Bullet bullet = new Bullet(player.getWeapon(), new Vector2(direction), damageEffect);
                bullet.setPosition(player.getX(), player.getY());
                game.getPlayerBullets().add(bullet);
                direction.rotateDeg(-5);

                stage.addActor(bullet);
            }

            player.reduceAmmo(1);
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Vector2 getPlayerPosition() {
        return player.getPosition();
    }

    public void reload() {
        if (player.isReloading()) return;

        player.setReloading(true);
        player.setTimerReloading(player.getWeapon().getReloadTime());
    }

    public void checkXp() {
        if(player.getXp() >= player.getLevel() * 20) {
            player.setXp(player.getXp() -player.getLevel() * 20);
            player.setLevel(player.getLevel() + 1);
            gameView.showAbilities();
        }
    }

    public void cheatAddXp() {
        player.addXp(10);
    }

    public void cheatAddLevel() {
        player.setXp(player.getLevel() * 20);
    }

    public void cheatKillAllEnemies() {
        Game game = App.getActiveGame();

        ArrayList<Enemy> enemies = new ArrayList<>();
        for (Enemy enemy: game.getEnemies()) {
            if (enemy instanceof Tree) continue;
            if (enemy instanceof Elder) continue;

            enemies.add(enemy);
        }

        for (Enemy enemy : enemies) {
            enemy.remove();
        game.getEnemies().removeAll(enemies);
        }
    }

    public void cheatReduceTime(float time) {
        App.getActiveGame().addTime(time);
    }

    public void updateReloading(float delta) {
        if (player.isAutoReload() && player.getAmmoLeft() <= 0 && !player.isReloading()) {
            reload();
            return;
        }
        if (!player.isReloading()) return;

        player.reduceTimerReloading(delta);
        if (player.getTimerReloading() <= 0) {
            player.setAmmoLeft(player.getMaxAmmo());
            player.setReloading(false);
            player.setTimerReloading(0);
        }
    }


}

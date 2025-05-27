package com.untilldown.Controller.ModelControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.untilldown.Main;
import com.untilldown.Model.*;
import com.untilldown.Model.Enums.Action;

public class PlayerController {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
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

        // Normalize to avoid diagonal speed boost
        if (dx != 0 || dy != 0) {
            float len = (float) Math.sqrt(dx * dx + dy * dy);
            dx /= len;
            dy /= len;
        }

        player.move(dx, dy, delta);
        player.reduceLastDamage(delta);

        updateReloading(delta);
    }


    public void attack(Stage stage) {
        Game game = App.getActiveGame();
        if (player.getAmmoLeft() <= 0) return;

        if (player.isReloading()) return;

        if (getPlayer().isAutoAim()) {

        } else {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.input.getY();

            Vector3 worldCoords = stage.getCamera().unproject(new Vector3(mouseX, mouseY, 0));
            Vector2 mouseWorldPos = new Vector2(worldCoords.x, worldCoords.y);

            Vector2 playerPos = getPlayerPosition();
            Vector2 direction = mouseWorldPos.sub(playerPos).nor();
            int projectile = player.getProjectile();
            direction.rotateDeg((projectile / 2) * 5);

            for (int i = 0; i < projectile; i++) {
                Bullet bullet = new Bullet(player.getWeapon(), direction);
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

    public void updateReloading(float delta) {
        if (!player.isReloading()) return;

        player.reduceTimerReloading(delta);
        if (player.getTimerReloading() <= 0) {
            player.setAmmoLeft(player.getWeapon().getMaxAmmo());
            player.setReloading(false);
            player.setTimerReloading(0);
        }
    }


}

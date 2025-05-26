package com.untilldown.Controller.ModelControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
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

        // Normalize to avoid diagonal speed boost
        if (dx != 0 || dy != 0) {
            float len = (float) Math.sqrt(dx * dx + dy * dy);
            dx /= len;
            dy /= len;
        }

        player.move(dx, dy, delta);
        player.reduceLastDamage(delta);
    }


    public void attack(Stage stage) {
        Game game = App.getActiveGame();
        if (player.getAmmoLeft() <= 0) return;

        if (getPlayer().isAutoAim()) {

        } else {
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();
            Vector2 v = new Vector2(x, y);

            Vector2 direction = v.sub(getPlayerPosition()).nor();

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


}

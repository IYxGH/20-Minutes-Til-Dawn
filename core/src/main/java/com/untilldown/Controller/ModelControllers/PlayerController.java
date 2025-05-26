package com.untilldown.Controller.ModelControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.untilldown.Main;
import com.untilldown.Model.Player;

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

    public void update(float delta) {
        float dx = 0, dy = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += 1;

        // Normalize to avoid diagonal speed boost
        if (dx != 0 || dy != 0) {
            float len = (float) Math.sqrt(dx * dx + dy * dy);
            dx /= len;
            dy /= len;
        }

        player.move(dx, dy, delta);
        player.reduceLastDamage(delta);
    }



    public Player getPlayer() {
        return player;
    }

    public Vector2 getPlayerPosition() {
        return player.getPosition();
    }


}

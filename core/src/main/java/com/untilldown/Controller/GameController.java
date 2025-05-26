package com.untilldown.Controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.untilldown.Controller.ModelControllers.PlayerController;
import com.untilldown.Controller.ModelControllers.WeaponController;
import com.untilldown.Controller.ModelControllers.WorldController;
import com.untilldown.Model.Game;
import com.untilldown.Model.Player;
import com.untilldown.View.GameView;

public class GameController {
    private Game game;
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;
    private boolean isPaused;

    private Stage stage;

    public GameController(Game game) {
        this.game = game;
    }

    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController(game.getPlayer());
        worldController = new WorldController();
        weaponController = new WeaponController();
    }

    public void initGame(Stage stage) {
        this.stage = stage;

        // Add world background/map
        worldController.initWorld(stage);

        // Add player/hero to stage
        playerController.initPlayer(stage);

        // Add weapons (if needed)
        weaponController.initWeapons(stage, playerController.getPlayer());
    }

    public void update(float delta) {
        if (isPaused) return;

        updateInfoLabel(view.getInfoLabel(), view.getProgressBar());
        playerController.update(delta);
        weaponController.update(delta);
        worldController.update(delta, view.getGameStage());
        game.addTime(delta);
    }

    public Vector2 getHeroPosition() {
        return playerController.getPlayerPosition();
    }

    public void updateInfoLabel(Label label, ProgressBar progressBar) {
        //update label
        StringBuilder info = new StringBuilder();
        Player player = playerController.getPlayer();
        info.append("HP: ").append(player.getHp()).append("\n");
        info.append("Time Left: ").append(game.getTimeLeft()).append("\n");
        info.append("Kills: ").append(player.getKills()).append("\n");
        info.append("Ammo: ").append(player.getAmmoLeft()).append("\n");

        label.setText(info.toString());
        label.setColor(Color.CYAN);


        //update progress bar
        progressBar.setRange(0, player.getLevel() * 30);
        progressBar.setValue(player.getXp());

    }

    public void pause() {
        view.getPauseMenu().setVisible(true);
        isPaused = true;
        view.getUiTable().setColor(1, 1, 1, 0.2f);
    }

    public void resume() {
        view.getPauseMenu().setVisible(false);
        isPaused = false;
        view.getUiTable().setColor(1, 1, 1, 1);
    }

    public void giveUp() {}
}

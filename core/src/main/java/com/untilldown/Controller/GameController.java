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
        playerController.setGameView(view);
        worldController = new WorldController();
        worldController.setGameView(view);
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
        playerController.update(delta, view.getGameStage());
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
        info.append("HP: ").append(String.format("%.2f", player.getHp())).append("\n");
        info.append("Time Left: ").append(game.getTimeLeft()).append("\n");
        info.append("Kills: ").append(player.getKills()).append("\n");
        info.append("Ammo: ").append(player.getAmmoLeft()).append("\n");
        info.append("Auto Aim: ").append(player.isAutoAim()).append("\n");
        //TODO: remove
        info.append("XP: ").append(player.getXp()).append("\n");

        label.setText(info.toString());
        label.setColor(Color.CYAN);


        //update progress bar
        progressBar.setRange(0, player.getLevel() * 20);
        progressBar.setValue(player.getXp());

    }

    public void pause() {
        view.getPauseMenu().setVisible(true);
        setPaused(true);
    }

    public void setPaused(boolean paused) {
        if (paused)
            view.getUiTable().setColor(1, 1, 1, 0.2f);
        else
            view.getUiTable().setColor(1, 1, 1, 1);


        this.isPaused = paused;
    }

    public void resume() {
        view.getPauseMenu().setVisible(false);
        isPaused = false;
        view.getUiTable().setColor(1, 1, 1, 1);
    }

    public void giveUp() {}

    public boolean checkEndGame() {
        if (game.getTime() >= game.getDuration()) return true;

        if (game.getPlayer().getHp() <= 0) return true;

        return false;
    }
}

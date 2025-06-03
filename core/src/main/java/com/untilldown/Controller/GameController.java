package com.untilldown.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.untilldown.Controller.ModelControllers.PlayerController;
import com.untilldown.Controller.ModelControllers.WeaponController;
import com.untilldown.Controller.ModelControllers.WorldController;
import com.untilldown.Model.*;
import com.untilldown.Model.EnemyClasses.Enemy;
import com.untilldown.Model.EnemyClasses.Tree;
import com.untilldown.Model.Enums.Ability;
import com.untilldown.Model.Enums.Action;
import com.untilldown.View.GameView;

import javax.swing.text.Position;
import java.util.Map;

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
        checkEndGame();

        if (game.getPlayer().isAutoAim()) setCursor();

        view.getBackground().setPosition(playerController.getPlayer().getX() - Gdx.graphics.getWidth() / 2, playerController.getPlayer().getY() - Gdx.graphics.getHeight() / 2);


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

        //update label
        Label label = view.getInfoInPauseMenu();
        StringBuilder info = new StringBuilder();
        Player player = playerController.getPlayer();
        info.append("Abilities: \n");
        for (Map.Entry<Ability, Integer> entry : player.getCollectedAbilities().entrySet()) {
            info.append("   ").append(entry.getKey().getMessage()).append(": ").append(entry.getValue()).append("\n");
        }

        GameControls gameControls = App.gameControls;
        info.append("\nCheats: \n");
        for (Map.Entry<Action, Integer> entry: gameControls.getCurrentControls().entrySet()) {
            Action action = entry.getKey();
            if (!action.isCheat()) continue;
            int num = entry.getValue();
            info.append("   ").append(action.getInfo()).append(": ").append(Input.Keys.toString(entry.getValue())).append("\n");
        }

        label.setText(info.toString());
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

    public void giveUp() {
        view.showEndGame();
    }

    public void checkEndGame() {
        if (game.getTime() >= game.getDuration() || game.getPlayer().getHp() <= 0) {
            setPaused(true);
            view.showEndGame();
        }
    }

    public void finishGame() {
        User user = App.getCurrentUser();
        Player player = playerController.getPlayer();

        if (game.getTime() > user.getMaxLifeTime()) {
            user.setMaxLifeTime(game.getTime());
            App.updateUserInDatabase(user);
        }

        user.setTotalPoints(user.getTotalPoints() + player.getPoints());
        App.updateUserInDatabase(user);

        user.setTotalKills(user.getTotalKills() + player.getKills());
        App.updateUserInDatabase(user);


    }

    public void setCursor() {
// 1) Find closest non‐Tree enemy in world coordinates
        Vector2 closestPos = new Vector2( Float.MAX_VALUE, Float.MAX_VALUE );
        Vector2 playerPos  = playerController.getPlayer().getPosition();
        float   minDist    = Float.MAX_VALUE;

        for (Enemy enemy : game.getEnemies()) {
            if (enemy instanceof Tree) continue;

            Vector2 enemyPos = new Vector2(enemy.getX(), enemy.getY());
            float   dist     = enemyPos.dst(playerPos);
            if (dist < minDist) {
                minDist = dist;
                closestPos.set(enemyPos);
            }
        }

// 2) Project that world position into screen space
        Vector3 worldPos = new Vector3(closestPos.x, closestPos.y, 0);
        Camera camera = view.getGameStage().getCamera();
        camera.project(worldPos);  // now worldPos.x/y are in screen coords (origin = bottom‐left)

// 3) Flip the Y coordinate for Gdx.input
        int screenX = (int) worldPos.x;
        int screenY = Gdx.graphics.getHeight() - (int) worldPos.y;

// 4) Finally move the hardware cursor
        Gdx.input.setCursorPosition(screenX, screenY);

    }


}

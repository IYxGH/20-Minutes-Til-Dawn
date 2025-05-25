package com.untilldown.Controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
//        playerController.initPlayer(stage);

        // Add weapons (if needed)
        weaponController.initWeapons(stage, playerController.getPlayer());
    }

    public void update(float delta) {
        playerController.update(delta);
        weaponController.update(delta);
    }

    public Vector2 getHeroPosition() {
        return playerController.getPlayerPosition();
    }
}

package com.untilldown.Controller;

import com.untilldown.Controller.ModelControllers.PlayerController;
import com.untilldown.Controller.ModelControllers.WeaponController;
import com.untilldown.Controller.ModelControllers.WorldController;
import com.untilldown.Model.Player;
import com.untilldown.View.GameView;

public class GameController {
    private GameView view;
    private PlayerController playerController;
    private WorldController worldController;
    private WeaponController weaponController;


    public void setView(GameView view) {
        this.view = view;
        playerController = new PlayerController();
        worldController = new WorldController();
        weaponController = new WeaponController();
    }

}

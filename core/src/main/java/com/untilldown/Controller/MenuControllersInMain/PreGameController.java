package com.untilldown.Controller.MenuControllersInMain;

import com.untilldown.Controller.GameController;
import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.Game;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.Model.Player;
import com.untilldown.View.GameView;
import com.untilldown.View.MenusViewInMain.PreGameView;

public class PreGameController {
    private PreGameView view;

    public void setView(PreGameView view) {
        this.view = view;
    }

    public void play() {
        Game game = new Game(new Player(App.getCurrentUser(), view.getSelectedHero(),
            view.getSelectedWeapon()), view.getSelectedDuration());
        Main.getMain().setScreen(new GameView(new GameController(game), GameAssetManager.getGameAssetManager().getSkin()));
    }
}

package com.untilldown.Controller;

import com.untilldown.Controller.MenuControllersInMain.*;
import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.View.MainMenuView;
import com.untilldown.View.MenusViewInMain.*;
import com.untilldown.View.StartMenuView;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void continueGame() {}

    public void goToProfile() {
        Main.getMain().setScreen(new ProfileView(new ProfileController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void goToPreGame() {
        Main.getMain().setScreen(new PreGameView(new PreGameController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void goToScoreBoard() {
        Main.getMain().setScreen(new ScoreBoardView(new ScoreBoardController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void goToSettings() {
        Main.getMain().setScreen(new SettingsView(new SettingsController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void goToTalent() {
        Main.getMain().setScreen(new TalentView(new TalentController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void logout() {
        App.updateUserInDatabase(App.getCurrentUser());
        App.setCurrentUser(null);
        Main.getMain().setScreen(new StartMenuView(new StartMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }
}

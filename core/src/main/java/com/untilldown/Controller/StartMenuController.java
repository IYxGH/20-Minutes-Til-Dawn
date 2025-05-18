package com.untilldown.Controller;

import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.Model.User;
import com.untilldown.View.LoginMenuView;
import com.untilldown.View.MainMenuView;
import com.untilldown.View.SignupMenuView;

public class StartMenuController {
    private SignupMenuView view;

    public void setView(SignupMenuView view) {
        this.view = view;
    }

    public void goToSignupMenu() {
        Main.getMain().setScreen(new SignupMenuView(new SignupMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void goToLoginMenu() {
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void playAsGuest() {
        App.setCurrentUser(new User(true));
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }
}

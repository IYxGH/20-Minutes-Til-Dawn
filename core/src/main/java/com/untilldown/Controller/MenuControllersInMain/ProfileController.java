package com.untilldown.Controller.MenuControllersInMain;

import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.Model.User;
import com.untilldown.View.MenusViewInMain.ProfileView;

public class ProfileController {
    private ProfileView view;

    public void setView(ProfileView view) {
        this.view = view;
    }

    public void changePassword(String password) {
        User currentUser = App.getCurrentUser();

        if (!User.isPasswordValid(password)) {
            view.getErrorChangePasswordLabel().setVisible(true);
        }

        currentUser.setPassword(password);
        Main.getMain().setScreen(new ProfileView(new ProfileController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }
}

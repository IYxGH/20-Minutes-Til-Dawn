package com.untilldown.Controller;

import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.Model.User;
import com.untilldown.View.ForgetPasswordMenuView;
import com.untilldown.View.LoginMenuView;
import com.untilldown.View.MainMenuView;
import com.untilldown.View.StartMenuView;

public class LoginMenuController{
    private LoginMenuView view;

    public void setView(LoginMenuView view) {
        this.view = view;
    }

    public void login(String username, String password){
        User user = App.findUser(username);
        if(user == null){
            view.showErrorMessage("There is no user with this username");
            return;
        }

        if (!password.equals(user.getPassword())){
            view.showErrorMessage("Wrong password");
            return;
        }

        App.setCurrentUser(user);
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void back() {
        Main.getMain().setScreen(new StartMenuView(new StartMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void goToForgetPasswordMenu(){
        Main.getMain().setScreen(new ForgetPasswordMenuView(new ForgetPasswordMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }
}

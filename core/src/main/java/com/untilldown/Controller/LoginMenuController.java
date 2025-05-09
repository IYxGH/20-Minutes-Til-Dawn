package com.untilldown.Controller;

import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.Model.User;
import com.untilldown.View.ForgetPasswordMenuView;
import com.untilldown.View.LoginMenuView;
import com.untilldown.View.StartMenuView;

public class LoginMenuController{
    private LoginMenuView view;

    public void setView(LoginMenuView view) {
        this.view = view;
    }

    public void login(String username, String password){
        User user = App.findUser(username);
        if(user == null){
            view.showErrorMessage("there is no user with this username");
            return;
        }

        if (!password.equals(user.getPassword())){
            view.showErrorMessage("wrong password");
            return;
        }

        App.setCurrentUser(user);
//        Main.getMain().setScreen();
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

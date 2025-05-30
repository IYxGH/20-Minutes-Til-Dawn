package com.untilldown.Controller;

import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.Model.User;
import com.untilldown.View.ForgetPasswordMenuView;
import com.untilldown.View.LoginMenuView;
import com.untilldown.View.MainMenuView;
import com.untilldown.View.StartMenuView;

public class ForgetPasswordMenuController {
    private ForgetPasswordMenuView view;

    public void setView(ForgetPasswordMenuView view) {
        this.view = view;
    }

    public void changePassword(String username, String answer, String password){
        User user = App.findUser(username);
        if(user == null){
            view.showErrorMessage("there is no user with this username");
            return;
        }

        if (!answer.equals(user.getSecurityAnswer())){
            view.showErrorMessage("wrong answer");
            return;
        }

        if (!User.isPasswordValid(password)){
            view.showErrorMessage("weak password");
            return;
        }

        user.setPassword(password);
        App.updateUserInDatabase(user);
        back();
    }

    public void back() {
        Main.getMain().setScreen(new LoginMenuView(new LoginMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }
}

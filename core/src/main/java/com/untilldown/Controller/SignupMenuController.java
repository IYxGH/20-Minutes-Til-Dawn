package com.untilldown.Controller;

import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.Model.User;
import com.untilldown.View.MainMenuView;
import com.untilldown.View.SignupMenuView;
import com.untilldown.View.StartMenuView;

public class SignupMenuController {
    private SignupMenuView view;

    public void setView(SignupMenuView signUpView) {
        this.view = signUpView;
    }

    public void handleRegisterInput(String username, String password) {
        if (username.length() < 2) {
            view.showErrorMessage("Username must be at least 2 characters");
            return;
        }

        if (App.findUser(username) != null) {
            view.showErrorMessage("Username is already in use!");
            return;
        }

        if (!User.isPasswordValid(password)) {
            view.showErrorMessage("Password is weak!");
            return;
        }

        User user = new User(username, password);
        App.getUsers().add(user);
        App.setCurrentUser(user);

        view.getSecurityQuestion().setVisible(true);
        view.getSecurityAnswerField().setVisible(true);
        view.getConfirmAnswerButton().setVisible(true);
        view.showSecurityQuestionDialog();
    }

    public void handleConfirmAnswerInput(String answer) {
        if (answer.length() < 2) {
            view.showErrorMessage("your Answer is too short!");
            return;
        }

        App.getCurrentUser().setSecurityAnswer(answer);
        goToMainMenu();

    }

    public void goToMainMenu() {
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }

    public void backToStartMenu() {
        Main.getMain().setScreen(new StartMenuView(new StartMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));
    }
}

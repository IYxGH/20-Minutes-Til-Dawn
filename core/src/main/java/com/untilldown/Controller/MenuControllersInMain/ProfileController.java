package com.untilldown.Controller.MenuControllersInMain;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.untilldown.Controller.StartMenuController;
import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.Enums.Message;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.Model.User;
import com.untilldown.View.MenusViewInMain.ProfileView;
import com.untilldown.View.StartMenuView;

import java.awt.*;

public class ProfileController {
    private ProfileView view;

    public void setView(ProfileView view) {
        this.view = view;
    }

    public void changePassword(Dialog dialog, String password) {
        User currentUser = App.getCurrentUser();

        if (!User.isPasswordValid(password)) {
            view.getErrorDialogLabel().setVisible(true);
            return;
        }

        currentUser.setPassword(password);
        App.updateUserInDatabase(currentUser);
        dialog.remove();
        view.showTemporaryMessage(Message.PASSWORD_CHANGED_SUCCESSFULLY.getMessage(), Color.GREEN);
    }

    public void changeName(Dialog dialog, String name) {
        User currentUser = App.getCurrentUser();

        if (name.length() < 3) {
            view.getErrorDialogLabel().setText(Message.USERNAME_IS_SHORT.getMessage());
            view.getErrorDialogLabel().setColor(Color.RED);
            view.getErrorDialogLabel().setVisible(true);
            return;
        }

        if (App.findUser(name) != null) {
            view.getErrorDialogLabel().setText(Message.USERNAME_IS_IN_USE.getMessage());
            view.getErrorDialogLabel().setColor(Color.RED);
            view.getErrorDialogLabel().setVisible(true);
            return;
        }

        String oldName = currentUser.getUsername();
        currentUser.setUsername(name);
        App.updateUserInDatabase(oldName,currentUser);
        dialog.remove();
        view.showTemporaryMessage(Message.USERNAME_CHANGED_SUCCESSFULLY.getMessage(), Color.GREEN);
    }

    public void deleteCurrentUserAccount(Dialog dialog) {
        User currentUser = App.getCurrentUser();
        App.removeUserFromDatabase(currentUser.getUsername());
        App.setCurrentUser(null);
        dialog.remove();

        Main.getMain().setScreen(new StartMenuView(new StartMenuController(),
            GameAssetManager.getGameAssetManager().getSkin()));

    }

    public void updateAvatar(User user, Dialog dialog, String successMessage) {
        if (user == null) {
            // Handle case where user is not logged in (shouldn't happen if UI is correct)
            view.showTemporaryMessage(Message.LOGIN_REQUIRED_AVATAR.getMessage(), Color.RED);
            dialog.remove();
            return;
        }

        App.updateUserInDatabase(user);
        dialog.remove();
        view.showTemporaryMessage(successMessage, Color.GREEN);
        // Important: Update the avatar display in the ProfileView's main screen
        view.updateCurrentAvatarDisplay();
    }
}

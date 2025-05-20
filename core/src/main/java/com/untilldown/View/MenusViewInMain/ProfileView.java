package com.untilldown.View.MenusViewInMain;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.untilldown.Controller.MainMenuController;
import com.untilldown.Controller.MenuControllersInMain.ProfileController;
import com.untilldown.Main;
import com.untilldown.Model.Enums.Message;
import com.untilldown.View.MainMenuView;

public class ProfileView implements Screen {
    private final ProfileController controller;
    private final Skin skin;
    private Stage stage;
    private float height;
    private float width;

    private TextButton changeUsernameButton;
    private TextButton changePasswordButton;
    private TextButton changeAvatarButton;
    private TextButton deleteAccountButton;
    private TextButton backButton;

    // Error labels
    private Label errorDialogLabel;






    public ProfileView(ProfileController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
        this.skin = skin;
        this.height = Gdx.graphics.getHeight();
        this.width = Gdx.graphics.getWidth();

        stage = new Stage();

        changeUsernameButton = new TextButton(Message.CHANGE_USERNAME.getMessage(), skin);
        changePasswordButton = new TextButton(Message.CHANGE_PASSWORD.getMessage(), skin);
        changeAvatarButton = new TextButton(Message.CHANGE_AVATAR.getMessage(), skin);
        deleteAccountButton = new TextButton(Message.DELETE_ACCOUNT.getMessage(), skin);
        backButton = new TextButton(Message.BACK.getMessage(), skin);
        errorDialogLabel = new Label("", skin);
        errorDialogLabel.setColor(Color.RED);

        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showChangeUsername();
            }
        });

        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showChangePassword();
            }
        });

        changeAvatarButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showChangeAvatar();
            }
        });

        deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showDeleteAccount();
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });
    }

    public void showChangeUsername() {
        Dialog dialog = new Dialog(Message.CHANGE_USERNAME.getMessage(), skin);
        dialog.getTitleLabel().setColor(Color.GREEN);
        dialog.getContentTable().padBottom(5);

        Label usernameLabel = new Label(Message.ENTER_NEW_USERNAME.getMessage(), skin);
        TextField usernameField = new TextField("", skin);
        usernameField.setMessageText(Message.NEW_USERNAME.getMessage());
        TextButton setButton = new TextButton(Message.ENTER_NEW_USERNAME.getMessage(), skin);
        TextButton backButton = new TextButton(Message.BACK.getMessage(), skin);
        usernameField.setWidth(setButton.getWidth());

        dialog.getContentTable().add(usernameLabel).padBottom(5).row();
        dialog.getContentTable().add(usernameField).width(100).pad(4).row();
        dialog.getContentTable().add(errorDialogLabel).row();
        errorDialogLabel.setVisible(false);
        dialog.getContentTable().add(setButton).pad(2).row();
        dialog.getContentTable().add(backButton).pad(2).row();
        dialog.center();
        dialog.getContentTable().center();

        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.show(stage);
        dialog.center();
        dialog.getTitleTable().padTop(20).padBottom(20);
        dialog.getButtonTable().center();
        dialog.getTitleLabel().setFontScale(1.2f);
        dialog.getTitleLabel().setAlignment(Align.center);

        setButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changeName(dialog, usernameField.getText());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.remove();
            }
        });


    }

    public void showChangePassword() {
        Dialog dialog = new Dialog("Change Password", skin);
        dialog.getTitleLabel().setColor(Color.GREEN);
        dialog.getContentTable().padBottom(5);

        Label passwordLabel = new Label(Message.ENTER_NEW_PASSWORD.getMessage(), skin);
        errorDialogLabel = new Label(Message.PASSWORD_IS_WEAK.getMessage(), skin);
        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText(Message.NEW_PASSWORD.getMessage());
        TextButton setButton = new TextButton(Message.ENTER_NEW_PASSWORD.getMessage(), skin);
        TextButton backButton = new TextButton(Message.BACK.getMessage(), skin);

        dialog.getContentTable().add(passwordLabel).padBottom(5).row();
        dialog.getContentTable().add(passwordField).width(100).pad(4).row();
        dialog.getContentTable().add(errorDialogLabel).row();
        errorDialogLabel.setVisible(false);
        dialog.getContentTable().add(setButton).pad(2).row();
        dialog.getContentTable().add(backButton).pad(2).row();
        dialog.center();
        dialog.getContentTable().center();

        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.show(stage);
        dialog.center();
        dialog.getTitleTable().padTop(20).padBottom(20);
        dialog.getButtonTable().center();
        dialog.getTitleLabel().setFontScale(1.2f);
        dialog.getTitleLabel().setAlignment(Align.center);

        setButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changePassword(dialog, passwordField.getText());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.remove();
            }
        });


    }

    public void showChangeAvatar() {}

    public void showDeleteAccount() {}

    public void showTemporaryMessage(String message, Color color) {
        Label tempLabel = new Label(message, skin);
        tempLabel.setColor(color);
        tempLabel.setPosition(width / 2 - tempLabel.getWidth() / 2, 10);
        tempLabel.addAction(
            Actions.sequence(
                Actions.fadeIn(0.3f),
                Actions.delay(2f),
                Actions.fadeOut(0.3f),
                Actions.removeActor()
            )
        );
        stage.addActor(tempLabel);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);


        float buttonWidth = width * 0.2f;
        float buttonHeight = height * 0.15f;
        float heightPad = height * 0.02f;

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setFillParent(true);
        table.center();

        table.add(changeUsernameButton).width(buttonWidth).height(buttonHeight).expandX().fillX();
        table.row().pad(heightPad);
        table.add(changePasswordButton).width(buttonWidth).height(buttonHeight);
        table.row().pad(heightPad);
        table.add(changeAvatarButton).width(buttonWidth).height(buttonHeight);
        table.row().pad(heightPad);
        table.add(deleteAccountButton).width(buttonWidth).height(buttonHeight);
        table.row().pad(heightPad);
        table.add(backButton).width(buttonWidth).height(buttonHeight);
        table.row().pad(heightPad);

        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public ProfileController getController() {
        return controller;
    }

    public Skin getSkin() {
        return skin;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public TextButton getChangeUsernameButton() {
        return changeUsernameButton;
    }

    public void setChangeUsernameButton(TextButton changeUsernameButton) {
        this.changeUsernameButton = changeUsernameButton;
    }

    public TextButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public void setChangePasswordButton(TextButton changePasswordButton) {
        this.changePasswordButton = changePasswordButton;
    }

    public TextButton getChangeAvatarButton() {
        return changeAvatarButton;
    }

    public void setChangeAvatarButton(TextButton changeAvatarButton) {
        this.changeAvatarButton = changeAvatarButton;
    }

    public TextButton getDeleteAccountButton() {
        return deleteAccountButton;
    }

    public void setDeleteAccountButton(TextButton deleteAccountButton) {
        this.deleteAccountButton = deleteAccountButton;
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public void setBackButton(TextButton backButton) {
        this.backButton = backButton;
    }

    public Label getErrorDialogLabel() {
        return errorDialogLabel;
    }

    public void setErrorDialogLabel(Label errorDialogLabel) {
        this.errorDialogLabel = errorDialogLabel;
    }
}

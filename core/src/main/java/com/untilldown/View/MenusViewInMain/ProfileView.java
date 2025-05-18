package com.untilldown.View.MenusViewInMain;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

    private Label errorChangePasswordLabel;





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

        });

        deleteAccountButton.addListener(new ClickListener() {

        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });
    }

    public void showChangeUsername() {
        Dialog dialog = new Dialog("Change Username", skin);


    }

    public void showChangePassword() {
        Dialog dialog = new Dialog("Change Password", skin);

        Label passwordLabel = new Label(Message.ENTER_NEW_PASSWORD.getMessage(), skin);
        errorChangePasswordLabel = new Label(Message.PASSWORD_IS_WEAK.getMessage(), skin);
        TextField passwordField = new TextField(Message.NEW_PASSWORD    .getMessage(), skin);
        TextButton setButton = new TextButton(Message.ENTER_NEW_PASSWORD.getMessage(), skin);

        dialog.getContentTable().add(passwordLabel);
        dialog.getContentTable().add(passwordField).width(width / 12).pad(width / 60).row();
        dialog.getContentTable().add(errorChangePasswordLabel).row();
        errorChangePasswordLabel.setColor(Color.RED);
        errorChangePasswordLabel.setVisible(false);
        dialog.getContentTable().add(setButton).width(width / 12).pad(width / 60).row();


        setButton.addListener(new ClickListener() {

        });

    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);


        float buttonWidth = width * 0.2f;
        float buttonHeight = height * 0.15f;
        float heightPad = height * 0.2f;

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

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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

    public Label getErrorChangePasswordLabel() {
        return errorChangePasswordLabel;
    }

    public void setErrorChangePasswordLabel(Label errorChangePasswordLabel) {
        this.errorChangePasswordLabel = errorChangePasswordLabel;
    }
}

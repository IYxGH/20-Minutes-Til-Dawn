package com.untilldown.View.MenusViewInMain;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
        Dialog dialog = new Dialog("Change Username", skin);


    }

    public void showChangePassword() {
        Dialog dialog = new Dialog("Change Password", skin);
        dialog.getTitleLabel().setColor(Color.GREEN);
        dialog.getContentTable().padBottom(5);

        Label passwordLabel = new Label(Message.ENTER_NEW_PASSWORD.getMessage(), skin);
        errorChangePasswordLabel = new Label(Message.PASSWORD_IS_WEAK.getMessage(), skin);
        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText(Message.NEW_PASSWORD.getMessage());
        TextButton setButton = new TextButton(Message.ENTER_NEW_PASSWORD.getMessage(), skin);
        TextButton backButton = new TextButton(Message.BACK.getMessage(), skin);

        dialog.getContentTable().add(passwordLabel).padBottom(5).row();
        dialog.getContentTable().add(passwordField).pad(4).row();
        dialog.getContentTable().add(errorChangePasswordLabel).row();
        errorChangePasswordLabel.setColor(Color.RED);
        errorChangePasswordLabel.setVisible(false);
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
                controller.changePassword(passwordField.getText());
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

    public Label getErrorChangePasswordLabel() {
        return errorChangePasswordLabel;
    }

    public void setErrorChangePasswordLabel(Label errorChangePasswordLabel) {
        this.errorChangePasswordLabel = errorChangePasswordLabel;
    }
}

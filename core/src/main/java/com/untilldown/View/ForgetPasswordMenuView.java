package com.untilldown.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilldown.Controller.ForgetPasswordMenuController;

public class ForgetPasswordMenuView implements Screen {
    private Stage stage;
    private TextField username;
    private TextField answer;
    private TextField password;
    private Label errorMessage;
    private TextButton changePasswordButton;
    private TextButton backButton;


    private final Skin skin;
    private final ForgetPasswordMenuController controller;

    public ForgetPasswordMenuView(ForgetPasswordMenuController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
        this.skin = skin;

        username = new TextField("", skin);
        answer = new TextField("", skin);
        password = new TextField("", skin);
        errorMessage = new Label("", skin);
        errorMessage.setColor(Color.RED);
        errorMessage.setVisible(false);
        changePasswordButton = new TextButton("Change Password", skin);
        backButton = new TextButton("Back", skin);


        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.changePassword(username.getText(), answer.getText(), password.getText());
            }
        });
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.back();
            }
        });

    }

    public void showSuccessfulMessage(String message) {
        Dialog dialog = new Dialog(message, skin);
        dialog.show(stage);
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float objectWidth = screenWidth * 0.2f;


        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(new Label("Username:", skin));
        table.row().pad(0.01f * screenHeight);
        table.add(username).expandX().width(objectWidth).height(0.07f * screenHeight);
        table.row().padTop(0.02f * screenHeight);

        table.add(new Label("Whats your favorite club?", skin));
        table.row().pad(0.01f * screenHeight);
        table.add(answer).expandX().width(objectWidth).height(0.07f * screenHeight);
        table.row().padTop(0.02f * screenHeight);

        table.add(new Label("New Password:", skin));
        table.row().pad(0.01f * screenHeight);
        table.add(password).expandX().width(objectWidth).height(0.07f * screenHeight);
        table.row().padTop(0.02f * screenHeight);
        table.add(errorMessage).expandX();
        table.row().padTop(0.03f * screenHeight);

        table.add(changePasswordButton).expandX().width(objectWidth).height(0.1f * screenHeight);
        table.row().padTop(0.02f * screenHeight);
        table.add(backButton).expandX().width(objectWidth).height(0.1f * screenHeight);
        table.row().padTop(0.02f * screenHeight);
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

    public void showErrorMessage(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }
}

package com.untilldown.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.untilldown.Controller.LoginMenuController;

public class LoginMenuView implements Screen {
    private Stage stage;
    private TextField username;
    private TextField password;
    private Label errorMessage;
    private TextButton loginButton;
    private TextButton forgetPasswordButton;
    private TextButton backButton;


    private final Skin skin;
    private final LoginMenuController controller;

    public LoginMenuView(LoginMenuController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
        this.skin = skin;

        username = new TextField("", skin);
        password = new TextField("", skin);
        errorMessage = new Label("", skin);
        errorMessage.setVisible(false);
        loginButton = new TextButton("Login", skin);
        forgetPasswordButton = new TextButton("Forget Password", skin);
        backButton = new TextButton("Back", skin);


        loginButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.login(username.getText(), password.getText());
            }
        });

        forgetPasswordButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.back();
            }
        });

    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float objectWidth = screenWidth * 0.25f;


        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(username).expandX().width(objectWidth).height(0.07f * screenHeight);
        table.row().padTop(0.02f * screenHeight);
        table.add(password).expandX().width(objectWidth).height(0.07f * screenHeight);
        table.row().padTop(0.02f * screenHeight);
        table.add(errorMessage).expandX();
        table.row().padTop(0.03f * screenHeight);

        table.add(loginButton).expandX().width(objectWidth).height(0.1f * screenHeight);
        table.row().padTop(0.02f * screenHeight);
        table.add(forgetPasswordButton).expandX().width(objectWidth).height(0.1f * screenHeight);
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

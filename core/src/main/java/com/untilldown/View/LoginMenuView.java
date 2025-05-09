package com.untilldown.View;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.untilldown.Controller.LoginMenuController;

public class LoginMenuView implements Screen {
    private Stage stage;


    private final Skin skin;
    private final LoginMenuController controller;

    public LoginMenuView(LoginMenuController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
    }

    @Override
    public void show() {

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
}

package com.untilldown.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilldown.Controller.StartMenuController;
import com.untilldown.Model.GameAssetManager;

public class StartMenuView implements Screen {
    private Stage stage;
    private Skin skin;
    private TextButton signUpButton;
    private TextButton loginButton;
    private TextButton playAsGuestButton;
    private Label titleLabel;

    private final StartMenuController controller;

    public StartMenuView(StartMenuController controller, Skin skin) {
        this.skin = skin;
        this.controller = controller;
        signUpButton = new TextButton("Sign Up", skin);
        loginButton = new TextButton("Login", skin);
        playAsGuestButton = new TextButton("Play As Guest", skin);
        titleLabel = new Label("20 MINUTES TILL DOWN", skin);
        titleLabel.setFontScale(2.5f);
        titleLabel.setColor(Color.CYAN);

        signUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToSignupMenu();
            }
        });

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.goToLoginMenu();
            }
        });

        playAsGuestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.playAsGuest();
            }
        });
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();


        Table table = new Table();
        table.top().padTop(screenHeight * 0.1f);
        table.setFillParent(true);
        table.center();

        table.add(titleLabel).expandX().center().padBottom(screenHeight * 0.1f);
        table.row();
        table.add(signUpButton).width(screenWidth * 0.25f).height(screenHeight * 0.1f);
        table.row().pad(screenHeight * 0.04f);
        table.add(loginButton).width(screenWidth * 0.25f).height(screenHeight * 0.1f);
        table.row().pad(screenHeight * 0.04f);
        table.add(playAsGuestButton).width(screenWidth * 0.25f).height(screenHeight * 0.1f);
//        table.row().padBottom(screenHeight * 0.04f);

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
}

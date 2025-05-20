package com.untilldown.View.MenusViewInMain;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.untilldown.Controller.MenuControllersInMain.PreGameController;

public class PreGameView implements Screen {
    private final PreGameController controller;
    private final Skin skin;
    private Stage stage;

    public PreGameView(PreGameController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();

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

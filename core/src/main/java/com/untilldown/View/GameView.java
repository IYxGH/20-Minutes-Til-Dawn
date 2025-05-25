package com.untilldown.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.untilldown.Controller.GameController;

public class GameView implements Screen, InputProcessor {
    private Stage stage;
    private GameController controller;
    private OrthographicCamera camera;
    private Viewport viewport;

    private final float WORLD_WIDTH = 3500;
    private final float WORLD_HEIGHT = 1500;

    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);

        camera = new OrthographicCamera();
        viewport = new FitViewport(800, 600, camera);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        controller.initGame(stage);

    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        updateCamera();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    private void updateCamera() {
        Vector2 heroPosition = controller.getHeroPosition();
        float cameraX = MathUtils.clamp(heroPosition.x, camera.viewportWidth / 2, WORLD_WIDTH - camera.viewportWidth / 2);
        float cameraY = MathUtils.clamp(heroPosition.y, camera.viewportHeight / 2, WORLD_HEIGHT - camera.viewportHeight / 2);
        camera.position.set(cameraX, cameraY, 0);
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}

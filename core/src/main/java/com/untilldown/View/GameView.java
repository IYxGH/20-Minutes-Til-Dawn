package com.untilldown.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.untilldown.Controller.GameController;
import com.untilldown.Model.Enums.Message;

public class GameView implements Screen, InputProcessor {
    private Skin skin;
    private Stage gameStage;
    private Stage uiStage;
    private Viewport gameViewport;
    private Viewport uiViewport;
    private GameController controller;
    private OrthographicCamera camera;

    private Table uiTable;
    private TextButton pauseButton;
    private Label infoLabel;
    private ProgressBar progressBar;
    private Table pauseMenu;

    private final float WORLD_WIDTH = 3040;
    private final float WORLD_HEIGHT = 1856;

    public GameView(GameController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
        this.skin = skin;

        camera = new OrthographicCamera();
        gameViewport = new FitViewport(800, 600, camera);
        uiViewport = new ScreenViewport();
        gameStage = new Stage(gameViewport);
        uiStage = new Stage(uiViewport);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(gameStage);
        Gdx.input.setInputProcessor(multiplexer);

        setupUiStage();
        setupPauseMenu();

        controller.initGame(gameStage);
    }

    private void setupUiStage() {
        uiTable = new Table();
        pauseButton = new TextButton("Pause", skin);
        infoLabel = new Label("", skin);

        progressBar = new ProgressBar(0, 100, 0.01f, false, skin);
        progressBar.setValue(0);

        uiTable.setFillParent(true);
        uiTable.add(pauseButton);
        uiTable.row().pad(10);
        uiTable.add(progressBar).width(Gdx.graphics.getWidth() * 0.15f);
        uiTable.row().pad(10);
        uiTable.add(infoLabel);
        uiTable.row().pad(10);

        uiTable.top().left();
        uiStage.addActor(uiTable);

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.pause();
            }
        });

    }

    private void setupPauseMenu() {
        pauseMenu = new Table();
        pauseMenu.setFillParent(true);
        pauseMenu.setVisible(false);


        TextButton resumeButton = new TextButton(Message.RESUME.getMessage(), skin);
        TextButton giveUpButton = new TextButton(Message.GIVE_UP.getMessage(), skin);

        pauseMenu.center();
        pauseMenu.add(resumeButton).pad(15);
        pauseMenu.row();
        pauseMenu.add(giveUpButton).pad(15);

        uiStage.addActor(pauseMenu);

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.resume();
            }
        });

        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.giveUp();
            }
        });

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        controller.update(delta);
        camera.position.set(controller.getHeroPosition().x, controller.getHeroPosition().y, 0);
        camera.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameStage.act(delta);
        gameStage.draw();
        uiStage.act(delta);
        uiStage.draw();
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
        gameViewport.update(width, height);
        uiViewport.update(width, height);
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

    public Label getInfoLabel() {
        return infoLabel;
    }

    public void setInfoLabel(Label infoLabel) {
        this.infoLabel = infoLabel;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Table getPauseMenu() {
        return pauseMenu;
    }

    public void setPauseMenu(Table pauseMenu) {
        this.pauseMenu = pauseMenu;
    }

    public Table getUiTable() {
        return uiTable;
    }
}

package com.untilldown.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.untilldown.Controller.GameController;
import com.untilldown.Controller.MainMenuController;
import com.untilldown.Controller.ModelControllers.SFXController;
import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.Enums.Ability;
import com.untilldown.Model.Enums.Message;
import com.untilldown.Model.Game;
import com.untilldown.Model.Player;

import java.util.HashMap;

public class GameView implements Screen, InputProcessor {
    private Skin skin;
    private Stage gameStage;
    private Stage uiStage;
    private Viewport gameViewport;
    private Viewport uiViewport;
    private GameController controller;
    private OrthographicCamera camera;
    private Label infoInPauseMenu;

    private Table uiTable;
    private Table abilitiesTable;
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
        setupAbilitiesTable();

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
        infoInPauseMenu = new Label("", skin);

        pauseMenu.center();
        pauseMenu.add(resumeButton).pad(15);
        pauseMenu.row();
        pauseMenu.add(giveUpButton).pad(15);
        pauseMenu.row();
        pauseMenu.add(infoInPauseMenu).pad(25);
        pauseMenu.row();

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

    private void setupAbilitiesTable() {
        Game game = App.getActiveGame();
        Player player = game.getPlayer();
        abilitiesTable = new Table();
        abilitiesTable.setFillParent(true);
        abilitiesTable.setVisible(false);

        Label label = new Label(Message.CHOOSE_ABILITY.getMessage(), skin);
        label.setColor(Color.RED);
        label.setScale(1.5f);
        abilitiesTable.add(label).pad(10);
        abilitiesTable.row();

        Ability[] abilityList =  Ability.get3Random();
        TextButton[] abilityButtons = new TextButton[abilityList.length];
        HashMap<TextButton, Ability> abilitiesMap = new HashMap<>();

        for (int i = 0; i < abilityList.length; i++) {
            abilityButtons[i] = new TextButton(abilityList[i].getMessage(), skin);
            TextButton clickedButton = abilityButtons[i];
            abilitiesMap.put(abilityButtons[i], abilityList[i]);

            clickedButton.setScale(1);
            clickedButton.setColor(1,1,1,0.5f);



            abilityButtons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    SFXController.LevelUpSound();
                    Ability ability = abilitiesMap.get(clickedButton);
                    ability.useAbility(game, player);
                    abilitiesTable.setVisible(false);
                    player.getCollectedAbilities().put(ability,
                        player.getCollectedAbilities().get(ability) + 1);
                    controller.setPaused(false);
                }

                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    clickedButton.setScale(1.2f);
                    clickedButton.setColor(1,1,1,1);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    clickedButton.setScale(1);
                    clickedButton.setColor(1,1,1,0.5f);
                }
            });

            abilitiesTable.add(clickedButton);
            abilitiesTable.row().pad(10);
        }

        abilitiesTable.center();

        uiStage.addActor(abilitiesTable);


    }

    public void showAbilities() {
        setupAbilitiesTable();
        controller.setPaused(true);
        abilitiesTable.setVisible(true);
    }

    public void showEndGame() {
        pauseMenu.setVisible(false);
        Player player = App.getActiveGame().getPlayer();
        Game game = App.getActiveGame();
        Dialog dialog = new Dialog("End Game", skin);

        Label result = new Label("", skin);
        result.setFontScale(2);

        if (game.getDuration() == game.getTime()) {
            result.setColor(Color.GREEN);
            result.setText(Message.WON.getMessage());
        } else {
            result.setColor(Color.RED);
            result.setText(Message.DEAD.getMessage());
        }

        Label usernameLabel = new Label(player.getUser().getUsername(), skin);
        Label timeLeft = new Label(Message.TIME_LEFT.getMessage() + " " + game.getTimeLeft() , skin);
        Label kills = new Label(Message.KILLS.getMessage() + player.getKills(), skin);

        TextButton continueButton = new TextButton(Message.CONTINUE.getMessage(), skin);
        TextButton cheatButton = new TextButton("Hmmm...", skin);

        dialog.getContentTable().add(result).pad(30).row();
        dialog.getContentTable().add(usernameLabel).pad(10).row();
        dialog.getContentTable().add(timeLeft).pad(10).row();
        dialog.getContentTable().add(kills).pad(10).row();
        dialog.getContentTable().add(continueButton).pad(10).row();
        if (game.getDuration() > game.getTime()) {
            dialog.getContentTable().add(cheatButton).pad(10).row();
        }

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.finishGame();
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });

        cheatButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.setTimePastLastDamage(2);
                player.setHp(2);
                dialog.remove();
                controller.setPaused(false);
            }
        });

        dialog.setMovable(false);
        dialog.setResizable(false);
        dialog.show(uiStage);
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

    public Stage getGameStage() {
        return gameStage;
    }

    public void setGameStage(Stage gameStage) {
        this.gameStage = gameStage;
    }

    public Stage getUiStage() {
        return uiStage;
    }

    public void setUiStage(Stage uiStage) {
        this.uiStage = uiStage;
    }

    public Label getInfoInPauseMenu() {
        return infoInPauseMenu;
    }

    public void setInfoInPauseMenu(Label infoInPauseMenu) {
        this.infoInPauseMenu = infoInPauseMenu;
    }

    public void setUiTable(Table uiTable) {
        this.uiTable = uiTable;
    }

    public Table getAbilitiesTable() {
        return abilitiesTable;
    }

    public void setAbilitiesTable(Table abilitiesTable) {
        this.abilitiesTable = abilitiesTable;
    }

    public TextButton getPauseButton() {
        return pauseButton;
    }

    public void setPauseButton(TextButton pauseButton) {
        this.pauseButton = pauseButton;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public float getWORLD_WIDTH() {
        return WORLD_WIDTH;
    }

    public float getWORLD_HEIGHT() {
        return WORLD_HEIGHT;
    }
}

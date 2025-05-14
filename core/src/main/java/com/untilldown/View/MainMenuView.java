package com.untilldown.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilldown.Controller.MainMenuController;
import com.untilldown.Model.App;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.Model.User;

public class MainMenuView implements Screen {
    private Stage stage;
    Skin skin;
    private final TextButton continueGameButton;
    private final TextButton preGameButton;
    private final TextButton settingsButton;
    private final TextButton profileButton;
    private final TextButton scoreBoardButton;
    private final TextButton talentMenuButton;
    private final TextButton logoutButton;
    private final Label gameTitle;
    private final Table table;
    private final MainMenuController controller;

    private Image avatarImage;
    private Label userInfoLabel;

    public MainMenuView(MainMenuController controller, Skin skin) {
        this.skin = skin;
        this.controller = controller;
        this.gameTitle = new Label("MAIN MENU", skin, "title");
        this.table = new Table();
        this.continueGameButton = new TextButton("Continue Saved Game", skin);
        this.preGameButton = new TextButton("Pre-Game", skin);
        this.settingsButton = new TextButton("Settings", skin);
        this.profileButton = new TextButton("Profile", skin);
        this.scoreBoardButton = new TextButton("Scoreboard", skin);
        this.talentMenuButton = new TextButton("Talents", skin);
        this.logoutButton = new TextButton("Logout", skin);

        controller.setView(this);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        User currentUser = App.getCurrentUser();
//        avatarImage = GameAssetManager.getGameAssetManager().getAvatarImage(currentUser.getAvatarAssignedID());
//        avatarImage.setSize(64, 64);

        userInfoLabel = new Label("User: " + currentUser.getUsername(), skin);

        Table topLeftTable = new Table();
        topLeftTable.top().left().pad(10);
        topLeftTable.setFillParent(true);

        Table userInfoTable = new Table();
        userInfoTable.add(avatarImage).padRight(10);
        userInfoTable.add(userInfoLabel).left().top();
        topLeftTable.add(userInfoTable).left().top();

        stage.addActor(topLeftTable);

        // Center button layout
        table.setFillParent(true);
        table.center();

        float buttonWidth = screenWidth * 0.3f;
        float buttonHeight = screenHeight * 0.09f;
        float spacing = screenHeight * 0.02f;

        table.add(gameTitle).padBottom(spacing * 2);
        table.row();
        table.add(continueGameButton).width(buttonWidth).height(buttonHeight).padBottom(spacing);
        table.row();
        table.add(preGameButton).width(buttonWidth).height(buttonHeight).padBottom(spacing);
        table.row();
        table.add(settingsButton).width(buttonWidth).height(buttonHeight).padBottom(spacing);
        table.row();
        table.add(profileButton).width(buttonWidth).height(buttonHeight).padBottom(spacing);
        table.row();
        table.add(scoreBoardButton).width(buttonWidth).height(buttonHeight).padBottom(spacing);
        table.row();
        table.add(talentMenuButton).width(buttonWidth).height(buttonHeight).padBottom(spacing);
        table.row();
        table.add(logoutButton).width(buttonWidth).height(buttonHeight).padBottom(spacing);

        stage.addActor(table);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(v);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }
}

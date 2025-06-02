package com.untilldown.View.MenusViewInMain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilldown.Controller.MainMenuController;
import com.untilldown.Controller.MenuControllersInMain.SettingsController;
import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.Enums.Action;
import com.untilldown.Model.Enums.Message;
import com.untilldown.Model.Enums.MusicName;
import com.untilldown.View.MainMenuView;

import java.util.ArrayList;

public class SettingsView implements Screen {
    private final SettingsController controller;
    private Skin skin;
    private Stage stage;
    private Slider volumeSlider;
    private Table table;
    private Table actionsTable;
    private CheckBox musicCheckBox;
    private CheckBox SFXCheckBox;
    private CheckBox autoReloadCheckBox;
    private ArrayList<TextButton> musicButtons;
    private ArrayList<TextButton> actionButtons;
    private TextButton languageButton;
    private TextButton backButton;

    public SettingsView(SettingsController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
        this.skin = skin;

        volumeSlider = new Slider(0, 1, 0.05f, false, skin);
        volumeSlider.setValue(App.getCurrentMusic().getVolume());
        musicCheckBox = new CheckBox(Message.MUSIC.getMessage(), skin);
        musicCheckBox.setChecked(App.getCurrentMusic().isPlaying());
        SFXCheckBox = new CheckBox(Message.SFX.getMessage(), skin);
        SFXCheckBox.setChecked(App.isSFXon());
        languageButton = new TextButton(App.getCurrentLanguage().name(), skin);

        actionButtons = new ArrayList<>();

        //change action controllers
        for (Action action : Action.values()) {
            TextButton button = new TextButton(action.getInfo(), skin);
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    showChangeAction(action);
                }
            });
            actionButtons.add(button);
        }

        // change music
        musicButtons = new ArrayList<>();

        for (MusicName musicName : MusicName.values()) {
            TextButton button = new TextButton(musicName.getName(), skin);

            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.changeMusic(musicName);
                }
            });
            musicButtons.add(button);
        }

        backButton = new TextButton(Message.BACK.getMessage(), skin);


        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float value = volumeSlider.getValue();
                App.setVolume(value);
            }
        });

        musicCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.toggleMusic();
            }
        });

        SFXCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                App.toggleSFXon();
            }
        });

        languageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.toggleLanguage();
                languageButton.setText(App.getCurrentLanguage().name());
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });
    }

    public void showTemporaryMessage(String message, Color color) {
        Label tempLabel = new Label(message, skin);
        tempLabel.setColor(color);
        tempLabel.setPosition((float) Gdx.graphics.getWidth() / 2 - tempLabel.getWidth() / 2, 10);
        tempLabel.addAction(
            Actions.sequence(
                Actions.fadeIn(0.3f),
                Actions.delay(2f),
                Actions.fadeOut(0.3f),
                Actions.removeActor()
            )
        );
        stage.addActor(tempLabel);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        table = new Table();
        table.setFillParent(true);
        table.left();

        actionsTable = new Table();
        actionsTable.setFillParent(true);
        actionsTable.right();


        table.add(new Label(Message.VOLUME.getMessage(), skin)).padRight(10);
        table.add(volumeSlider).width(200);
        table.row();
        for (TextButton button : musicButtons) {
            table.add(button).pad(20);
        }
        table.row();
        table.add(musicCheckBox).pad(20);
        table.add(SFXCheckBox).pad(20);
        table.row();

        table.add(languageButton).center().pad(20);
        table.row();

        table.add(backButton).pad(30);
        table.row();

        stage.addActor(table);

        int tofCounter = 0;
        for (TextButton button : actionButtons) {
            actionsTable.add(button).pad(12);
            actionsTable.setHeight(button.getHeight() + 1);
            tofCounter++;
            if (tofCounter % 3 == 0) actionsTable.row();
        }
        stage.addActor(actionsTable);
    }

    public void showChangeAction(final Action action) {
        // 1) Show a dialog prompting the user
        final Dialog dialog = new Dialog("Rebind “" + action.getInfo() + "”", skin);
        dialog.getContentTable().add(new Label(Message.PRESS_ANY_KEY_TO_BIND.getMessage(), skin)).pad(20);
        TextButton button = new TextButton(action.getInfo(), skin);
        dialog.getContentTable().add(button).pad(20);
        dialog.show(stage);

        // 2) Save the old input processor so we can restore later
        final InputProcessor oldProcessor = Gdx.input.getInputProcessor();

        // 3) Install a temporary InputAdapter to catch the next key press
        InputAdapter rebindProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                // Update the mapping
                controller.changeButton(action, keycode);

                // Cleanup: restore the old processor and hide the dialog
                Gdx.input.setInputProcessor(oldProcessor);
                dialog.hide();
                return true;
            }
        };

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.remove();
                Gdx.input.setInputProcessor(oldProcessor);
            }
        });


        Gdx.input.setInputProcessor(rebindProcessor);
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

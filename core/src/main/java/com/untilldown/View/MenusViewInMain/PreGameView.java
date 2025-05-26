package com.untilldown.View.MenusViewInMain;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilldown.Controller.GameController;
import com.untilldown.Controller.MainMenuController;
import com.untilldown.Controller.MenuControllersInMain.PreGameController;
import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.Enums.Hero;
import com.untilldown.Model.Enums.Message;
import com.untilldown.Model.Enums.WeaponType;
import com.untilldown.Model.Game;
import com.untilldown.Model.Player;
import com.untilldown.Model.Weapon;
import com.untilldown.View.GameView;
import com.untilldown.View.MainMenuView;

public class PreGameView implements Screen {
    private final PreGameController controller;
    private final Skin skin;
    private ImageButton[] characterButtons;
    private Stage stage;
    private Hero selectedHero = Hero.SHANA;
    private WeaponType selectedWeapon = WeaponType.REVOLVER;
    private float selectedDuration = 5;

    private Array<ImageButton> heroButtons = new Array<>();
    private Array<TextButton> weaponButtons = new Array<>();
    private Array<TextButton> timeButtons = new Array<>();


    public PreGameView(PreGameController controller, Skin skin) {
        this.controller = controller;
        controller.setView(this);
        this.skin = skin;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);


        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        float screenWidth = Gdx.graphics.getWidth();
        float imageSize = screenWidth * 0.15f;


        Table heroTable = new Table();
        //  Hero Portraits with Info
        for (Hero hero : Hero.values()) {
            TextureRegionDrawable heroDrawable = new TextureRegionDrawable(
                new TextureRegion(hero.getPortrairTexture())
            );
            ImageButton heroButton = new ImageButton(heroDrawable);
            heroButton.getImageCell().size(imageSize);

            heroButtons.add(heroButton);
            heroButton.setColor(1, 1, 1, 0.3f);

            heroButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedHero = hero;
                    for (ImageButton heroButton : heroButtons) {
                        heroButton.setColor(1,1,1,0.3f);
                    }
                    heroButton.setColor(1,1,1,1);
                }
            });

            VerticalGroup heroGroup = new VerticalGroup();
            heroGroup.space(4);
            heroGroup.addActor(heroButton);
            Label heroLabel = new Label(hero.name(), skin);
            heroLabel.setColor(Color.CYAN);
            heroGroup.addActor(heroLabel);
            heroGroup.addActor(new Label(Message.HP + ": " + String.valueOf(hero.getHp()), skin));
            heroGroup.addActor(new Label(Message.SPEED.getMessage() + ": " + String.valueOf(hero.getSpeed()), skin));

            heroTable.add(heroGroup).pad(10);
        }
        root.add(heroTable).pad(10).row();

        // Weapon Selection
        Table weaponTable = new Table();
        for (WeaponType weapon : WeaponType.values()) {
            TextButton weaponButton = new TextButton(weapon.getName(), skin);


            weaponButtons.add(weaponButton);
            weaponButton.setColor(1,1, 1, 0.3f);

            weaponButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedWeapon = weapon;
                    for (Button weaponButton : weaponButtons) {
                        weaponButton.setColor(1,1,1,0.3f);
                    }
                    weaponButton.setColor(1,1,1,1);
                }
            });

            weaponTable.add(weaponButton).pad(5);
        }
        root.add(weaponTable).pad(10).row();

        //  Time Selection
        HorizontalGroup timeGroup = new HorizontalGroup();
        timeGroup.space(10);
        int[] times = {2, 5, 10, 20};
        for (int t : times) {
            TextButton timeButton = new TextButton(t + Message.MINUTES.getMessage(), skin);
            timeButtons.add(timeButton);
            timeButton.setColor(1,1,1,0.3f);
            timeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedDuration = t;
                    for (TextButton timeButton : timeButtons) {
                        timeButton.setColor(1,1,1,0.3f);
                    }
                    timeButton.setColor(1,1,1,1);
                }
            });
            timeGroup.addActor(timeButton);
        }
        root.add(timeGroup).pad(10).row();


        // Play + Back Buttons
        Table actionButtons = new Table();
        TextButton playButton = new TextButton(Message.PLAY.getMessage(), skin);
        TextButton backButton = new TextButton(Message.BACK.getMessage(), skin);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.play();
//                Game game = new Game(new Player(App.getCurrentUser(), selectedHero, selectedWeapon), selectedDuration);
//                App.getGames().add(game);
//                App.setActiveGame(game);
//                Main.getMain().setScreen(new GameView(new GameController(game), skin));
            }
        });

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getMain().setScreen(new MainMenuView(new MainMenuController(), skin));
            }
        });

        actionButtons.add(playButton).pad(10).width(screenWidth * 0.25f);
        actionButtons.add(backButton).pad(10).width(screenWidth * 0.25f);
        root.add(actionButtons).pad(20).row();

        heroButtons.get(0).setColor(1,1,1,1);
        weaponButtons.get(0).setColor(1,1,1,1);
        timeButtons.get(1).setColor(1,1,1,1);

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

    public WeaponType getSelectedWeapon() {
        return selectedWeapon;
    }

    public Hero getSelectedHero() {
        return selectedHero;
    }

    public float getSelectedDuration() {
        return selectedDuration;
    }
}

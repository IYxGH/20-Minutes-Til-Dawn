package com.untilldown.View.MenusViewInMain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.untilldown.Controller.MenuControllersInMain.TalentController;
import com.untilldown.Model.Enums.Message;

import java.util.Map;
import java.util.List;

/**
 * View for the Talent Menu, displaying game information and guides.
 */
public class TalentView implements Screen {
    private final TalentController controller;
    private Stage stage;
    private Skin skin;
    private Table mainTable;
    private Table contentTable;
    private ScrollPane contentScrollPane;

    /**
     * Enum to define the currently displayed section.
     */
    public enum Section {
        HEROES, KEYS, CHEATS, ABILITIES
    }

    private Section currentSection = Section.HEROES; // Default section to display

    /**
     * Constructor for the TalentView.
     *
     * @param controller The TalentController instance.
     * @param skin The Skin to use for UI elements.
     */
    public TalentView(TalentController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        this.controller.setView(this); // Set the view in the controller
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        mainTable = new Table(skin);
        mainTable.setFillParent(true);
        mainTable.pad(20);
        mainTable.align(Align.top);

        // --- Title Label ---
        Label titleLabel = new Label(Message.TALENT_MENU_TITLE.getMessage(), skin, "title");
        titleLabel.setScale(1.5f);
        mainTable.add(titleLabel).padBottom(30).colspan(4).row(); // Colspan 4 for the 4 section buttons

        // --- Section Selection Buttons ---
        Table sectionButtonsTable = new Table(skin);
        sectionButtonsTable.defaults().pad(5).expandX().fillX();

        TextButton heroesButton = new TextButton(Message.SECTION_HEROES.getMessage(), skin);
        heroesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentSection = Section.HEROES;
                updateContentDisplay();
            }
        });
        sectionButtonsTable.add(heroesButton);

        TextButton keysButton = new TextButton(Message.SECTION_KEYS.getMessage(), skin);
        keysButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentSection = Section.KEYS;
                updateContentDisplay();
            }
        });
        sectionButtonsTable.add(keysButton);

        TextButton cheatsButton = new TextButton(Message.SECTION_CHEATS.getMessage(), skin);
        cheatsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentSection = Section.CHEATS;
                updateContentDisplay();
            }
        });
        sectionButtonsTable.add(cheatsButton);

        TextButton abilitiesButton = new TextButton(Message.SECTION_ABILITIES.getMessage(), skin);
        abilitiesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentSection = Section.ABILITIES;
                updateContentDisplay();
            }
        });
        sectionButtonsTable.add(abilitiesButton).row();

        mainTable.add(sectionButtonsTable).fillX().padBottom(20).colspan(4).row();

        // --- Dynamic Content Area (within a ScrollPane) ---
        contentTable = new Table(skin);
        contentTable.top().left(); // Align content to top-left within its table
        contentTable.pad(10); // Padding inside the content area

        contentScrollPane = new ScrollPane(contentTable, skin);
        contentScrollPane.setFadeScrollBars(false);
        contentScrollPane.setScrollingDisabled(true, false); // Vertical scrolling only
        mainTable.add(contentScrollPane).expand().fill().colspan(4).row(); // Make it expand and fill available space

        // --- Back Button ---
        TextButton backButton = new TextButton(Message.BACK.getMessage(), skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.goBack(); // Call controller to handle navigation
            }
        });
        mainTable.add(backButton).padTop(20).colspan(4).row();

        stage.addActor(mainTable);

        // Initial display update
        updateContentDisplay();
    }

    /**
     * Updates the content display based on the currentSection.
     * Clears previous content and populates with new data.
     */
    private void updateContentDisplay() {
        contentTable.clearChildren(); // Clear previous content

        switch (currentSection) {
            case HEROES:
                displayHeroes();
                break;
            case KEYS:
                displayKeys();
                break;
            case CHEATS:
                displayCheats();
                break;
            case ABILITIES:
                displayAbilities();
                break;
        }
    }

    /**
     * Populates the contentTable with hero information.
     */
    private void displayHeroes() {
        contentTable.add(new Label(Message.HERO_NAME_HEADER.getMessage(), skin)).expandX().left().pad(5);
        contentTable.add(new Label(Message.HERO_DESCRIPTION_HEADER.getMessage(), skin)).expandX().left().pad(5).row();
        contentTable.add().colspan(2).height(5).row(); // Spacer

        List<Map<String, String>> heroes = controller.getHeroInfo();
        for (Map<String, String> hero : heroes) {
            contentTable.add(new Label(hero.get("name"), skin)).expandX().left().pad(5);
            contentTable.add(new Label(hero.get("description"), skin)).expandX().left().pad(5).row();
            contentTable.add().colspan(2).height(5).row();
        }
    }

    /**
     * Populates the contentTable with game key bindings.
     */
    private void displayKeys() {
        contentTable.add(new Label(Message.ACTION_HEADER.getMessage(), skin)).expandX().left().pad(5);
        contentTable.add(new Label(Message.KEY_HEADER.getMessage(), skin)).expandX().left().pad(5).row();
        contentTable.add().colspan(2).height(5).row(); // Spacer

        Map<String, String> keyBindings = controller.getGameKeyBindings();
        for (Map.Entry<String, String> entry : keyBindings.entrySet()) {
            contentTable.add(new Label(entry.getKey(), skin)).expandX().left().pad(5);
            contentTable.add(new Label(entry.getValue(), skin)).expandX().left().pad(5).row();
            contentTable.add().colspan(2).height(5).row(); // Small spacer between entries
        }
    }

    /**
     * Populates the contentTable with cheat codes information.
     */
    private void displayCheats() {
        contentTable.add(new Label(Message.CHEAT_CODE_HEADER.getMessage(), skin)).expandX().left().pad(5);
        contentTable.add(new Label(Message.CHEAT_EFFECT_HEADER.getMessage(), skin)).expandX().left().pad(5).row();
        contentTable.add().colspan(2).height(5).row(); // Spacer

        List<Map<String, String>> cheats = controller.getCheatCodes();
        for (Map<String, String> cheat : cheats) {
            contentTable.add(new Label(cheat.get("code"), skin)).expandX().left().pad(5);
            contentTable.add(new Label(cheat.get("effect"), skin)).expandX().left().pad(5).row();
            contentTable.add().colspan(2).height(5).row(); // Small spacer between entries
        }
    }

    /**
     * Populates the contentTable with game abilities information.
     */
    private void displayAbilities() {
        contentTable.add(new Label(Message.ABILITY_NAME_HEADER.getMessage(), skin)).expandX().left().pad(5);
        contentTable.add(new Label(Message.ABILITY_DESCRIPTION_HEADER.getMessage(), skin)).expandX().left().pad(5).row();
        contentTable.add().colspan(2).height(5).row(); // Spacer

        List<Map<String, String>> abilities = controller.getGameAbilities();
        for (Map<String, String> ability : abilities) {
            contentTable.add(new Label(ability.get("name"), skin)).expandX().left().pad(5);
            contentTable.add(new Label(ability.get("description"), skin)).expandX().left().pad(5).row();
            contentTable.add().colspan(2).height(5).row(); // Small spacer between entries
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Clear the screen with black

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f)); // Update actors
        stage.draw(); // Draw the stage
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update viewport on resize
    }

    @Override
    public void pause() {
        // Optional: handle pausing game elements
    }

    @Override
    public void resume() {
        // Optional: handle resuming game elements
    }

    @Override
    public void hide() {
        // Called when this screen is no longer the current screen
        Gdx.input.setInputProcessor(null); // Clear input processor
    }

    @Override
    public void dispose() {
        // Dispose of the stage when the screen is no longer needed
        stage.dispose();
    }
}

package com.untilldown.View.MenusViewInMain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.untilldown.Controller.MenuControllersInMain.ScoreBoardController;
import com.untilldown.Model.Enums.Message;
import com.untilldown.Model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * View for the Scoreboard menu.
 * Displays user rankings based on different criteria and allows sorting.
 */
public class ScoreBoardView implements Screen {
    private final ScoreBoardController controller;
    private final Skin skin;
    private Stage stage;
    private Table mainTable;
    private Table userEntriesTable; // Table to hold the actual user data rows
    private ScrollPane scrollPane;


    // Enum to define sorting criteria
    public enum SortCriteria {
        POINTS, USERNAME, KILLS, LIFETIME
    }

    private SortCriteria currentSortCriteria = SortCriteria.POINTS; // Default sorting

    /**
     * Constructor for the ScoreBoardView.
     *
     * @param controller The ScoreBoardController instance.
     * @param skin The Skin to use for UI elements.
     */
    public ScoreBoardView(ScoreBoardController controller, Skin skin) {
        this.controller = controller;
        this.skin = skin;
        // Set the view in the controller, so it has a reference back
        this.controller.setView(this);
    }

    @Override
    public void show() {
        // Initialize the stage with a ScreenViewport for responsive UI
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Create the main table that fills the screen
        mainTable = new Table(skin);
        mainTable.setFillParent(true);
        mainTable.pad(20); // Add some padding around the edges
        mainTable.align(Align.top); // Align content to the top

        // --- Title Label ---
        Label titleLabel = new Label(Message.SCOREBOARD_TITLE.getMessage(), skin, "title");
        // Adjusted colspan to 5 to match the 5 columns in the scoreboard
        mainTable.add(titleLabel).padBottom(30).colspan(5).row();

        // --- Sort Buttons ---
        Table sortButtonsTable = new Table(skin);
        sortButtonsTable.defaults().pad(5);

        TextButton sortByPointsButton = new TextButton(Message.SORT_BY_POINTS.getMessage(), skin);
        sortByPointsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentSortCriteria = SortCriteria.POINTS;
                updateScoreboardDisplay();
            }
        });
        sortButtonsTable.add(sortByPointsButton);

        TextButton sortByUsernameButton = new TextButton(Message.SORT_BY_USERNAME.getMessage(), skin);
        sortByUsernameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentSortCriteria = SortCriteria.USERNAME;
                updateScoreboardDisplay();
            }
        });
        sortButtonsTable.add(sortByUsernameButton);

        TextButton sortByKillsButton = new TextButton(Message.SORT_BY_KILLS.getMessage(), skin);
        sortByKillsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentSortCriteria = SortCriteria.KILLS;
                updateScoreboardDisplay();
            }
        });
        sortButtonsTable.add(sortByKillsButton);

        TextButton sortByLifeTimeButton = new TextButton(Message.SORT_BY_LIFETIME.getMessage(), skin);
        sortByLifeTimeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentSortCriteria = SortCriteria.LIFETIME;
                updateScoreboardDisplay();
            }
        });
        sortButtonsTable.add(sortByLifeTimeButton);

        // Adjusted colspan to 5
        mainTable.add(sortButtonsTable).padBottom(20).colspan(5).row();

        // --- Scoreboard Headers ---
        Table headerTable = new Table(skin);
        headerTable.defaults().pad(1).expandX().center(); // Center align headers
        // Increased width for Rank and adjusted Username width to sum to 1.0f
        headerTable.add(new Label(Message.RANK_HEADER.getMessage(), skin)).width(Gdx.graphics.getWidth() * 0.1f);
        headerTable.add(new Label(Message.USERNAME_HEADER.getMessage(), skin)).width(Gdx.graphics.getWidth() * 0.25f); // Adjusted
        headerTable.add(new Label(Message.SCORE_HEADER.getMessage(), skin)).width(Gdx.graphics.getWidth() * 0.2f);
        headerTable.add(new Label(Message.KILLS_HEADER.getMessage(), skin)).width(Gdx.graphics.getWidth() * 0.2f);
        headerTable.add(new Label(Message.LIFETIME_HEADER.getMessage(), skin)).width(Gdx.graphics.getWidth() * 0.2f);
        // Adjusted colspan to 5
        mainTable.add(headerTable).fillX().padBottom(10).colspan(5).row();

        // --- User Entries Table (inside ScrollPane) ---
        userEntriesTable = new Table(skin);
        userEntriesTable.defaults().pad(5).expandX().center();
        userEntriesTable.align(Align.top);

        scrollPane = new ScrollPane(userEntriesTable, skin);
        scrollPane.setFadeScrollBars(false); // Keep scrollbars visible
        scrollPane.setScrollingDisabled(true, false);
        // Adjusted colspan to 5
        mainTable.add(scrollPane).expand().fill().colspan(5).row();

        // --- Back Button ---
        TextButton backButton = new TextButton(Message.BACK.getMessage(), skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                controller.goBack(); // Call controller method to handle navigation
            }
        });
        // Adjusted colspan to 5
        mainTable.add(backButton).padTop(20).colspan(5).row();

        stage.addActor(mainTable);

        // Initial display update
        updateScoreboardDisplay();
    }

    /**
     * Updates the scoreboard display based on the current sorting criteria.
     * This method clears the existing entries and repopulates the table.
     */
    private void updateScoreboardDisplay() {
        userEntriesTable.clearChildren();

        ArrayList<User> sortedUsers;
        switch (currentSortCriteria) {
            case USERNAME:
                sortedUsers = controller.getSortedUsersByUsername();
                break;
            case KILLS:
                sortedUsers = controller.getSortedUsersByKills();
                break;
            case LIFETIME:
                sortedUsers = controller.getSortedUsersByLifeTime();
                break;
            case POINTS:
            default:
                sortedUsers = controller.getSortedUsersByPoints();
                break;
        }

        List<User> topUsers = controller.getTopUsers(sortedUsers, 10);
        User currentUser = controller.getCurrentUser();

        // Add user data rows
        int rank = 1;
        for (User user : topUsers) {
            Label.LabelStyle rowStyle = skin.get("default", Label.LabelStyle.class); // Default style

            // Apply different visual effects for top 3
            if (rank <= 3) {
                Label.LabelStyle top3Style = new Label.LabelStyle(rowStyle);
                top3Style.fontColor = Color.GOLD; // Example: change font color
                rowStyle = top3Style;
            }

            // Apply different visual effects for the logged-in user
            if (currentUser != null && user.getUsername().equals(currentUser.getUsername())) {
                Label.LabelStyle currentUserStyle = new Label.LabelStyle(rowStyle);
                currentUserStyle.fontColor = Color.CYAN; // Example: change font color
                rowStyle = currentUserStyle;
            }

            // Create labels for each piece of user data
            Label rankLabel = new Label(String.valueOf(rank), rowStyle);
            Label usernameLabel = new Label(user.getUsername(), rowStyle);
            Label scoreLabel = new Label(String.valueOf(user.getTotalPoints()), rowStyle);
            Label killsLabel = new Label(String.valueOf(user.getTotalKills()), rowStyle);
            Label lifetimeLabel = new Label(String.format("%.2f", user.getMaxLifeTime()), rowStyle);

            // Add labels to the user entries table with consistent widths and alignment
            // Adjusted width for Rank and Username to match header
            userEntriesTable.add(rankLabel).width(Gdx.graphics.getWidth() * 0.15f).center();
            userEntriesTable.add(usernameLabel).width(Gdx.graphics.getWidth() * 0.25f).center();
            userEntriesTable.add(scoreLabel).width(Gdx.graphics.getWidth() * 0.2f).center();
            userEntriesTable.add(killsLabel).width(Gdx.graphics.getWidth() * 0.2f).center();
            userEntriesTable.add(lifetimeLabel).width(Gdx.graphics.getWidth() * 0.2f).center().row();

            rank++;
        }
    }

    @Override
    public void render(float delta) {
        // Clear the screen (optional, usually done in the main game loop)
        ScreenUtils.clear(0, 0, 0, 1);


        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Update viewport on resize
    }

    @Override
    public void pause() {

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

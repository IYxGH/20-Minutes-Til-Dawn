package com.untilldown.Controller.MenuControllersInMain;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.untilldown.Controller.MainMenuController;
import com.untilldown.Main;
import com.untilldown.Model.App;
import com.untilldown.Model.Enums.Ability;
import com.untilldown.Model.Enums.Action;
import com.untilldown.Model.Enums.Hero;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.Model.GameControls;
import com.untilldown.View.MainMenuView;
import com.untilldown.View.MenusViewInMain.TalentView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the Talent Menu (information and guides).
 * Handles the logic for retrieving and preparing data for the TalentView.
 */
public class TalentController {
    private TalentView view;

    public void setView(TalentView view) {
        this.view = view;
    }

    /**
     * Retrieves information about game heroes.
     * You should fill this with actual hero data.
     *
     * @return A list of Maps, where each Map represents a hero with "name" and "description" keys.
     */
    public List<Map<String, String>> getHeroInfo() {
        List<Map<String, String>> heroes = new ArrayList<>();

        for (Hero hero : Hero.values()) {
            Map<String, String> heroData = new LinkedHashMap<>();
            heroData.put("name", hero.name());
            heroData.put("description", hero.getInfo());
            heroes.add(heroData);
        }

        return heroes;
    }

    /**
     * Retrieves the current game key bindings as used by the user.
     * This uses the App.gameControls static instance.
     *
     * @return A Map where keys are action names and values are the bound keys.
     */
    public Map<String, String> getGameKeyBindings() {
        // Assuming App.gameControls holds the user's current key bindings
        GameControls controls = App.gameControls;
        return controls.getKeyBindings();
    }

    /**
     * Retrieves a list of cheat codes and their effects.
     * You should fill this with your actual cheat codes.
     *
     * @return A list of Maps, where each Map represents a cheat code with "code" and "effect" keys.
     */
    public List<Map<String, String>> getCheatCodes() {
        List<Map<String, String>> cheats = new ArrayList<>();

        // TODO: Populate with your actual cheat codes and their effects
        // Example structure:
        GameControls controls = App.gameControls;
        for (Action action : Action.values()) {
            if (action.isCheat()) {
                Map<String, String> cheat = new LinkedHashMap<>();
                cheat.put("code", Input.Keys.toString(controls.getKey(action)));
                cheat.put("effect", action.getInfo());
                cheats.add(cheat);

            }
        }

        return cheats;
    }

    /**
     * Retrieves information about game abilities.
     * You should fill this with your actual game abilities.
     *
     * @return A list of Maps, where each Map represents an ability with "name" and "description" keys.
     */
    public List<Map<String, String>> getGameAbilities() {
        List<Map<String, String>> abilities = new ArrayList<>();

        // TODO: Populate with your actual game ability data
        // Example structure:
        for (Ability ability : Ability.values()) {
            Map<String, String> ability1 = new LinkedHashMap<>();
            ability1.put("name", ability.getMessage());
            ability1.put("description", ability.getInfo());
            abilities.add(ability1);
        }


        return abilities;
    }

    /**
     * Handles navigation back to the previous screen (e.g., main menu).
     */
    public void goBack() {
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }
}

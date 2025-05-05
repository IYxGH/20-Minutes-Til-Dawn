package com.untilldown.Controller;

import com.untilldown.Main;
import com.untilldown.Model.GameAssetManager;
import com.untilldown.View.MainMenuView;
import com.untilldown.View.PreGameMenuView;

public class MainMenuController {
    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void handleMainMenuButtons() {
        if (view != null) {
            if (view.getPlayButton().isChecked() && view.getField().getText().equals("kiarash")) {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
            }
        }
    }
}

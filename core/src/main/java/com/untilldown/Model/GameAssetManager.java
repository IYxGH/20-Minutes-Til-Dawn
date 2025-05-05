package com.untilldown.Model;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private GameAssetManager gameAssetManager;
    private Skin skin = new Skin();

    public GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }
}

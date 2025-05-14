package com.untilldown.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class GameAssetManager extends AssetManager {
    public static final int NUM_AVATARS = 3;
    private static GameAssetManager gameAssetManager;
    private Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }

        return gameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public void setSkin(Skin skin) {
        this.skin = skin;
    }

    public void load() {

    }


    public void loadAvatars() {
        for (int i = 0; i < NUM_AVATARS; i++) {
            load("avatars/avatar" + i + ".png", Texture.class);
        }
    }

    public boolean avatarsLoaded() {
        for (int i = 0; i < NUM_AVATARS; i++) {
            if (!isLoaded("avatars/avatar" + i + ".png")) {
                return false;
            }
        }
        return true;
    }

    public Image getAvatarImage(int index) {
        String path = "avatars/avatar" + index + ".png";
        if (!isLoaded(path)) {
            throw new GdxRuntimeException("Avatar not loaded: " + path);
        }
        Texture texture = get(path, Texture.class);
        return new Image(texture);
    }
}

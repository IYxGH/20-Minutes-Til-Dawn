package com.untilldown.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.untilldown.Model.Enums.AvatarType;

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


    public Image getAvatarImage(AvatarType avatarType) {
        Texture texture = new Texture(Gdx.files.internal(avatarType.getPath()));
        return new Image(texture);
    }
}

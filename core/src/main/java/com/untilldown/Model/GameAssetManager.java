package com.untilldown.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
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
        // Load and set up the font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("skin/nougat-extrablack-webfont (2).ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        BitmapFont customFont = generator.generateFont(parameter);
        generator.dispose();

        // Replace default font in skin
        skin.add("default-font", customFont, BitmapFont.class);

        for (ObjectMap.Entry<String, Label.LabelStyle> entry : skin.getAll(Label.LabelStyle.class)) {
            entry.value.font = customFont;
        }
        for (ObjectMap.Entry<String, TextButton.TextButtonStyle> entry : skin.getAll(TextButton.TextButtonStyle.class)) {
            entry.value.font = customFont;
        }
        for (ObjectMap.Entry<String, TextField.TextFieldStyle> entry : skin.getAll(TextField.TextFieldStyle.class)) {
            entry.value.font = customFont;
        }
    }


    public Image getAvatarImage(AvatarType avatarType) {
        Texture texture = new Texture(Gdx.files.internal(avatarType.getPath()));
        return new Image(texture);
    }
}

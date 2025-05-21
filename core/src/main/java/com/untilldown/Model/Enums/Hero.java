package com.untilldown.Model.Enums;

import com.badlogic.gdx.graphics.Texture;

public enum Hero {
    SHANA("Heros/Shana/portrait.png",
        new String[] {""},
        4,
        4),
    DIAMOND("Heros/Diamond/Portrait.png",
        new String[]{""},
        7,
        1),
//    SCARLET(),
//    LILITH(),
//    DASHER(),
    ;


    private final String portraitFilePath;
    private final String[] pngPath;
    private final float hp;
    private final float speed;

    Hero(String portraitFilePath, String[] pngPath, float hp, float speed) {
        this.portraitFilePath = portraitFilePath;
        this.pngPath = pngPath;
        this.hp = hp;
        this.speed = speed;
    }

    public Texture getPortrairTexture() {
        return new Texture(portraitFilePath);
    }

    public float getHp() {
        return hp;
    }

    public float getSpeed() {
        return speed;
    }
}

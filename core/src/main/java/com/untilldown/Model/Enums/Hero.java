package com.untilldown.Model.Enums;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Arrays;

public enum Hero {
    SHANA("Heros/Shana/portrait.png",
        4,
        4,
        new ArrayList<>(Arrays.asList("Heros/Shana/idle/Idle_0 #8330.png",
            "Heros/Shana/idle/Idle_1.png",
            "Heros/Shana/idle/Idle_2.png",
            "Heros/Shana/idle/Idle_3.png",
            "Heros/Shana/idle/Idle_4.png",
            "Heros/Shana/idle/Idle_5.png"
            )),
        new ArrayList<>(Arrays.asList("Heros/Shana/walk/Walk_0.png",
            "Heros/Shana/walk/Walk_1.png",
            "Heros/Shana/walk/Walk_2.png",
            "Heros/Shana/walk/Walk_3.png",
            "Heros/Shana/walk/Walk_5.png",
            "Heros/Shana/walk/Walk_6.png",
            "Heros/Shana/walk/Walk_7.png"))),
    DIAMOND("Heros/Diamond/Portrait.png",
        7,
        1,
        new ArrayList<>(Arrays.asList("")) ,
        new ArrayList<>(Arrays.asList())
        ),
//    SCARLET(),
//    LILITH(),
//    DASHER(),
    ;


    private final String portraitFilePath;
    private final float hp;
    private final float speed;
    private final ArrayList<String> idlePaths;
    private final ArrayList<String> walkingPaths;

    Hero(String portraitFilePath, float hp, float speed, ArrayList<String> idlePaths, ArrayList<String> walkingPaths) {
        this.portraitFilePath = portraitFilePath;
        this.hp = hp;
        this.speed = speed;
        this.idlePaths = idlePaths;
        this.walkingPaths = walkingPaths;
    }

    public Texture getPortrairTexture() {
        return new Texture(portraitFilePath);
    }

    public Array<TextureRegion> getTextureIdle() {

        Array<TextureRegion> idleTextures = new Array<>();
        for (String idlePath : idlePaths) {
            idleTextures.add(new TextureRegion(new Texture(Gdx.files.internal(idlePath))));
        }

        return idleTextures;
    }

    public Array<TextureRegion> getTextureWalking() {

        Array<TextureRegion> walkingTextures = new Array<>();
        for (String walkingPath : walkingPaths) {
            walkingTextures.add(new TextureRegion(new Texture(Gdx.files.internal(walkingPath))));
        }

        return walkingTextures;
    }

    public float getHp() {
        return hp;
    }

    public float getSpeed() {
        return speed;
    }
}

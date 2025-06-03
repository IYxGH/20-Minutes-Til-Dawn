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
        new ArrayList<>(Arrays.asList(
            "Heros/Shana/idle/Idle_0 #8330.png",
            "Heros/Shana/idle/Idle_1.png",
            "Heros/Shana/idle/Idle_2.png",
            "Heros/Shana/idle/Idle_3.png",
            "Heros/Shana/idle/Idle_4.png",
            "Heros/Shana/idle/Idle_5.png"
            )),
        new ArrayList<>(Arrays.asList(
            "Heros/Shana/walk/Walk_0.png",
            "Heros/Shana/walk/Walk_1.png",
            "Heros/Shana/walk/Walk_2.png",
            "Heros/Shana/walk/Walk_3.png",
            "Heros/Shana/walk/Walk_5.png",
            "Heros/Shana/walk/Walk_6.png",
            "Heros/Shana/walk/Walk_7.png")),
        Message.SHANA_INFO
        ),
    DIAMOND("Heros/Diamond/Portrait.png",
        7,
        1,
        new ArrayList<>(Arrays.asList(
            "Heros/Diamond/idle/Idle (1).png",
            "Heros/Diamond/idle/Idle (2).png",
            "Heros/Diamond/idle/Idle (3).png",
            "Heros/Diamond/idle/Idle (4).png",
            "Heros/Diamond/idle/Idle (5).png",
            "Heros/Diamond/idle/Idle (6).png"
        )) ,
        new ArrayList<>(Arrays.asList(
            "Heros/Diamond/walk/Walk (1).png",
            "Heros/Diamond/walk/Walk (2).png",
            "Heros/Diamond/walk/Walk (3).png",
            "Heros/Diamond/walk/Walk (4).png",
            "Heros/Diamond/walk/Walk (5).png",
            "Heros/Diamond/walk/Walk (6).png",
            "Heros/Diamond/walk/Walk (7).png",
            "Heros/Diamond/walk/Walk (8).png"
        )),
        Message.DIAMOND_INFO
    ),
    SCARLET("Heros/Scarlet/Portrait.png",
        3,
        5,
        new ArrayList<>(Arrays.asList(
            "Heros/Scarlet/idle/Idle (1).png",
            "Heros/Scarlet/idle/Idle (2).png",
            "Heros/Scarlet/idle/Idle (3).png",
            "Heros/Scarlet/idle/Idle (4).png",
            "Heros/Scarlet/idle/Idle (5).png",
            "Heros/Scarlet/idle/Idle (6).png"
        )) ,
        new ArrayList<>(Arrays.asList(
            "Heros/Scarlet/run/Run (1).png",
            "Heros/Scarlet/run/Run (2).png",
            "Heros/Scarlet/run/Run (3).png",
            "Heros/Scarlet/run/Run (4).png"
        )),
        Message.SCARLET_INFO
    ),
    LILITH("Heros/Lilith/Portrait.png",
        5,
        3,
        new ArrayList<>(Arrays.asList(
            "Heros/Lilith/idle/Idle (1).png",
            "Heros/Lilith/idle/Idle (2).png",
            "Heros/Lilith/idle/Idle (3).png",
            "Heros/Lilith/idle/Idle (4).png",
            "Heros/Lilith/idle/Idle (5).png",
            "Heros/Lilith/idle/Idle (6).png"
        )) ,
        new ArrayList<>(Arrays.asList(
            "Heros/Lilith/walk/Walk (1).png",
            "Heros/Lilith/walk/Walk (2).png",
            "Heros/Lilith/walk/Walk (3).png",
            "Heros/Lilith/walk/Walk (4).png",
            "Heros/Lilith/walk/Walk (5).png",
            "Heros/Lilith/walk/Walk (6).png",
            "Heros/Lilith/walk/Walk (7).png",
            "Heros/Lilith/walk/Walk (8).png"
        )),
        Message.LILITH_INFO
    ),
    DASHER("Heros/Dasher/Portrait.png",
        2,
        10,
        new ArrayList<>(Arrays.asList(
            "Heros/Dasher/idle/Idle (1).png",
            "Heros/Dasher/idle/Idle (2).png",
            "Heros/Dasher/idle/Idle (3).png",
            "Heros/Dasher/idle/Idle (4).png",
            "Heros/Dasher/idle/Idle (5).png",
            "Heros/Dasher/idle/Idle (6).png"
        )) ,
        new ArrayList<>(Arrays.asList(
            "Heros/Dasher/run/Run (1).png",
            "Heros/Dasher/run/Run (2).png",
            "Heros/Dasher/run/Run (3).png",
            "Heros/Dasher/run/Run (4).png"
        )),
        Message.DASHER_INFO
    ),
    ;


    private final String portraitFilePath;
    private final float hp;
    private final float speed;
    private final ArrayList<String> idlePaths;
    private final ArrayList<String> walkingPaths;
    private final Message aboutMessage;

    Hero(String portraitFilePath, float hp, float speed, ArrayList<String> idlePaths, ArrayList<String> walkingPaths, Message aboutMessage) {
        this.portraitFilePath = portraitFilePath;
        this.hp = hp;
        this.speed = speed;
        this.idlePaths = idlePaths;
        this.walkingPaths = walkingPaths;
        this.aboutMessage = aboutMessage;
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

    public String getInfo() {
        return aboutMessage.getMessage();
    }
}

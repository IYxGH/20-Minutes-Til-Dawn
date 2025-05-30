package com.untilldown.Model.Enums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Arrays;

public enum AnimationEffect {
    DEATH_EFFECT(new ArrayList<>(Arrays.asList(
        "FX/DeathFX/DeathFX_0.png",
        "FX/DeathFX/DeathFX_1.png",
        "FX/DeathFX/DeathFX_2.png",
        "FX/DeathFX/DeathFX_3.png"
    )),
        0.12f
        ),
    EXPLOSION_EFFECTS(new ArrayList<>(Arrays.asList(
        "FX/ExplosionFX/ExplosionFX_0.png",
        "FX/ExplosionFX/ExplosionFX_1.png",
        "FX/ExplosionFX/ExplosionFX_2.png",
        "FX/ExplosionFX/ExplosionFX_3.png",
        "FX/ExplosionFX/ExplosionFX_4.png",
        "FX/ExplosionFX/ExplosionFX_5.png"
    )),
        0.1f
        ),
    HOLYSHIELD_EFFECTS(new ArrayList<>(Arrays.asList(
        "FX/HolyShield/HolyShield_0.png",
        "FX/HolyShield/HolyShield_1.png",
        "FX/HolyShield/HolyShield_2.png",
        "FX/HolyShield/HolyShield_3.png",
        "FX/HolyShield/HolyShield_4.png",
        "FX/HolyShield/HolyShield_5.png",
        "FX/HolyShield/HolyShield_6.png",
        "FX/HolyShield/HolyShield_7.png",
        "FX/HolyShield/HolyShield_8.png"
    )),
        0.22f
        )
    ;

    private final ArrayList<String> paths;
    private final float duration;

    AnimationEffect(ArrayList<String> paths, float duration) {
        this.paths = paths;
        this.duration = duration;
    }

    public Array<TextureRegion> getTextureRegions() {
        Array<TextureRegion> regions = new Array<>();

        for (String path : paths) {
            regions.add(new TextureRegion(new Texture(path)));
        }

        return regions;
    }

    public float getDuration() {
        return duration;
    }
}

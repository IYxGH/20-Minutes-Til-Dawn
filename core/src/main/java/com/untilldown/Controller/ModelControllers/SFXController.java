package com.untilldown.Controller.ModelControllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
import com.untilldown.Model.App;

public class SFXController {
    private final static Sound hurtSound1 = Gdx.audio.newSound(Gdx.files.internal("MySFX/jessie_hurt_05.ogg"));
    private final static Sound hurtSound2 = Gdx.audio.newSound(Gdx.files.internal("MySFX/elprimo_hurt_01.ogg"));
    private final static Sound levelUpSound1 = Gdx.audio.newSound(Gdx.files.internal("MySFX/elprimo_lead_02.ogg"));
    private final static Sound levelUpSound2 = Gdx.audio.newSound(Gdx.files.internal("MySFX/colt_lead_05.ogg"));
    private final static Sound shotSound1 = Gdx.audio.newSound(Gdx.files.internal("MySFX/shelly_shotgun_fire_01.ogg"));
    private final static Sound clickSound = Gdx.audio.newSound(Gdx.files.internal("MySFX/8bit_reload_01.ogg"));



    public static void HurtSound() {
        if (!App.isSFXon()) return;
        int a = MathUtils.random(1, 2);
        if (a == 1) hurtSound1.play();
        else if (a == 2) hurtSound2.play();
    }

    public static void LevelUpSound() {
        if (!App.isSFXon()) return;

        int a = MathUtils.random(1, 2);
        if (a == 1) levelUpSound1.play();
        else if (a == 2) levelUpSound2.play();
    }

    public static void ShotSound() {
        if (!App.isSFXon()) return;
        int a = MathUtils.random(1, 1);
        if (a == 1) shotSound1.play();
    }

    public static void clickSound() {
        if (!App.isSFXon()) return;
        clickSound.play();
    }



}

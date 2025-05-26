package com.untilldown.Model.Enums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public enum WeaponType {
    REVOLVER("Revolver", 20, 1, 1, 6,
        "Weapon/T_Revolver_SS.png", 4),
    SHOTGUN("Shotgun", 10, 4, 1, 2, "", 3),
    SMGS_DUAL("SMGs Dual", 24, 2, 1, 8, "", 4),
    ;

    private final String name;
    private final double damage;
    private final double projectile;
    private final double reloadTime;
    private final double maxAmmo;
    private final String imagePath;
    private final int numOfBullets;

    WeaponType(String name, double damage, double projectile, double reloadTime, double maxAmmo, String path, int numOfBullets) {
        this.name = name;
        this.damage = damage;
        this.projectile = projectile;
        this.reloadTime = reloadTime;
        this.maxAmmo = maxAmmo;
        this.imagePath = path;
        this.numOfBullets = numOfBullets;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Texture getTexture() {
        return new Texture(imagePath);
    }

    public String getName() {
        return name;
    }

    public Array<TextureRegion> getBulletTextureRegions() {
        Texture texture = getTexture();
        TextureRegion temp = new TextureRegion(texture);
        TextureRegion[][] cut = temp.split(temp.getRegionWidth() / 10, temp.getRegionHeight() / 2);

        Array<TextureRegion> result = new Array<>();
        for (int i = 1; i <= numOfBullets; i++) {
            result.add(cut[0][i]);
        }
        return result;
    }
}

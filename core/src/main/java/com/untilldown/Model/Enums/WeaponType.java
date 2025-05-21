package com.untilldown.Model.Enums;

import com.badlogic.gdx.graphics.Texture;

public enum WeaponType {
    REVOLVER("Revolver", 20, 1, 1,6, "Weapon/T_Revolver_SS.png"),
    SHOTGUN("Shotgun", 10, 4, 1, 2,""),
    SMGS_DUAL("SMGs Dual", 24, 2, 1, 8,""),
    ;

    private final String name;
    private final double damage;
    private final double projectile;
    private final double reloadTime;
    private final double maxAmmo;
    private final String imagePath;

    WeaponType(String name, double damage, double projectile, double reloadTime, double maxAmmo, String path) {
        this.name = name;
        this.damage = damage;
        this.projectile = projectile;
        this.reloadTime = reloadTime;
        this.maxAmmo = maxAmmo;
        this.imagePath = path;
    }

    public Texture getTexture() {
        return new Texture(imagePath);
    }
    public String getName() {
        return name;
    }
}

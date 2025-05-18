package com.untilldown.Model.Enums;

public enum AvatarType {
    EL_PRIMO("Avatars/ElPrimo.png"),
    BATMANI("Avatars/Batmani.png"),
    BEN_10("Avatars/BenTen.png"),
    ;

    private final String path;

    AvatarType(String path) {
        this.path = path;
    }

    public static AvatarType getRandomAvatarType() {
        int rand = (int) (Math.random() * AvatarType.values().length);
        return AvatarType.values()[rand];
    }

    public String getPath() {
        return path;
    }
}

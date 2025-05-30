package com.untilldown.Model.Enums;

public enum MusicName {
    MUSIC1("Az Hend", "Music/Vaghti Az Hend Omadam.mp3"),
    MUSIC2("Santur", "Music/santur.mp3"),
    MUSIC3("FIFA WC", "Music/FootBall_1990.mp3")

    ;

    private final String name;
    private final String path;

    MusicName(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }
}

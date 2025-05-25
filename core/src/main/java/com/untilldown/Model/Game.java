package com.untilldown.Model;

public class Game {
    private Player player;
    private final double duration;
    private float time = 0.0f;

    public Game(Player player, double duration) {
        this.player = player;
        this.duration = duration;
    }

    public Player getPlayer() {
        return player;
    }

    public double getDuration() {
        return duration;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void addTime(float time) {
        this.time += time;
    }
}

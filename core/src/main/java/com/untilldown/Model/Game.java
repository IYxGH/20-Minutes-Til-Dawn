package com.untilldown.Model;

public class Game {
    private Player player;
    private double duration;

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
}

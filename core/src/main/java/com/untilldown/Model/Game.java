package com.untilldown.Model;

import com.untilldown.Model.EnemyClasses.Enemy;

import java.util.ArrayList;

public class Game {
    private Player player;
    private final float duration;
    private float time = 0.0f;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Bullet> playerBullets = new ArrayList<>();
    private ArrayList<Bullet> enemyBullets = new ArrayList<>();

    public Game(Player player, float duration) {
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

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
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

    public String getTimeLeft() {
        float timeLeft = (float) (duration - getTime());

        return String.format("%.2f", timeLeft);
    }


}

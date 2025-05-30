package com.untilldown.Model;

import com.untilldown.Model.EnemyClasses.Enemy;

import java.util.ArrayList;

public class Game {
    public static final int MAP_MIN_X = 15;
    public static final int MAP_MIN_Y = 15;
    public static final int MAP_MAX_X = 3026;
    public static final int MAP_MAX_Y = 1842;


    private Player player;
    private final float duration;
    private float time = 0.0f;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Bullet> playerBullets = new ArrayList<>();
    private ArrayList<Bullet> enemyBullets = new ArrayList<>();
    private ArrayList<Seed> seeds = new ArrayList<>();

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

    public ArrayList<Bullet> getPlayerBullets() {
        return playerBullets;
    }

    public ArrayList<Seed> getSeeds() {
        return seeds;
    }

    public void setSeeds(ArrayList<Seed> seeds) {
        this.seeds = seeds;
    }

    public void setPlayerBullets(ArrayList<Bullet> playerBullets) {
        this.playerBullets = playerBullets;
    }

    public ArrayList<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public void setEnemyBullets(ArrayList<Bullet> enemyBullets) {
        this.enemyBullets = enemyBullets;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void addTime(float time) {
        this.time += time;
        if (this.time > duration) {
            this.time = duration;
        }
    }

    public String getTimeLeft() {
        float timeLeft = (float) (duration - getTime());

        return String.format("%.2f", timeLeft);
    }


}

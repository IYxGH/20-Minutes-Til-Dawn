package com.untilldown.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.untilldown.Main;
import com.untilldown.Model.Enums.Hero;
import com.untilldown.Model.Enums.WeaponType;

public class Player extends Actor {
    private User user;
    private Hero hero;
    private WeaponType weapon;

    //game statues
    private float hp;
    private int kills = 0;
    private int ammoLeft;
    private int level = 0;
    private int xp = 0;
    private float timePastLastDamage = 0;
    private boolean autoAim = false;

    //reloading variables
    private boolean reloading = false;
    private float timerReloading = 0f;


    private PlayerState playerState = PlayerState.STANDING;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> idleAnimation;
    private boolean facingLeft = false;


    public enum PlayerState {
        STANDING,
        WALKING
    }


    public Player(User user, Hero hero, WeaponType weapon) {
        this.hero = hero;
        this.weapon = weapon;
        this.ammoLeft = weapon.getMaxAmmo();

        hp = hero.getHp();


        //walking animation
        walkAnimation = new Animation(0.1f, hero.getTextureWalking());
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);


        // Standing animation
        idleAnimation = new Animation(0.1f, hero.getTextureIdle());
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion sampleFrame = hero.getTextureIdle().get(0);
        setSize(sampleFrame.getRegionWidth(), sampleFrame.getRegionHeight());

    }

    public double getSpeed() {
        return hero.getSpeed();
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void move(float dx, float dy, float deltaTime) {
        float distanceX = (float) (dx * getSpeed() * 100 * deltaTime);
        float distanceY = (float) (dy * getSpeed() * 100 * deltaTime);

        if (distanceX != 0 || distanceY != 0) {
            playerState = PlayerState.WALKING;
        } else {
            playerState = PlayerState.STANDING;
        }

        if (dx < 0) {
            facingLeft = true;
        } else if (dx > 0) {
            facingLeft = false;
        }

        float newX = getX() + distanceX;
        float newY = getY() + distanceY;

        // Clamp new position so player stays inside the map
        newX = MathUtils.clamp(newX, Game.MAP_MIN_X, Game.MAP_MAX_X - getWidth());
        newY = MathUtils.clamp(newY, Game.MAP_MIN_Y, Game.MAP_MAX_Y - getHeight());

        setPosition(newX, newY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame = null;
        batch.enableBlending();

        float currentTime = App.getActiveGame().getTime();
        switch (playerState) {
            case STANDING:
                currentFrame = idleAnimation.getKeyFrame(currentTime);
                break;
            case WALKING:
                currentFrame = walkAnimation.getKeyFrame(currentTime);
                break;
        }

        // Handling to change face when walking
        if (facingLeft && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        } else if (!facingLeft && currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }

        if (timePastLastDamage != 0) {
            this.setColor(0.5f, 0.5f, 1, 0.5f);
        } else {
            this.setColor(1, 1, 1, 1);
        }

        batch.draw(currentFrame,getX(), getY(), getWidth(), getHeight());
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public float getTimePastLastDamage() {
        return timePastLastDamage;
    }

    public void setTimePastLastDamage(float timePastLastDamage) {
        this.timePastLastDamage = timePastLastDamage;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public boolean isAutoAim() {
        return autoAim;
    }

    public void setAutoAim(boolean autoAim) {
        this.autoAim = autoAim;
    }

    public void toggleAutoAim() {
        autoAim = !autoAim;
    }

    public int getAmmoLeft() {
        return ammoLeft;
    }

    public void setAmmoLeft(int ammoLeft) {
        this.ammoLeft = ammoLeft;
    }

    public boolean isReloading() {
        return reloading;
    }

    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    public float getTimerReloading() {
        return timerReloading;
    }

    public void setTimerReloading(float timerReloading) {
        this.timerReloading = timerReloading;
    }

    public void reduceTimerReloading(float delta) {
        timerReloading -= delta;
    }

    public void reduceAmmo(int ammo) {
        this.ammoLeft -= ammo;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public User getUser() {
        return user;
    }

    public Hero getHero() {
        return hero;
    }

    public WeaponType getWeapon() {
        return weapon;
    }

    public PlayerState getPlayerState() {
        return playerState;
    }

    public Animation<TextureRegion> getWalkAnimation() {
        return walkAnimation;
    }

    public Animation<TextureRegion> getIdleAnimation() {
        return idleAnimation;
    }

    public boolean isFacingLeft() {
        return facingLeft;
    }

    public int getProjectile() {
        int projectile = weapon.getProjectile();
        //TODO: if there are any effects
        return projectile;
    }

    public void reduceHp(float damage) {
        hp -= damage;
    }

    public void reduceLastDamage(float delta) {
        timePastLastDamage = timePastLastDamage - delta;
        if (timePastLastDamage < 0) {
            timePastLastDamage = 0;
        }
    }
}

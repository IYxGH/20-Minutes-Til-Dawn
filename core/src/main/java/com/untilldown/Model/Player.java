package com.untilldown.Model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.untilldown.Controller.ModelControllers.SFXController;
import com.untilldown.Model.EnemyClasses.Enemy;
import com.untilldown.Model.Enums.Ability;
import com.untilldown.Model.Enums.AnimationEffect;
import com.untilldown.Model.Enums.Hero;
import com.untilldown.Model.Enums.WeaponType;

import java.util.HashMap;

public class Player extends Actor {
    private final User user;
    private final Hero hero;
    private final WeaponType weapon;

    //game statues
    private float hp;
    private int kills = 0;
    private int ammoLeft;
    private int level = 1;
    private int xp = 0;
    private float timePastLastDamage = 0;
    private boolean autoAim = false;
    private HashMap<Ability, Integer> collectedAbilities = new HashMap<>();

    //effects
    private float maxHp;
    private float timerBuffWeapon;
    private int projectileEffect = 0;
    private int maxAmmo = 0;
    private float timerSpeedEffect = 0;

    //reloading variables
    private boolean autoReload = true;
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
        this.user = user;
        this.hero = hero;
        this.weapon = weapon;
        this.maxAmmo = weapon.getMaxAmmo();
        this.ammoLeft = maxAmmo;

        hp = hero.getHp();
        maxHp = hp;


        //walking animation
        walkAnimation = new Animation(0.1f, hero.getTextureWalking());
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP);


        // Standing animation
        idleAnimation = new Animation(0.1f, hero.getTextureIdle());
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion sampleFrame = hero.getTextureIdle().get(0);
        setSize(sampleFrame.getRegionWidth(), sampleFrame.getRegionHeight());

        // Abilities
        for (Ability ability : Ability.values()) {
            collectedAbilities.put(ability, 0);
        }

    }

    public double getSpeed() {
        int effect = 1;
        if (timerSpeedEffect > 0) effect++;
        return hero.getSpeed() * effect;
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

    public void reduceHp(float damage) {
        hp -= damage;
    }

    public void addHp(float hp) {
        this.hp += hp;
    }

    public void addMaxHp(float maxHp) {
        this.maxHp += maxHp;
    }

    public float getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(float maxHp) {
        this.maxHp = maxHp;
    }

    public void addMaxAmmo(int maxAmmo) {
        this.maxAmmo += maxAmmo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
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

    public void addKills(int kills) {
        this.kills += kills;
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
        if (timerReloading <= 0) return;
        timerReloading -= delta;
    }

    public boolean isAutoReload() {
        return autoReload;
    }

    public void setAutoReload(boolean autoReload) {
        this.autoReload = autoReload;
    }

    public void toggleAutoReload() {
        autoReload = !autoReload;
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

    public void addXp(int xp) {
        this.xp += xp;
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

    public float getTimerBuffWeapon() {
        return timerBuffWeapon;
    }

    public void setTimerBuffWeapon(float timerBuffWeapon) {
        this.timerBuffWeapon = timerBuffWeapon;
    }

    public void reduceTimerBuffWeapon(float delta) {
        this.timerBuffWeapon -= delta;
    }

    public float getTimerSpeedEffect() {
        return timerSpeedEffect;
    }

    public void setTimerSpeedEffect(float timerSpeedEffect) {
        this.timerSpeedEffect = timerSpeedEffect;
    }

    public void reduceTimerSpeedEffect(float delta) {
        if (timerSpeedEffect <= 0) return;
        timerSpeedEffect -= delta;
    }

    public int getProjectileEffect() {
        return projectileEffect;
    }

    public void setProjectileEffect(int projectileEffect) {
        this.projectileEffect = projectileEffect;
    }

    public void addProjectileEffect(int amount) {
        this.projectileEffect += amount;
    }

    public HashMap<Ability, Integer> getCollectedAbilities() {
        return collectedAbilities;
    }

    public void setCollectedAbilities(HashMap<Ability, Integer> collectedAbilities) {
        this.collectedAbilities = collectedAbilities;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public void setWalkAnimation(Animation<TextureRegion> walkAnimation) {
        this.walkAnimation = walkAnimation;
    }

    public void setIdleAnimation(Animation<TextureRegion> idleAnimation) {
        this.idleAnimation = idleAnimation;
    }

    public void setFacingLeft(boolean facingLeft) {
        this.facingLeft = facingLeft;
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
        projectile += projectileEffect;
        return projectile;
    }

    public void reduceLastDamage(float delta) {
        timePastLastDamage = timePastLastDamage - delta;
        if (timePastLastDamage < 0) {
            timePastLastDamage = 0;
        }
    }

    public void damagePlayer(Enemy enemy, Stage stage) {
        this.reduceHp(enemy.getDamage());
        this.setTimePastLastDamage(2);
        enemy.reduceHp(1);
        stage.addActor(new AnimationActor(AnimationEffect.HOLYSHIELD_EFFECTS, this, true));
        if (App.isSFXon()) SFXController.HurtSound();
    }

    public void damagePlayer(Bullet bullet, Stage stage   ) {
        this.reduceHp(bullet.getDamage());
        this.setTimePastLastDamage(2);
        stage.addActor(new AnimationActor(AnimationEffect.HOLYSHIELD_EFFECTS, this, true));
        if (App.isSFXon()) SFXController.HurtSound();
    }

    public int getPoints() {
        return getKills() * (int) App.getActiveGame().getTime();
    }
}

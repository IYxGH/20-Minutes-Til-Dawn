package com.untilldown.Model;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.untilldown.Model.Enums.AvatarType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String username;
    private String password;
    private String securityAnswer = null;
    private boolean isGuest = false;
    private AvatarType avatarAssigned;
    private Image avatarImage;

    //Game records
    private int totalPoints;
    private int totalKills;
    private float maxLifeTime;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        GameAssetManager.getGameAssetManager();
        avatarAssigned = AvatarType.getRandomAvatarType();
    }

    public User(boolean isGuest) {
        this.isGuest = isGuest;
        username = "GUEST";
        this.avatarAssigned = AvatarType.EL_PRIMO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public AvatarType getAvatarAssigned() {
        return avatarAssigned;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public void setAvatarImage(Image avatarImage) {
        this.avatarImage = avatarImage;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public float getMaxLifeTime() {
        return maxLifeTime;
    }

    public void setMaxLifeTime(float maxLifeTime) {
        this.maxLifeTime = maxLifeTime;
    }

    public void setAvatarAssigned(AvatarType avatarAssigned) {
        this.avatarAssigned = avatarAssigned;
    }

    public Image getAvatarImage() {
        if (avatarAssigned  == null) return avatarImage;
        return null; //TODO
    }

    public static boolean isPasswordValid(String password) {
        String pattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*)(_]).{8,}$";

        Matcher matcher = Pattern.compile(pattern).matcher(password);

        return matcher.matches();
    }


}

package com.untilldown.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String username;
    private String password;
    private String securityAnswer = null;
    private int avatarAssignedID;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        GameAssetManager.getGameAssetManager();
        avatarAssignedID = (int) (Math.random() * GameAssetManager.NUM_AVATARS);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getAvatarAssignedID() {
        return avatarAssignedID;
    }

    public void setAvatarAssignedID(int avatarAssignedID) {
        this.avatarAssignedID = avatarAssignedID;
    }

    public static boolean isPasswordValid(String password) {
        String pattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*)(_]).{8,}$";

        Matcher matcher = Pattern.compile(pattern).matcher(password);

        return matcher.matches();
    }
}

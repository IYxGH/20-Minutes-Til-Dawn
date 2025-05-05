package com.untilldown.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String username;
    private String password;
    private String securityAnswer = null;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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

    public static boolean isPasswordValid(String password) {
        String pattern = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*)(_]).{8,}$";

        Matcher matcher = Pattern.compile(pattern).matcher(password);

        return matcher.matches();
    }
}
